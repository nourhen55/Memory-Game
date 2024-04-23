package doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.joueur;

public class gesjeuphp implements igsionjoueur {

    private Connection cnx;

    public gesjeuphp() throws SQLException {
        cnx = liaison.getInstance();
    }
    
    @Override
    public List<joueur> trierListejoueur() throws SQLException {
        List<joueur> liste = new ArrayList<>();
        String query = "SELECT * FROM joueur ORDER BY score DESC";
        try (
        		PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(new joueur(rs.getString("nom"), rs.getString("prenom"), rs.getInt("score")));
            }
        }
        return liste;
    }
    @Override
    public joueur afficherpremierjoueur(int score) throws SQLException {
        List<joueur> joueurs = trierListejoueur();
            joueur premierJoueur = joueurs.get(0);
            
        return premierJoueur;} 
        	
    public String ajouterjoueur(joueur e) throws SQLException {
        String queryCheckExistence = "SELECT COUNT(*) AS count FROM joueur WHERE nom = ? AND prenom = ? AND score = ?";
        String queryInsert = "INSERT INTO joueur (nom, prenom, score) VALUES (?, ?, ?)";
        String queryDelete = "DELETE FROM joueur WHERE nom = ? AND prenom = ? AND score < ?";
        String mess = ""; 

       
        PreparedStatement psCheckExistence = cnx.prepareStatement(queryCheckExistence);
        psCheckExistence.setString(1, e.getNom());
        psCheckExistence.setString(2, e.getPrenom());
        psCheckExistence.setInt(3, e.getScore());

        ResultSet rs = psCheckExistence.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("count");
            if (count > 0) {
                mess = "Le joueur existe déjà dans la base de données avec le même score.";
                return mess;
            } else {
                PreparedStatement psDelete = cnx.prepareStatement(queryDelete);
                psDelete.setString(1, e.getNom());
                psDelete.setString(2, e.getPrenom());
                psDelete.setInt(3, e.getScore());
                psDelete.executeUpdate();

                PreparedStatement psInsert = cnx.prepareStatement(queryInsert);
                psInsert.setString(1, e.getNom());
                psInsert.setString(2, e.getPrenom());
                psInsert.setInt(3, e.getScore());
                int rowsAffected = psInsert.executeUpdate();
                if (rowsAffected > 0) {
                    mess = "Le joueur a été ajouté avec succès.";
                } else {
                    mess = "Une erreur s'est produite lors de l'ajout du joueur.";
                }
                return mess;
            }
        } else {
            mess = "Une erreur s'est produite lors de la vérification de l'existence du joueur.";
            return mess;
        }
    }




	

}
