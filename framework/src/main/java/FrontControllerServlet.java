package main.java;
import jakarta.servlet.ServletException;
 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.stream.Stream;
import mg.itu.URLMapping;
import utils.Scannerrrs;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import utils.ClasseMethodeMap;
import utils.URLetMethodeHttps;
import utils.ModelView;
public class FrontControllerServlet extends jakarta.servlet.http.HttpServlet {
    private Map<URLetMethodeHttps, ClasseMethodeMap> listeUrMap3 = new HashMap<>();
    private String Errer;
    String prefixe;
    String suffixe;
 
    public void init() throws ServletException {
         // Étape A : Récupérer la valeur déclarée dans le web.xml ("/WEB-INF/classes/")
        String parametreChemin = this.getInitParameter("CheminClasses");

        // Étape B : Convertir en chemin absolu réel sur le disque
        String chemine = this.getServletContext().getRealPath(parametreChemin);
        String packageContr = "";
        if (getInitParameter("PackCon") != null) {
            packageContr = getInitParameter("PackCon");
        }
        if (getInitParameter("PackView") != null && getInitParameter("ExtensionView") != null){
            this.prefixe = getInitParameter("PackView");
            this.suffixe = getInitParameter("ExtensionView");
        }
        try {
            ArrayList<ArrayList<String>> scanResultMap = Scannerrrs.ScannerURLMapping(chemine, packageContr);
            this.listeUrMap3 = Scannerrrs.MettreDansMap(scanResultMap);
            this.Errer = "";
        } catch (Exception e) {
            this.Errer = e.getMessage();
        }
    }
 
    //
    private Object convertirArgument(String[] valeurs, Class<?> typeAttendu) {
    if (valeurs == null || valeurs.length == 0) {
        if (typeAttendu.isPrimitive()) {
            if (typeAttendu == boolean.class) return false;
            return 0; 
        }
        return null; 
    }
 
    String premiereValeur = valeurs[0]; 
 
    if (typeAttendu == String.class) {
        return premiereValeur;
    } else if (typeAttendu == int.class || typeAttendu == Integer.class) {
        return Integer.parseInt(premiereValeur);
    } else if (typeAttendu == double.class || typeAttendu == Double.class) {
        return Double.parseDouble(premiereValeur);
    } else if (typeAttendu == boolean.class || typeAttendu == Boolean.class) {
        return Boolean.parseBoolean(premiereValeur);
    }
    
    return premiereValeur; 
    }
    //maka donnee by url
    protected void TakeDonneByUrl(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Map<URLetMethodeHttps, ClasseMethodeMap> listeFiltree){
        listeFiltree.forEach((url, classeMethode) -> {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, Class<?>> parametermethode = classeMethode.getNomEtTyPeArg();
            
            Class<?> kl = classeMethode.getKilasy();
            Method[] methodkl = kl.getMethods();
            Method m = null;

            for (Method method : methodkl) {
                if (method.getName().equals(classeMethode.getNomMethode())) {
                    m = method;
                }
            }
            ArrayList<Object> temp = new ArrayList<>();
            parameterMap.forEach((nom, value)->{
                Class<?> klas = parametermethode.get(nom);
                if (klas != null){
                Object arg = convertirArgument(value, klas);
                temp.add(arg);
                }
            });
            Object[] argumentsPourAppel = new Object[temp.size()];
            for (int index = 0; index < temp.size(); index++) {
                argumentsPourAppel[index] = temp.get(index);
            }
                Object result = null;
                try {
                    if(classeMethode.isStaticite()){
                        result = m.invoke(null, argumentsPourAppel);
                    } else {
                        Object instance = kl.getDeclaredConstructor().newInstance();
                        result = m.invoke(instance, argumentsPourAppel);
                    }
                
                    request.setAttribute("methodeNom", m.getName());
                    request.setAttribute("classeNom", kl.getSimpleName());
                
                    if (m.getReturnType() == void.class) {
                        request.setAttribute("statusExecution", "Exécutée avec succès (void, aucun retour)");
                    } else if(m.getReturnType() != void.class) {
                        if (m.getReturnType() ==  ModelView.class){
                            ModelView a = (ModelView) result;
                            request.setAttribute("ModelView", a);
                        }else{
                            request.setAttribute("statusExecution", "Retour : " + (result != null ? result.toString() : "null"));
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
        if (!listeFiltree.isEmpty()) {
            this.TakeDonneByUrl(request, response, listeFiltree);
        }
    }

    protected void processRequest(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
     if (request.getDispatcherType() != jakarta.servlet.DispatcherType.REQUEST) {
        // Requête interne (forward vers une JSP), interceptée via le mapping "/*".
        // On la fait exécuter par le vrai moteur JSP de Tomcat au lieu de l'ignorer.
        request.getServletContext().getNamedDispatcher("jsp").forward(request, response);
        return;
    }

        this.executeFonction(request, response);
        
        // si c est ModelView
        if((ModelView) request.getAttribute("ModelView") == null){
            this.Output(request, response);
        }else if((ModelView) request.getAttribute("ModelView") != null){
            ModelView a = (ModelView) request.getAttribute("ModelView");
            Set<String> cle = a.getContenueView().keySet();
            String premierElement = cle.stream()
                             .findFirst()
                             .orElse(null); // Renvoie null si le Set est vide
            request.setAttribute(premierElement,a.getContenueView().get(premierElement));
            request.getRequestDispatcher(prefixe+"/"+a.getNomView()+"."+suffixe).forward(request, response);
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
        processRequest(request, response);
    }
 
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
        processRequest(request, response);
    }
} 
