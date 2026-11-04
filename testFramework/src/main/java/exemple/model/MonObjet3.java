package exemple.model;

import java.util.ArrayList;

import mg.itu.ObjetDansObjet;

public class MonObjet3 {
    @ObjetDansObjet 
    ArrayList<MonObjet>  list1;
    @ObjetDansObjet 
    MonObjet2[] list2;
    public ArrayList<MonObjet> getList1() {
        return list1;
    }
    public void setList1(ArrayList<MonObjet> list1) {
        this.list1 = list1;
    }
    public MonObjet2[] getList2() {
        return list2;
    }
    public void setList2(MonObjet2[] list2) {
        this.list2 = list2;
    }
    public MonObjet3() {
    }
    public MonObjet3(ArrayList<MonObjet> list1, MonObjet2[] list2) {
        this.list1 = list1;
        this.list2 = list2;
    }
}
