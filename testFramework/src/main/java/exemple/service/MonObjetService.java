package exemple.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exemple.model.MonObjet;

@Service
public class MonObjetService {

    @Autowired
    private DataSource dataSource; // bean déclaré dans applicationContext.xml

    public ArrayList<MonObjet> getDonneesPourFafa() {
        ArrayList<MonObjet> resultats = new ArrayList<>();

        String requete = "SELECT id, nom FROM matable"; // adapte à ta vraie table

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(requete)) {

            while (rs.next()) {
                resultats.add(new MonObjet(rs.getInt("id"), rs.getString("nom")));
            }

        } catch (Exception e) {
            resultats.add(new MonObjet(0, "Erreur BDD : " + e.getMessage()));
        }

        return resultats;
    }
}
