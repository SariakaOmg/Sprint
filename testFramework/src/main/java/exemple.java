import mg.itu.Controller;
import mg.itu.Param;
import mg.itu.URLMapping;
import mg.itu.WebRest;
import mg.itu.Param;
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
    public String FonctionExemple3(@Param("param1") String param1, @Param("param2") String param2, @Param("param3") String param3){
        return param1 + " et " + param2 + " et " + param3;
    }
}