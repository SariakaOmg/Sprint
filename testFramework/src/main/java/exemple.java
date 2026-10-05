import mg.itu.Controller;
import mg.itu.Param;
import mg.itu.URLMapping;
import mg.itu.WebRest;
import utils.ModelView;
import mg.itu.Param;

import java.util.ArrayList;

import exemple.model.MonObjet;
@Controller
public class exemple {

    @URLMapping(value = "/showForm", methode = "GET")
    public ModelView showForm() {
        return new ModelView("form", null);
    }

    @URLMapping(value = "/sasa/exam" , methode = "GET")
    public ArrayList<String> FonctionExemple(){
        System.out.println("OK");
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Maka");
        fruits.add("Fafa");
        return fruits;
    }
    
    @WebRest
    @URLMapping(value = "/sasa2/exam" , methode = "POST")
    public String FonctionExemple2(@Param("param1") int param1, @Param("param2") String param2){
        return param1 + " et " + param2;
    }

    @WebRest
    @URLMapping(value = "/sasa6/exam" , methode = "GET")
    public String FonctionExemple6(@Param("Objet1") MonObjet Objet1, @Param("param2") String[] param2){
        return Objet1.getNom() + " et " + param2[0];
    }

    @WebRest
    @URLMapping(value = "/sasa7/exam" , methode = "GET")
    public String FonctionExemple7(@Param("param1") String[] param1, @Param("param2") String[] param2){
        return param1[0] + "et"+ param2[1];
    }

    @WebRest 
    @URLMapping(value = "/sasa3/exam" , methode = "GET")
    public String FonctionExemple3(@Param("Objet1") MonObjet Objet1, @Param("Objet2") MonObjet Objet2, @Param("NameHasard") String NameHasard){
        return Objet1.getNom() + " et " + Objet2.getNom() + " et " + NameHasard;
    }

    @URLMapping(value = "/sasa5/exam" , methode = "GET")
    public String FonctionExemple5(@Param("Objet1") MonObjet Objet1, @Param("Objet2") MonObjet Objet2, @Param("NameHasard1") String NameHasard1, @Param("NameHasard2") String NameHasard2, @Param("NameHasard3") String NameHasard3){
        return Objet1.getNom() + " et " + Objet2.getNom() + " et " + NameHasard1 + " et " + NameHasard2 + " et " + NameHasard3;
    }

    @WebRest 
    @URLMapping(value = "/sasa4/exam" , methode = "GET")
    public String FonctionExemple4(@Param("Objet1") MonObjet Objet1, @Param("Objet2") MonObjet Objet2){
        return Objet1.getNom() + " et " + Objet2.getNom();
    }

    @WebRest
@URLMapping(value = "/MonObjetAppel", methode = "GET")
public String FonctionMonObjetAppel(@Param("Objet1") MonObjet Objet1){
    return Objet1.getNom() + " et " + Objet1.getId();
}
}