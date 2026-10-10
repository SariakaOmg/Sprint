package exemple.model;

public class MonObjet {

    private int id;
    private String nom;
    private int[] ex;

    public MonObjet(int id, String nom, int[] ex) {
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

    public int[] getEx() {
        return ex;
    }

    public void setEx(int[] ex) {
        this.ex = ex;
    }
}
