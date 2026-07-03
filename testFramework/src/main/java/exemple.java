import mg.itu.Controller;
import mg.itu.URLMapping;
@Controller
public class exemple {
    @URLMapping(value = "/sasa/exam" , methode = "GET")
    public void FonctionExemple(){
        System.out.println("OK");
    }
}