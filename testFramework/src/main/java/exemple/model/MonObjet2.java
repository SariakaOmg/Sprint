package exemple.model;

import mg.itu.ObjetDansObjet;

public class MonObjet2 {
    int id;
    @ObjetDansObjet 
    MonObjet myObjet;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public MonObjet getMyObjet() {
        return myObjet;
    }
    public void setMyObjet(MonObjet myObjet) {
        this.myObjet = myObjet;
    }
    public MonObjet2() {
    }
    public MonObjet2(int id, MonObjet myObjet) {
        this.id = id;
        this.myObjet = myObjet;
    }
    
}
