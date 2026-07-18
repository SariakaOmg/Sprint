package exemple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import mg.itu.Controller;
import mg.itu.URLMapping;
import utils.*;
import exemple.model.MonObjet;
import exemple.service.MonObjetService;

@Controller
public class ex1 {
    @URLMapping(value="/sasa2", methode = "GET")
    public String fonctionExemple(){
        // System.out.println("OK11");
        String a = "ok methode 1 ";
        return a;
    }

    @URLMapping(value="/sasa1", methode = "GET")
    public ModelView methode2(){
        ArrayList<String> fruitss = new ArrayList<>();
        fruitss.add("Maka");
        ArrayList<Object> fruits = new ArrayList<>(fruitss);
        Map<String, ArrayList<Object>> list = new HashMap<>();
        list.put("key", fruits);
        ModelView mv = new ModelView("Fafa",list);
        
        return mv;
    }

    @URLMapping(value = "/fafa", methode = "GET")
public ModelView afficherFafa(HttpServletRequest request, HttpServletResponse response) {

    // 1. Récupérer le contexte Spring (chargé par ContextLoaderListener)
    WebApplicationContext context = WebApplicationContextUtils
            .getRequiredWebApplicationContext(request.getServletContext());

    // 2. Extraire le service Spring depuis le contexte
    MonObjetService monObjetService = context.getBean(MonObjetService.class);

    // 3. Utiliser le service (qui va chercher en base) pour extraire les données
    ArrayList<MonObjet> listeDonnees = monObjetService.getDonneesPourFafa();
    ArrayList<Object> listeObjets = new ArrayList<>(listeDonnees); // <-- la conversion

    Map<String, ArrayList<Object>> list = new HashMap<>();
    list.put("key", listeObjets);
    ModelView mv = new ModelView("Fafa", list);
    return mv;
}

}
