package exemple.model;
import  utils.MapFile;
public class MonObjet4 {
    MapFile fichier;
    String nom;
    public MapFile getFichier() {
        return fichier;
    }
    public void setFichier(MapFile fichier) {
        this.fichier = fichier;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public MonObjet4() {
    }
    public MonObjet4(MapFile fichier, String nom) {
        this.fichier = fichier;
        this.nom = nom;
    }
    
}
