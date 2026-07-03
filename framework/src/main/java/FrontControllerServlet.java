package main.java;

public class FrontControllerServlet extends jakarta.servlet.http.HttpServlet {

    protected void processRequest(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
    response.setContentType("text/html;charset=UTF-8");
     
     String url = request.getRequestURI();

     java.io.PrintWriter out = response.getWriter();
     
     out.println("<!DOCTYPE html>");
     out.println("<html>");
     out.println("<head>");
     out.println("    <title>Mon Framework</title>");
     out.println("</head>");
     out.println("<body>");
     
     out.println("    <h1>Bienvenue dans mon Framework !</h1>");
     out.println("    <p>URL détectée par le FrontController : <strong>" + url + "</strong></p>");
     
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