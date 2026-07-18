package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelView {
    String NomView;
    Map<String,ArrayList<Object>> ContenueView;
    public String getNomView() {
        return NomView;
    }
    public void setNomView(String nomView) {
        NomView = nomView;
    }
    public Map<String, ArrayList<Object>> getContenueView() {
        return ContenueView;
    }
    public void setContenueView(Map<String, ArrayList<Object>> contenueView) {
        ContenueView = contenueView;
    }
    public ModelView() {
    }
    public ModelView(String nomView, Map<String, ArrayList<Object>> contenueView) {
        NomView = nomView;
        ContenueView = contenueView;
    }   
}
