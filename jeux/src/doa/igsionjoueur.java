package doa;

import java.sql.SQLException;
import java.util.List;

import metier.joueur;

public interface igsionjoueur {
    String ajouterjoueur(joueur e) throws SQLException;
    List<joueur> trierListejoueur() throws SQLException;
    joueur afficherpremierjoueur(int score) throws SQLException;
}
