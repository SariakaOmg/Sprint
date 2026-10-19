import mg.itu.Controller;
import mg.itu.Param;
import mg.itu.URLMapping;
import mg.itu.WebRest;
import utils.ModelView;
import mg.itu.Param;

import java.util.ArrayList;

import exemple.model.MonObjet;
import exemple.model.MonObjet2;
import exemple.model.MonObjet3;
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
    
    // /sasa2/exam?param1=5&param2=Fafa
    @WebRest
    @URLMapping(value = "/sasa2/exam" , methode = "POST")
    public String FonctionExemple2(@Param("param1") int param1, @Param("param2") String param2){
        return param1 + " et " + param2;
    }

    // /sasa6/exam?id_Objet1=1&nom_Objet1=Fafa&param2=5
    @WebRest
    @URLMapping(value = "/sasa6/exam" , methode = "GET")
    public String FonctionExemple6(@Param("Objet1") MonObjet Objet1, @Param("param2") String[] param2){
        return Objet1.getNom() + " et " + param2[0];
    }

    // /sasa7/exam?param1=5&param1=6&param2=Fafa&param2=Maka
    @WebRest
    @URLMapping(value = "/sasa7/exam" , methode = "GET")
    public String FonctionExemple7(@Param("param1") String[] param1, @Param("param2") String[] param2){
        return param1[0] + "et"+ param2[1];
    }

    // /sasa3/exam?id_Objet1=1&nom_Objet1=Fafa&id_Objet2=2&nom_Objet2=Maka&NameHasard=Hasard
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

    // http://localhost:8081/testFramework/Mia/exam?id_Objet1=1&nom_Objet1=Fafa&ex_Objet1=5&ex_Objet1=6&id_Objet2=2&nom_myObjet_Objet2=Fa&id_myObjet_Objet2=3
    @WebRest 
    @URLMapping(value = "/Mia/exam" , methode = "GET")
    public String ExempleFonction(@Param("Objet1") MonObjet Objet1, @Param("Objet2") MonObjet2 Objet2){
        return Objet1.getNom() + " et " + Objet2.getId() + "et "+Objet2.getMyObjet().getNom();
    }

    // http://localhost:8081/testFramework/Mia1/exam?id_Objet1=1&nom_Objet1=Fafa&ex_Objet1=5&ex_Objet1=6
    @WebRest 
    @URLMapping(value = "/Mia1/exam" , methode = "GET")
    public String ExempleFonction1(@Param("Objet1") MonObjet Objet1){
        return Objet1.getNom() + " et " + Objet1.getId() + " et " + Objet1.getEx().get(0);
    }

    // http://localhost:8081/testFramework/Mia2/exam?id_0_list1_Objet1=1&nom_0_list1_Objet1=Fafa&id_0_list2_Objet1=2&nom_0_list2_Objet1=Maka
    @WebRest 
    @URLMapping(value = "/Mia2/exam" , methode = "GET")
    public String ExempleFonction2(@Param("Objet1") MonObjet3 Objet1){
        return Objet1.getList1().get(0).getNom() + " et " + Objet1.getList2()[0].getId();
    }

}