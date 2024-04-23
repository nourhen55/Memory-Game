package metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class igsionjoueur implements ijoueur {

    protected List<joueur> listeJoueurs;

    public igsionjoueur() {
        listeJoueurs = new ArrayList<>();
    }

    @Override
    public void ajouterJoueur(joueur e) {
        listeJoueurs.add(e);
    }

    @Override
    public void trierListeJoueur() {
        Collections.sort(listeJoueurs, (j1, j2) -> Integer.compare(j2.getScore(), j1.getScore()));
    }

    @Override
    public void afficherPremierJoueur(int score) {
        trierListeJoueur();
        if (!listeJoueurs.isEmpty()) {
            joueur premierJoueur = listeJoueurs.get(0);
            System.out.println("Nom: " + premierJoueur.getNom() + ", Score: " + premierJoueur.getScore());
        } else {
            System.out.println("Aucun joueur trouvé.");
        }
    }

    public void toutLesMethodes(joueur e) {
        ajouterJoueur(e);
        trierListeJoueur();
        afficherPremierJoueur(e.getScore());
    }
}

