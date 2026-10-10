package main.java;
import jakarta.servlet.ServletException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.util.stream.Stream;
import mg.itu.URLMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;

import utils.*;
import mg.itu.Param;

import org.springframework.web.context.WebApplicationContext;
public class FrontControllerServlet extends jakarta.servlet.http.HttpServlet {
    private Map<URLetMethodeHttps, ClasseMethodeMap> listeUrMap3 = new HashMap<>();
    private String Errer;
    private WebApplicationContext springContext;
    private String packView;
    private String extensionView;
 
    public void init() throws ServletException {
        // Étape A : Récupérer la valeur déclarée dans le web.xml ("/WEB-INF/classes/")
        String parametreChemin = this.getInitParameter("CheminClasses");

        // Étape B : Convertir en chemin absolu réel sur le disque
        String chemine = this.getServletContext().getRealPath(parametreChemin);
        
        // String chemine = "/opt/tomcat/webapps/testFramework/WEB-INF/classes";
        String packageContr = "";
        if (getInitParameter("PackCon") != null) {
            packageContr = getInitParameter("PackCon");
        }
        this.packView = getInitParameter("PackView") != null ? getInitParameter("PackView") : "/WEB-INF/";
        this.extensionView = getInitParameter("ExtensionView") != null ? getInitParameter("ExtensionView") : "jsp";
        try {
            ArrayList<ArrayList<String>> scanResultMap = Scannerrrs.ScannerURLMapping(chemine, packageContr);
           // Scannerrrs.ScannerObjetDansObjet(chemine, packageContr);
            this.listeUrMap3 = Scannerrrs.MettreDansMap(scanResultMap);
            this.Errer = "";
        } catch (Exception e) {
            this.Errer = e.getMessage();
        }
        this.springContext = (WebApplicationContext) getServletContext().getAttribute("springContext");
    }
 
    // public static void GenererFonctionGetters(Class<?> kilasySup, String NomMethode, Class<?> typeDeRetour){

    // }
    // public static void GenererFonctionSetters(Class<?> kilasySup, String NomMethode, Class<?> TypeArg){

    // }
    //
    // private Object convertirArgument(String[] valeurs, Class<?> typeAttendu) {
    // if (valeurs == null || valeurs.length == 0) {
    //     if (typeAttendu.isPrimitive()) {
    //         if (typeAttendu == boolean.class) return false;
    //         return 0; 
    //     }
    //     return null; 
    // }
 
    // String premiereValeur = valeurs[0]; 

    // if(valeurs.length == 1){
    // if (typeAttendu == String.class) {
    //     return premiereValeur;
    // } else if (typeAttendu == int.class || typeAttendu == Integer.class) {
    //     return Integer.parseInt(premiereValeur);
    // } else if (typeAttendu == double.class || typeAttendu == Double.class) {
    //     return Double.parseDouble(premiereValeur);
    // } else if (typeAttendu == boolean.class || typeAttendu == Boolean.class) {
    //     return Boolean.parseBoolean(premiereValeur);
    // }
    // return premiereValeur; 
    // }else if(valeurs.length > 1){
    //     if (typeAttendu == String[].class) {
    //         return valeurs;
    //     } else if (typeAttendu == int[].class) {
    //         int[] intArray = new int[valeurs.length];
    //         for (int i = 0; i < valeurs.length; i++) {
    //             intArray[i] = Integer.parseInt(valeurs[i]);
    //         }
    //         return intArray;
    //     } else if (typeAttendu == double[].class) {
    //         double[] doubleArray = new double[valeurs.length];
    //         for (int i = 0; i < valeurs.length; i++) {
    //             doubleArray[i] = Double.parseDouble(valeurs[i]);
    //         }
    //         return doubleArray;
    //     } else if (typeAttendu == boolean[].class) {
    //         boolean[] booleanArray = new boolean[valeurs.length];
    //         for (int i = 0; i < valeurs.length; i++) {
    //             booleanArray[i] = Boolean.parseBoolean(valeurs[i]);
    //         }
    //         return booleanArray;
    //     }
    // }

    
    
    // return null;
    // }

