package exemple;
import mg.itu.Controller;
import mg.itu.URLMapping;

@Controller
public class ex1 {
    @URLMapping(value="/sasa1", methode = "GET")
    public String fonctionExemple(){
        // System.out.println("OK11");
        String a = "ok methode 1 ";
        return a;
    }

    @URLMapping(value="/sasa1", methode = "POST")
    public String methode2(){
       // System.out.println("methode2");
       String g = "ok methode 2 ";
       return g;
    }
}
