package main.java;
import jakarta.servlet.ServletException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.Files;
import java.util.stream.Stream;
import mg.itu.URLMapping;
import utils.Scannerrrs;
import java.util.HashMap;
import java.util.Map;
import utils.ClasseMethodeMap;

public class FrontControllerServlet extends jakarta.servlet.http.HttpServlet {
    private Map<String, ClasseMethodeMap> listeUrMap2 = new HashMap<>();
    private String Errer;

    public void init() throws ServletException {
        String chemine = "/opt/tomcat/webapps/testFramework/WEB-INF/classes";
        String packageContr = "";
        if (getInitParameter("PackCon") != null) {
            packageContr = getInitParameter("PackCon");
        }
        try {
            ArrayList<ArrayList<String>> scanResultMap = Scannerrrs.ScannerURLMapping(chemine, packageContr);
            for (int index = 0; index < scanResultMap.size(); index++) {
               ClasseMethodeMap cm = new ClasseMethodeMap();
               cm.setKilasy(Class.forName(scanResultMap.get(index).get(0)));
               cm.setNomMethode(scanResultMap.get(index).get(1));
               if(this.listeUrMap2.get(scanResultMap.get(index).get(2)) != null){
                throw new Exception("Deux Methode ayant le meme URL ");
               }else{
               this.listeUrMap2.put(scanResultMap.get(index).get(2), cm);
               }
            }
            this.Errer = "";
        } catch (Exception e) {
            this.Errer = e.getMessage();
        }
        
    }
    protected void processRequest(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
    response.setContentType("text/html;charset=UTF-8");
     
     String url = request.getRequestURI();

     java.io.PrintWriter out = response.getWriter();
     
     out.println("<!DOCTYPE html>");
     out.println("<html>");
     out.println("<head>");
     out.println("    <title>Mon Frameworks</title>");
     out.println("</head>");
     out.println("<body>");
     
     out.println("    <h1>Bienvenue dans mon Frameworks !</h1>");
     out.println("    <p>URL détectée par le FrontController : <strong>" + url + "</strong></p>");
     String path = request.getContextPath();
     String urlsansProjet = url.substring(path.length());
     Boolean urltrouve = false;
     if(!this.Errer.equals("")){
        out.println("<p> Erreur : "+this.Errer+"</p>");
        }else{
        if (this.listeUrMap2.get(urlsansProjet) != null) {
        out.println("<p> url de la fonction : "+urlsansProjet+"</p>");
        out.println(" <p> Classe ou elle se trouve : "+this.listeUrMap2.get(urlsansProjet).getKilasy().getName()+" </p> ");
        out.println(" <p> Methode qui l utilise : "+this.listeUrMap2.get(urlsansProjet).getNomMethode()+" </p> ");
        urltrouve = true;
        }    
        if(!urltrouve){
        out.println("<p>Inconnu les seules connus : </p>");
        
        this.listeUrMap2.forEach((NomUrl , ClasseMethode)->{
            out.println("<p>Url correspondant : "+NomUrl+"</p>");
            out.println("<p> Classe ou elle se trouve :"+ClasseMethode.getKilasy().getName()+" </p>");
            out.println("<p> Methode qui l'utilise : "+ClasseMethode.getNomMethode()+" </p>");
        });
    }
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