    private Object convertirArgument(String[] valeurs, Class<?> typeAttendu) {
    if (valeurs == null || valeurs.length == 0) {
        if (typeAttendu.isPrimitive()) return typeAttendu == boolean.class ? (Object) false : (Object) 0;
        return null;
    }

    // Tableaux (1 ou plusieurs valeurs)
    if (typeAttendu == String[].class) return valeurs;
    if (typeAttendu == int[].class) {
        int[] a = new int[valeurs.length];
        for (int i = 0; i < a.length; i++) a[i] = Integer.parseInt(valeurs[i]);
        return a;
    }
    if (typeAttendu == double[].class) {
        double[] a = new double[valeurs.length];
        for (int i = 0; i < a.length; i++) a[i] = Double.parseDouble(valeurs[i]);
        return a;
    }
    if (typeAttendu == boolean[].class) {
        boolean[] a = new boolean[valeurs.length];
        for (int i = 0; i < a.length; i++) a[i] = Boolean.parseBoolean(valeurs[i]);
        return a;
    }

    // Valeurs simples
    String v = valeurs[0];
    if (typeAttendu == int.class || typeAttendu == Integer.class) return Integer.parseInt(v);
    if (typeAttendu == double.class || typeAttendu == Double.class) return Double.parseDouble(v);
    if (typeAttendu == boolean.class || typeAttendu == Boolean.class) return Boolean.parseBoolean(v);
    return v;
}

