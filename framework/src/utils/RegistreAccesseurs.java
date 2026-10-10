package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mg.itu.ObjetDansObjet;

/**
 * Pour une classe, construit la table  nom -> Accesseur.
 * Ex pour O (avec @ObjetDansObjet sur a et b) :
 *   "id"      -> O.id
 *   "nom_b_a" -> O.a.b.nom
 */
public class RegistreAccesseurs {
    private static final Map<Class<?>, Map<String, Accesseur>> CACHE = new ConcurrentHashMap<>();

    public static Map<String, Accesseur> pour(Class<?> c) {
        return CACHE.computeIfAbsent(c, k -> {
            Map<String, Accesseur> m = new LinkedHashMap<>();
            explorer(k, new ArrayList<>(), m, 0);
            return m;
        });
    }

    private static void explorer(Class<?> c, List<Field> prefixe, Map<String, Accesseur> out, int profondeur) {
        if (profondeur > 6) return; // évite une boucle infinie (A contient B qui contient A)
        for (Field f : c.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) continue;
            List<Field> chemin = new ArrayList<>(prefixe);
            chemin.add(f);
            if (f.isAnnotationPresent(ObjetDansObjet.class)) {
                explorer(Accesseur.typeCible(f), chemin, out, profondeur + 1); // on descend (dans l'élément si c'est une liste/tableau)
            } else {
                out.put(cle(chemin), new Accesseur(chemin.toArray(new Field[0])));
            }
        }
    }

    // chemin [a, b, nom] -> "nom_b_a"
    private static String cle(List<Field> chemin) {
        StringBuilder sb = new StringBuilder(chemin.get(chemin.size() - 1).getName());
        for (int i = chemin.size() - 2; i >= 0; i--) {
            sb.append("_").append(chemin.get(i).getName());
        }
        return sb.toString();
    }
}