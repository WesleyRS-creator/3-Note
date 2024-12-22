package main.object.finance;

import main.object.temp.Annee;

public class Ecolage {
    private int id_ecolage;
    private int montant;
    private Annee annee;

    public void setId_ecolage(int id_ecolage) {
        this.id_ecolage = id_ecolage;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getMontant() {
        return montant;
    }

    public int getId_ecolage() {
        return id_ecolage;
    }

    public Annee getAnnee() {
        return annee;
    }

    public boolean estPayePourPeriode(int montantRequis) {
        return this.montant >= montantRequis;
    }

    public boolean estAssocieA(Annee annee) {
        return this.annee.getId_annee() == annee.getId_annee();
    }

    // Méthode pour afficher les détails d'un écolage
    public void afficherEcolage() {
        System.out.println("  ID Écolage : " + id_ecolage);
        System.out.println("  Montant : " + montant);
        if (annee != null) {
            System.out.println("  Année :");
            annee.afficherAnnee();
        } else {
            System.out.println("  Année : Non spécifiée");
        }
    }
}