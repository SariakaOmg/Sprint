package utils; // Adaptez selon votre package

import java.util.HashMap;
import java.util.Map;

public class MySession {
    private String id;
    private Map<String, Object> sessionData = new HashMap<>();

    public MySession() {}

    public MySession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Méthode pour ajouter une donnée
    public void set(String key, Object value) {
        sessionData.put(key, value);
    }

    // Méthode pour récupérer une donnée
    public Object get(String key) {
        return sessionData.get(key);
    }

    // Méthode pour supprimer une donnée
    public void remove(String key) {
        sessionData.remove(key);
    }

    public Map<String, Object> getSessionData() {
        return sessionData;
    }

    public void setSessionData(Map<String, Object> sessionData) {
        this.sessionData = sessionData;
    }
}