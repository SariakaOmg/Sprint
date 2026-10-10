package utils;

import java.lang.reflect.Field;

/**
 * Représente un chemin de champs, ex : a -> b -> nom
 * set(racine, valeurs) suit ce chemin et met la valeur au bout.
 */
public class Accesseur {
    private final Field[] chemin;

    public Accesseur(Field[] chemin) {
        this.chemin = chemin;
        for (Field f : chemin) {
            f.setAccessible(true);
        }
    }

    // type de l'attribut final (ex : String, int, int[]...)
    public Class<?> getType() {
        return chemin[chemin.length - 1].getType();
    }

    public void set(Object racine, String[] valeurs) throws Exception {
        Object courant = racine;
        // on descend dans les objets intermédiaires (tous sauf le dernier champ)
        for (int i = 0; i < chemin.length - 1; i++) {
            Object suivant = chemin[i].get(courant);
            if (suivant == null) {
                suivant = chemin[i].getType().getDeclaredConstructor().newInstance();
                chemin[i].set(courant, suivant);
            }
            courant = suivant;
        }
        Field cible = chemin[chemin.length - 1];
        cible.set(courant, convertir(valeurs, cible.getType()));
    }

    public Object get(Object racine) throws Exception {
        Object courant = racine;
        for (Field f : chemin) {
            if (courant == null) return null;
            courant = f.get(courant);
        }
        return courant;
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
        if (t == double.class || t == Double.class) return Double.parseDouble(v[0]);
        if (t == boolean.class || t == Boolean.class) return Boolean.parseBoolean(v[0]);
        return v[0];
    }
}