package exemple.model;
import java.util.ArrayList;
public class MonObjet {

    private int id;
    private String nom;
    private ArrayList<Integer> ex;

    public MonObjet(int id, String nom, ArrayList<Integer> ex) {
        this.id = id;
        this.nom = nom;
        this.ex = ex;
    }

    public MonObjet() {
    }

    public MonObjet(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }

    public ArrayList<Integer> getEx() {
        return ex;
    }

    public void setEx(ArrayList<Integer> ex) {
        this.ex = ex;
    }
}
