package utils;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MySession {
    private String id;
    private Map<String, Object> attributes = new HashMap<>();
    private Set<String> initialKeys = new HashSet<>();

    public MySession() {}

    public MySession(String id) {
        this.id = id;
    }

    // --- Fonction statique from(HttpSession) ---
    public static MySession from(HttpSession httpSession) {
        MySession session = new MySession(httpSession.getId());
        
        httpSession.getAttributeNames().asIterator().forEachRemaining(name -> {
            Object value = httpSession.getAttribute(name);
            session.attributes.put(name, value);
            session.initialKeys.add(name); // Mémorise les clés présentes au départ
        });
        
        return session;
    }

    // --- Fonction flushTo(HttpSession) ---
    public void flushTo(HttpSession httpSession) {
        if (httpSession == null) return;

        // 1. Supprimer du vrai HttpSession les clés qui ont été retirées de MySession
        for (String key : initialKeys) {
            if (!attributes.containsKey(key)) {
                httpSession.removeAttribute(key);
            }
        }

        // 2. Mettre à jour ou ajouter les attributs de MySession vers HttpSession
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            httpSession.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    // --- Fonctions demandées côté développeur ---
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    // --- Getters et Setters classiques ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Set<String> getInitialKeys() {
        return initialKeys;
    }

    public void setInitialKeys(Set<String> initialKeys) {
        this.initialKeys = initialKeys;
    }
}