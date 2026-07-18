package utils;

import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class ClasseMethodeMap {
    Class<?> kilasy;
    String nomMethode;
    Map<String, Class<?>> nomEtTyPeArg = new HashMap<>();
    boolean Staticite;
    public Class<?> getKilasy() {
        return kilasy;
    }
    public void setKilasy(Class<?> kilasy) {
        this.kilasy = kilasy;
    }
    public String getNomMethode() {
        return nomMethode;
    }
    public void setNomMethode(String nomMethode) {
        this.nomMethode = nomMethode;
    }
    public ClasseMethodeMap() {
    }
    
    
    public ClasseMethodeMap(Class<?> kilasy, String nomMethode, Map<String, Class<?>> nomEtTyPeArg) {
        this.kilasy = kilasy;
        this.nomMethode = nomMethode;
        this.nomEtTyPeArg = nomEtTyPeArg;
    }
    public void CompletMethods()throws Exception{
        Method methode = null;
        for (Method m : kilasy.getMethods()) {
            if (m.getName().equals(this.nomMethode)) {
                methode = m;
            }
        }
        // les type de arguments
        Parameter[] parameters = methode.getParameters();
        for (Parameter p : parameters) {
            String nomArgument = p.getName();          
            Class<?> typeArgument = p.getType();       
    
            this.nomEtTyPeArg.put(nomArgument, typeArgument);
        }
        // static ou pas
            int modificateurs = methode.getModifiers();

            if (Modifier.isStatic(modificateurs)) {
                System.out.println("La méthode est STATIC !");
                this.Staticite = true;
            } else {
                System.out.println("La méthode n'est PAS static (méthode d'instance).");
                this.Staticite = false;
            }
    }
    public Map<String, Class<?>> getNomEtTyPeArg() {
        return nomEtTyPeArg;
    }
    public void setNomEtTyPeArg(Map<String, Class<?>> nomEtTyPeArg) {
        this.nomEtTyPeArg = nomEtTyPeArg;
    }
    public boolean isStaticite() {
        return Staticite;
    }
    public void setStaticite(boolean staticite) {
        Staticite = staticite;
    }
}
