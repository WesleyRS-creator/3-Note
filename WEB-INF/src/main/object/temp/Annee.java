package main.object.temp;

public class Annee {
    private int id_annee;
    private int annee_debut;
    private int annee_fin;
    private int montant;

    public void setId_annee(int id_annee) {
        this.id_annee = id_annee;
    }

    public void setAnnee_debut(int annee_debut) {
        this.annee_debut = annee_debut;
    }
    
    public void setAnnee_fin(int annee_fin) {
        this.annee_fin = annee_fin;
    }

    public int getId_annee() {
        return id_annee;
    }

    public int getAnnee_debut() {
        return annee_debut;
    }

    public int getAnnee_fin() {
        return annee_fin;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getMontant() {
        return montant;
    }

    public void afficherAnnee() {
        System.out.println("    ID Année : " + getId_annee());
        System.out.println("    Année Début : " + getAnnee_debut());
        System.out.println("    Année Fin : " + getAnnee_fin());
        System.out.println("    Montant Total : " + getMontant());
    }

}
