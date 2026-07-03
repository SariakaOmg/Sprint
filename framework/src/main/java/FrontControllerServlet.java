package main.java;
import jakarta.servlet.ServletException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.Files;
import java.util.stream.Stream;
import mg.itu.*;
import utils.Scannerrrs;

public class FrontControllerServlet extends jakarta.servlet.http.HttpServlet {
    private ArrayList<String> Controller = new ArrayList<>();

    public void init() throws ServletException {
        String chemine = "/opt/tomcat/webapps/testFramework/WEB-INF/classes";
        String packageContr = "";
        //Path cheminFichier = Paths.get(chemine);
        //Path cheminFichier = Paths.get("/opt/tomcat/webapps/testFramework/WEB-INF/classes");
        //try {
        //    Stream<Path> chemins = Files.walk(cheminFichier);
        //    chemins.forEach(chemin -> {
        //        if (!Files.isDirectory(chemin)){
        //            String cheminString = chemin.toString().replace(chemin.getFileSystem().getSeparator(), ".");
        //            String cheminVrais = cheminString.substring(chemine.length() + 1);
        //            String CheminVraissansPointClasse = cheminVrais.substring(0, cheminVrais.length()-6);
        //            Class<?> kilasy = null;
        //            try {
        //            kilasy = Class.forName(CheminVraissansPointClasse);
        //            } catch (Exception e) {
        //                
        //            }
        //            if(kilasy != null && kilasy.isAnnotationPresent(mg.itu.Controller.class)){
        //                this.Controller.add(kilasy.getName());
        //                //this.Controller.add(cheminString);
        //            }
        //        }
        //    });
        //} catch (Exception e) {
        //    // TODO: handle exception
        //}
        if (getInitParameter("PackCon") != null) {
            packageContr = getInitParameter("PackCon");
        }
        ArrayList<String> scanResultContr= Scannerrrs.ScannerController(chemine, packageContr);
        for (int index = 0; index < scanResultContr.size(); index++) {
            this.Controller.add(scanResultContr.get(index));
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
     out.println(" <p> Ici tous les controllers de cette test : </p> ");
     for (int i = 0; i < Controller.size(); i++) {
        out.println(" <p> controller"+i+" : "+Controller.get(i));
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