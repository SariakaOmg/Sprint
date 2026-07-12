package exemple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import mg.itu.Controller;
import mg.itu.URLMapping;
import utils.*;

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

}
