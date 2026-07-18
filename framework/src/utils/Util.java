package utils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Util {

    // Vérifie si la méthode "methode" possède un paramètre de type "param"
    public static boolean haveParameter(Method methode, Class<?> param) {
        for (Parameter p : methode.getParameters()) {
            if (p.getType().equals(param)) {
                return true;
            }
        }
        return false;
    }
}
