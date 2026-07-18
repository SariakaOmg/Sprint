package exemple;
import mg.itu.Controller;
import mg.itu.URLMapping;

@Controller
public class ex1 {
    @URLMapping("/methode")
    public void fonctionExemple(){
        System.out.println("OK11");
    }

    @URLMapping("/methode2")
    public void methode2(){
        System.out.println("methode2");
    }
}
