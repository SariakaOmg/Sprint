import mg.itu.Controller;
import mg.itu.WebRest;
@Controller 
public class exemple2 {
    @WebRest
    public String FonctionExemple2(){
        //System.out.println("OK2");
        return "Resultat de la fonction exemple2";
    }
}
