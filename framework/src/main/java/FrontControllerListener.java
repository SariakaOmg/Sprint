package main.java;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class FrontControllerListener implements ServletContextListener {

    private static final String SPRING_ROOT = "org.springframework.web.context.WebApplicationContext.ROOT";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("springContext", servletContext.getAttribute(SPRING_ROOT));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // rien à faire pour l'instant
    }
}
