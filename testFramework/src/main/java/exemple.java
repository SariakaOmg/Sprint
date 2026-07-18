import mg.itu.Controller;
import mg.itu.URLMapping;
@Controller
public class exemple {
    @URLMapping("/sasa/exam")
    public void FonctionExemple(){
        System.out.println("OK");
    }
}