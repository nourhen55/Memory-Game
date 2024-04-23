package metier;

public class joueur {
    private String nom;
    private String prenom;
    private int score;

    public joueur(String nom, String prenom, int score) {
        this.nom = nom;
        this.prenom = prenom;
        this.score = score;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
