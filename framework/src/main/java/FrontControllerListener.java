package main.java; // Adaptez selon votre package

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utils.Scannerrrs;

@WebListener
public class FrontControllerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Map<String, Object> singletonsMap = new HashMap<>();
        //ServletContext servletContext = sce.getServletContext();
        //servletContext.setAttribute("springContext", servletContext.getAttribute(SPRING_ROOT));
        try {
            // 1. Récupérer le chemin source (les classes compilées dans WEB-INF/classes)
            String cheminSource = context.getRealPath("/WEB-INF/classes");
            
            // 2. Définir ou récupérer le nom de votre package de contrôleurs 
            // (Par exemple "exemple" ou "mg.itu.controller" selon vos projets)
            String packageController = context.getInitParameter("PackCon");
            if (packageController == null) {
                packageController = "exemple"; // Mettez votre package par défaut ici si besoin
            }

            // 3. Appeler votre méthode ScannerController existante
            ArrayList<String> listControllers = Scannerrrs.ScannerController(cheminSource, packageController);

            // 4. Parcourir les classes trouvées et instancier les Singletons
            for (int i = 0; i < listControllers.size(); i++) {
                String className = listControllers.get(i);
                Class<?> clazz = Class.forName(className);

                // Vérifier si la classe possède l'annotation @Singleton
                // (Assurez-vous d'avoir créé l'annotation @Singleton dans mg.itu)
                if (clazz.isAnnotationPresent(mg.itu.Controller.class)) {
                    // Instanciation unique au démarrage du serveur
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    singletonsMap.put(clazz.getName(), instance);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Stocker la map des singletons dans le contexte global de l'application
        context.setAttribute("singletonsControllers", singletonsMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nettoyage si nécessaire à l'arrêt
    }
}