    private Method trouverSetter(Class<?> clazz, String nomSetter) {
    for (Method m : clazz.getMethods()) {
        if (m.getName().equalsIgnoreCase(nomSetter) && m.getParameterCount() == 1) {
            return m;
        }
    }
    return null;
}
    //maka donnee by url
    protected void TakeDonneByUrl(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Map<URLetMethodeHttps, ClasseMethodeMap> listeFiltree){
        listeFiltree.forEach((url, classeMethode) -> {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String[]> parametresFiltrees = new HashMap<>();
            // class[0] ilay class attribut, class[1] ilay class an ilay objet
            Map<String, Class<?>[]> parametermethode = classeMethode.getNomEtTyPeArg();
            // Map de object complexe
            parametermethode.forEach((nom, value)->{
                String[] paramValue = parameterMap.get(nom);
                if (paramValue != null) {
                    parametresFiltrees.put(nom, paramValue);
                }
            });
            
            // Map<String, String[]> ObjectCOmplexe = new HashMap<>();
            Map<String, ObjetC> ObjetCOmplexeMap = new HashMap<>();
            
            Class<?> kl = classeMethode.getKilasy();
            Method[] methodkl = kl.getMethods();
            Method m = null;

            for (Method method : methodkl) {
                if (method.getName().equals(classeMethode.getNomMethode())) {
                    m = method;
                }
            }
            
            ArrayList<Object> temp = new ArrayList<>();
            parametermethode.forEach((nom, klas)->{
                String[] value = parameterMap.get(nom);
                if (value == null) return;
                if (klas != null){
                    if (klas[0] != null && klas[1] == null){
                        Object arg = convertirArgument(value, klas[0]);
                        temp.add(arg);
                    } else if (klas[0] != null && klas[1] != null){
                        Class<?> kil= klas[0];
                        String kls = kil.getName();
                        String[] str = new String[2];
                        str[0] = kls;
                        // a regarder value[0]
                        str[1] = value[0];
                        /////
                        // Map<String, String[]> maptemp = new HashMap<>();
                        // maptemp.put(kls, value);
                        ObjetC o = new ObjetC(kls, value);
                        ObjetCOmplexeMap.put(nom, o);
                        /////
                        // ObjectCOmplexe.put(nom,str);
                    }
                }
            });
            // Noms avec indice, ex : nom_0_liste_Obj
                parameterMap.forEach((nomUrl, valeursUrl) -> {
                    if (nomUrl.matches(".*_\\d+_.*")) {
                        Class<?>[] klas = parametermethode.get(Accesseur.sansIndices(nomUrl));
                        if (klas != null && klas[0] != null && klas[1] != null) {
                            ObjetCOmplexeMap.put(nomUrl, new ObjetC(klas[0].getName(), valeursUrl));
                        }
                    }
                });
            Object[] argumentsPourAppel = new Object[temp.size()];
            for (int index = 0; index < temp.size(); index++) {
                argumentsPourAppel[index] = temp.get(index);
            }
                Object result = null;
                try {
                    Object instance = classeMethode.isStaticite() ? null : kl.getDeclaredConstructor().newInstance();

                    if (Util.haveParameter(m, WebApplicationContext.class)) {
                        if (this.springContext == null) {
                            throw new Exception("Pas de springContext disponible");
                        }
                        result = m.invoke(instance, this.springContext);
                    } else if (Util.haveParameter(m, jakarta.servlet.http.HttpServletRequest.class)
                            && Util.haveParameter(m, jakarta.servlet.http.HttpServletResponse.class)) {
                        result = m.invoke(instance, request, response);
                    } else {
                        Parameter[] p = m.getParameters();
                        Map<Integer, Object> finalE = new HashMap<>();
                        for (int index = 0; index < p.length; index++) {
                            String nomParametre = p[index].getName();
                            Class<?> typeParametre = p[index].getType();
                            if(typeParametre.getPackageName().equals("exemple.model")){
                            Map<String, String[]> pc = new HashMap<>();
                            int indice = -1;
                            int indice2 = -1;
                            for (Map.Entry<String, ObjetC> entry : ObjetCOmplexeMap.entrySet()) {
                                String nomComplexe = entry.getKey();
                                int lastIndex = nomComplexe.lastIndexOf("_");

                                    String AvantNomComplexe = nomComplexe;
                                    String apreNomComplexe = "";

                                if (lastIndex != -1) {
                                    AvantNomComplexe = nomComplexe.substring(0, lastIndex);
                                    apreNomComplexe = nomComplexe.substring(lastIndex + 1);
                                }
                                ObjetC oc = entry.getValue();
                                String[] valeurComplexe = oc.getValues();
                                if (nomParametre.equals(apreNomComplexe) && apreNomComplexe != null && AvantNomComplexe != null) {
                                    //pc.put(AvantNomComplexe, valeurComplexe);
                                    pc.put(AvantNomComplexe, valeurComplexe);
                                    indice = index;
                                }
                            }
                            //////////////
                            // for (Map.Entry<String, String[]> entry : ObjectCOmplexe.entrySet()) {
                            //     String nomComplexe = entry.getKey();
                            //     int lastIndex = nomComplexe.lastIndexOf("_");

                            //         String AvantNomComplexe = nomComplexe;
                            //         String apreNomComplexe = "";

                            //     if (lastIndex != -1) {
                            //         AvantNomComplexe = nomComplexe.substring(0, lastIndex);
                            //         apreNomComplexe = nomComplexe.substring(lastIndex + 1);
                            //     } 
                                    
                            //     //String apreNomComplexe = nomComplexe.substring(nomComplexe.indexOf("_") + 1);
                            //     //String AvantNomComplexe = nomComplexe.substring(0, nomComplexe.indexOf("_"));
                            //     String[] valeurComplexe = entry.getValue();
                            //     if (nomParametre.equals(apreNomComplexe) && apreNomComplexe != null && AvantNomComplexe != null) {
                            //         //pc.put(AvantNomComplexe, valeurComplexe);
                            //         pc.put(AvantNomComplexe, valeurComplexe[1]);
                            //         indice = index;
                            //     }
                            // }
                            ///
                            Object b = typeParametre.getDeclaredConstructor().newInstance();
                            for (Map.Entry<String, String[]> entry : pc.entrySet()) {
                                // Method method = trouverSetter(typeParametre, "set" + entry.getKey());
                                // Class<?> typeAttendu = method.getParameterTypes()[0];
                                // Object ValeurConvertie = convertirArgument(entry.getValue(), typeAttendu);
                                // method.invoke(b, ValeurConvertie);
                                // Accesseur acc = RegistreAccesseurs.pour(typeParametre).get(entry.getKey());
                                // acc.set(b, entry.getValue());
                                Accesseur acc = RegistreAccesseurs.pour(typeParametre).get(Accesseur.sansIndices(entry.getKey()));
                                    if (acc != null) {
                                        acc.set(b, entry.getValue(), Accesseur.indices(entry.getKey()));
                                    }
                            }
                            if(indice != -1)
                            {finalE.put(indice, b);}
                            }
                             
                        }
                        
                        Object[] args = new Object[p.length];

                        for (Map.Entry<Integer, Object> e : finalE.entrySet()) {
                            args[e.getKey()] = e.getValue();
                        }
                        
                        int j = 0;
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] == null && j < argumentsPourAppel.length) {
                                args[i] = argumentsPourAppel[j++];
                            }
                        }
                        
                        result = m.invoke(instance, args);
                    }
                
                    request.setAttribute("methodeNom", m.getName());
                    request.setAttribute("classeNom", kl.getSimpleName());
                    
                    if (result instanceof ModelView && classeMethode.isWebRest()) {
                        throw new Exception("Erreur : Une méthode annotée avec @WebRest ne peut pas retourner un ModelView.");
                    }
                    if (result instanceof ModelView && !classeMethode.isWebRest()) {
                        ModelView mv = (ModelView) result;
                        if (mv.getContenueView() != null) {
                            mv.getContenueView().forEach(request::setAttribute);
                        }
                        request.setAttribute("vueForward", mv.getNomView());
                    } else if (m.getReturnType() == void.class) {
                        request.setAttribute("statusExecution", "Exécutée avec succès (void, aucun retour)");
                    } else if(m.getReturnType() != void.class) {
                        if (m.getReturnType() ==  ModelView.class && !classeMethode.isWebRest()){
                            ModelView a = (ModelView) result;
                            request.setAttribute("ModelView", a);
                        }else{
                            request.setAttribute("statusExecution", "Retour : " + (result != null ? result.toString() : "null"));
                            // faire en json le resultat
                            Gson gson = new Gson();
                            String json = gson.toJson(result);
                            request.setAttribute("jsonResult", json);
                        }
                    } 
                    
                } catch (Exception e) {
                    request.setAttribute("erreurExecution", e.getMessage());
                }
        });
    }
    //filtrer by url
    protected Map<URLetMethodeHttps, ClasseMethodeMap> FiltrerByUrl(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)throws java.io.IOException{
        String url = request.getRequestURI();
            
        String path = request.getContextPath();
    
        String urlsansProjet = url.substring(path.length());
        Map<URLetMethodeHttps, ClasseMethodeMap> liste = new HashMap<>();

        if(!this.Errer.equals("")){
        }else{
            URLetMethodeHttps urLetMethodeHttps = new URLetMethodeHttps();
        if (request.getMethod().equals("GET")){
            urLetMethodeHttps.setUrl(urlsansProjet);
            urLetMethodeHttps.setMethode("GET");
        }else if (request.getMethod().equals("POST")){
            // URLetMethodeHttps urLetMethodeHttps2 = new URLetMethodeHttps();
            urLetMethodeHttps.setUrl(urlsansProjet);
            urLetMethodeHttps.setMethode("POST");
        }

        if (this.listeUrMap3.get(urLetMethodeHttps) != null) {
            liste.put(urLetMethodeHttps, this.listeUrMap3.get(urLetMethodeHttps));
        }if (this.listeUrMap3.get(urLetMethodeHttps) != null) {
            liste.put(urLetMethodeHttps,this.listeUrMap3.get(urLetMethodeHttps));
        }    
        }
        return liste;
    }

    
    protected void executeFonction(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException{
        response.setContentType("text/html;charset=UTF-8");
        Map<URLetMethodeHttps, ClasseMethodeMap> listeFiltree = this.FiltrerByUrl(request, response);
        if (request.getDispatcherType() == jakarta.servlet.DispatcherType.FORWARD) {
            request.getServletContext().getNamedDispatcher("jsp").forward(request, response);
            return;
        }
        if (!listeFiltree.isEmpty()) {
            this.TakeDonneByUrl(request, response, listeFiltree);
        }
        String vueForward = (String) request.getAttribute("vueForward");
        if (vueForward != null) {
            String chemin = this.packView + vueForward + "." + this.extensionView;
            request.getRequestDispatcher(chemin).forward(request, response);
        } else if (request.getAttribute("jsonResult") != null) {
            //this.Output(request, response);
            this.OutPutJson(request, response);
        } else {
            this.Output(request, response);
        }
    }
 
    protected  void OutPutJson(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {
        String jsonResult = (String) request.getAttribute("jsonResult");
        if (jsonResult != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResult);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<html><body><h1>Aucun résultat JSON disponible</h1></body></html>");
        }
    }
    // output
    protected void Output(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {
        String url = request.getRequestURI();
        java.io.PrintWriter out = response.getWriter();
        
        // Récupération des informations de la méthode exécutée
        String classeNom = (String) request.getAttribute("classeNom");
        String methodeNom = (String) request.getAttribute("methodeNom");
        String statusExecution = (String) request.getAttribute("statusExecution");
        String erreurExecution = (String) request.getAttribute("erreurExecution");
 
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Mon Framework</title></head>");
        out.println("<body>");
        out.println("    <h1>Bienvenue dans mon Framework !</h1>");
        out.println("    <p>URL détectée : <strong>" + url + "</strong></p>");
        
        if(!this.Errer.equals("")){
            out.println("<p style='color:red;'>Erreur Initialisation : " + this.Errer + "</p>");
        } else if (erreurExecution != null) {
            out.println("<p style='color:red;'>Erreur lors de l'exécution : " + erreurExecution + "</p>");
        } else if (methodeNom != null) {
            out.println("<h3>Contrôleur invoqué :</h3>");
            out.println("<ul>");
            out.println("    <li><strong>Classe :</strong> " + classeNom + "</li>");
            out.println("    <li><strong>Méthode :</strong> " + methodeNom + "()</li>");
            out.println("    <li><strong>Résultat :</strong> " + statusExecution + "</li>");
            out.println("</ul>");
        } else {
            out.println("<p>Aucune méthode correspondante trouvée pour cette URL.</p>");
        }
 
        out.println("</body>");
        out.println("</html>");
    }
 
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
        executeFonction(request, response);
    }
 
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
        executeFonction(request, response);
    }
} 
