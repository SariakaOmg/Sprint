package utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Accesseur {
    private final Field[] chemin;

    public Accesseur(Field[] chemin) {
        this.chemin = chemin;
        for (Field f : chemin) {
            f.setAccessible(true);
        }
    }

    public Class<?> getType() {
        return chemin[chemin.length - 1].getType();
    }

    public static boolean estListeOuTableau(Class<?> t) {
        return t.isArray() || Collection.class.isAssignableFrom(t);
    }

    // ArrayList<Personne> ou Personne[] -> Personne ; sinon le type du champ
    public static Class<?> typeCible(Field f) {
        Class<?> t = f.getType();
        if (t.isArray()) return t.getComponentType();
        if (Collection.class.isAssignableFrom(t)) {
            Type g = f.getGenericType();
            if (g instanceof ParameterizedType) {
                Type arg = ((ParameterizedType) g).getActualTypeArguments()[0];
                if (arg instanceof Class) return (Class<?>) arg;
            }
            return String.class;
        }
        return t;
    }

    // "nom_1_liste_Obj" -> "nom_liste_Obj"
    public static String sansIndices(String nomUrl) {
        StringBuilder sb = new StringBuilder();
        for (String seg : nomUrl.split("_")) {
            if (seg.matches("\\d+")) continue;
            if (sb.length() > 0) sb.append("_");
            sb.append(seg);
        }
        return sb.toString();
    }

    // "nom_1_liste2_0_liste1_Obj" -> [0, 1]  (ordre racine -> fin)
    public static int[] indices(String nomUrl) {
        List<Integer> trouves = new ArrayList<>();
        for (String seg : nomUrl.split("_")) {
            if (seg.matches("\\d+")) trouves.add(Integer.parseInt(seg));
        }
        int[] res = new int[trouves.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = trouves.get(trouves.size() - 1 - i);
        }
        return res;
    }

    private static Object nouvelleInstance(Class<?> c) throws Exception {
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }

    public void set(Object racine, String[] valeurs, int... indices) throws Exception {
        Object courant = racine;
        int numIndice = 0;
        for (int i = 0; i < chemin.length - 1; i++) {
            Field f = chemin[i];
            Class<?> t = f.getType();
            if (estListeOuTableau(t)) {
                if (numIndice >= indices.length) {
                    throw new IllegalArgumentException("Indice manquant pour la liste '" + f.getName()
                            + "' (ex : nom_0_" + f.getName() + "_...)");
                }
                int idx = indices[numIndice++];
                courant = elementA(courant, f, idx, true);
            } else {
                Object suivant = f.get(courant);
                if (suivant == null) {
                    suivant = nouvelleInstance(t);
                    f.set(courant, suivant);
                }
                courant = suivant;
            }
        }
        Field cible = chemin[chemin.length - 1];
        cible.set(courant, convertir(valeurs, cible));
    }

    @SuppressWarnings("unchecked")
    private Object elementA(Object proprietaire, Field f, int idx, boolean creer) throws Exception {
        Class<?> t = f.getType();
        Class<?> typeElement = typeCible(f);

        if (t.isArray()) {
            Object tab = f.get(proprietaire);
            if (tab == null || Array.getLength(tab) <= idx) {
                if (!creer) return null;
                Object nouveau = Array.newInstance(typeElement, idx + 1);
                if (tab != null) System.arraycopy(tab, 0, nouveau, 0, Array.getLength(tab));
                f.set(proprietaire, nouveau);
                tab = nouveau;
            }
            Object el = Array.get(tab, idx);
            if (el == null && creer) {
                el = nouvelleInstance(typeElement);
                Array.set(tab, idx, el);
            }
            return el;
        }

        Object coll = f.get(proprietaire);
        if (coll == null) {
            if (!creer) return null;
            coll = new ArrayList<Object>();
            f.set(proprietaire, coll);
        }
        if (!(coll instanceof List)) {
            throw new IllegalArgumentException("Le champ '" + f.getName() + "' doit être une List pour utiliser un indice");
        }
        List<Object> liste = (List<Object>) coll;
        if (liste.size() <= idx) {
            if (!creer) return null;
            while (liste.size() <= idx) liste.add(null);
        }
        Object el = liste.get(idx);
        if (el == null && creer) {
            el = nouvelleInstance(typeElement);
            liste.set(idx, el);
        }
        return el;
    }

    public Object get(Object racine, int... indices) throws Exception {
        Object courant = racine;
        int numIndice = 0;
        for (int i = 0; i < chemin.length; i++) {
            if (courant == null) return null;
            Field f = chemin[i];
            boolean dernier = (i == chemin.length - 1);
            if (!dernier && estListeOuTableau(f.getType())) {
                if (numIndice >= indices.length) {
                    throw new IllegalArgumentException("Indice manquant pour la liste '" + f.getName() + "'");
                }
                courant = elementA(courant, f, indices[numIndice++], false);
            } else {
                courant = f.get(courant);
            }
        }
        return courant;
    }

    // Listes simples : ArrayList<Integer>, List<String>, Set<Double>...
    private static Object convertir(String[] v, Field champ) {
        Class<?> t = champ.getType();
        if (Collection.class.isAssignableFrom(t)) {
            Class<?> typeElement = typeCible(champ);
            Collection<Object> resultat = Set.class.isAssignableFrom(t) ? new LinkedHashSet<>() : new ArrayList<>();
            if (v != null) {
                for (String s : v) {
                    resultat.add(convertir(new String[]{s}, typeElement));
                }
            }
            return resultat;
        }
        return convertir(v, t);
    }

    // String[] -> type attendu
    private static Object convertir(String[] v, Class<?> t) {
        if (v == null || v.length == 0) {
            if (t.isPrimitive()) return t == boolean.class ? (Object) false : (Object) 0;
            return null;
        }
        if (t == String[].class) return v;
        if (t == int[].class) {
            int[] a = new int[v.length];
            for (int i = 0; i < a.length; i++) a[i] = Integer.parseInt(v[i]);
            return a;
        }
        if (t == double[].class) {
            double[] a = new double[v.length];
            for (int i = 0; i < a.length; i++) a[i] = Double.parseDouble(v[i]);
            return a;
        }
        if (t == boolean[].class) {
            boolean[] a = new boolean[v.length];
            for (int i = 0; i < a.length; i++) a[i] = Boolean.parseBoolean(v[i]);
            return a;
        }
        if (t == int.class || t == Integer.class) return Integer.parseInt(v[0]);
        if (t == long.class || t == Long.class) return Long.parseLong(v[0]);
        if (t == float.class || t == Float.class) return Float.parseFloat(v[0]);
        if (t == double.class || t == Double.class) return Double.parseDouble(v[0]);
        if (t == boolean.class || t == Boolean.class) return Boolean.parseBoolean(v[0]);
        return v[0];
    }
}