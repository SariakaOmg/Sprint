import mg.itu.Controller;
import mg.itu.Param;
import mg.itu.URLMapping;
import mg.itu.WebRest;
import mg.itu.Param;
import exemple.model.MonObjet;
@Controller
public class exemple {
    @URLMapping(value = "/sasa/exam" , methode = "GET")
    public void FonctionExemple(){
        System.out.println("OK");
    }
    @WebRest
    @URLMapping(value = "/sasa2/exam" , methode = "GET")
    public String FonctionExemple2(@Param("param1") int param1, @Param("param2") String param2){
        return param1 + " et " + param2;
    }

    @WebRest 
    @URLMapping(value = "/sasa3/exam" , methode = "GET")
    public String FonctionExemple3(@Param("Objet1") MonObjet Objet1, @Param("Objet2") MonObjet Objet2, @Param("NameHasard") String NameHasard){
        return Objet1.getNom() + " et " + Objet2.getNom() + " et " + NameHasard;
    }

    @WebRest 
    @URLMapping(value = "/sasa4/exam" , methode = "GET")
    public String FonctionExemple4(@Param("Objet1") MonObjet Objet1, @Param("Objet2") MonObjet Objet2){
        return Objet1.getNom() + " et " + Objet2.getNom();
    }
}