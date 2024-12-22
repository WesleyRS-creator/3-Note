package main.object.etude;

public class Matiere {
    private int id_matiere;
    private String nom_matiere;
    private int credits;

    public void setId_matiere(int id_matiere) {
        this.id_matiere = id_matiere;
    }
    
    public void setNom_matiere(String nom_matiere) {
        this.nom_matiere = nom_matiere;
    }

    public void setCredits(int credit) {
        this.credits = credit;
    }

    public int getId_matiere() {
        return id_matiere;
    }

    public String getNom_matiere() {
        return nom_matiere;
    }

    public int getCredits() {
        return credits;
    }
}