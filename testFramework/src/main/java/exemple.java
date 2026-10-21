import mg.itu.Controller;
import mg.itu.Param;
import mg.itu.URLMapping;
import mg.itu.WebRest;
import utils.ModelView;
import utils.MySession;
import utils.MapFile;
import mg.itu.Param;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import exemple.model.MonObjet;
import exemple.model.MonObjet2;
import exemple.model.MonObjet3;
@Controller

public class exemple {

    @URLMapping(url = "traitement-login", methode = "POST")
    public ModelView login(MySession session) {
        // Supposons que l'utilisateur s'est connecté avec succès
        String nomUtilisateur = "Sariaka";

        // === POUR METTRE UNE VALEUR DANS LA SESSION ===
        session.set("userConnecte", nomUtilisateur);
        session.set("role", "Admin");

        ModelView mv = new ModelView("profil");
        return mv;
    }

    @URLMapping(url = "profil", methode = "GET")
    public ModelView monProfil(MySession session) {
        // Plus besoin de request.getSession() !
        Object user = session.get("userConnecte");
        ModelView mv = new ModelView("profil");
        mv.addObject("utilisateur", user);
        return mv;
    }

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

    // http://localhost:8081/testFramework/Mia2/examPOST?id_0_list1_Objet1=1&nom_0_list1_Objet1=Fafa&id_0_list2_Objet1=2&nom_0_list2_Objet1=Maka
    @WebRest 
    @URLMapping(value = "/Mia2/exam" , methode = "GET")
    public String ExempleFonction2(@Param("Objet1") MonObjet3 Objet1){
        return Objet1.getList1().get(0).getNom() + " et " + Objet1.getList2()[0].getId();
    }

    @WebRest 
    @URLMapping(value = "/Mae/exam" , methode = "POST")
    public String ExempleFonction3(@Param("Objet1") MapFile Objet1){
       // return Objet1.getNomFichier() + " et " + Objet1.getExtension();
       if (Objet1 == null || Objet1.getContenu() == null) {
        return "Erreur : Aucun fichier reçu ou fichier vide.";
        }

    try {
        // 1. Définir le dossier de destination sur le serveur
        // (Exemple : un dossier "uploads" à la racine de votre application ou un chemin absolu)
        String dossierDestination = "/home/sariaka/"; // ou "/var/www/uploads/" sur Linux
        
        // S'assurer que le dossier existe, sinon le créer
        File repertoire = new File(dossierDestination);
        if (!repertoire.exists()) {
            repertoire.mkdirs();
        }

        // 2. Construire le chemin complet du fichier final
        String cheminFichierFinal = dossierDestination + Objet1.getNomFichier();
        Path cheminCible = Paths.get(cheminFichierFinal);

        // 3. Écrire le tableau de bytes dans le fichier de destination
        Files.write(cheminCible, Objet1.getContenu());

        // 4. Retourner un message de succès
        return "Fichier enregistré avec succès : " + Objet1.getNomFichier() 
               + " (" + Objet1.getExtension() + ")";
        
    } catch (IOException e) {
        e.printStackTrace();
        return "Erreur lors de la sauvegarde du fichier : " + e.getMessage();
    }
    }

    @WebRest 
    @URLMapping(value = "/Mae2/exam" , methode = "POST")
    public String ExempleFonction2(@Param("Objet1") MapFile Objet1, @Param("Objet2") String Objet2){
        return Objet1.getNomFichier() + " et " + Objet1.getExtension() + " et " + Objet2;
    }

    @URLMapping(value = "/", methode = "GET")
    public ModelView formUpload() {
        return new ModelView("formulaireUpload", null);
    }

    @URLMapping(value = "/2", methode = "GET")
    public ModelView formUpload2() {
        return new ModelView("formulaireUploadAvecString", null);
    }
}