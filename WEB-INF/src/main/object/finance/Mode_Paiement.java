package main.object.finance;

import main.object.temp.Annee;

public class Mode_Paiement {
    private int id_mode_paiement;
    private String nom_mode;
    private int nbr_mois;

    public void setId_mode_paiement(int id_mode_paiement) {
        this.id_mode_paiement = id_mode_paiement;
    }

    public int getId_mode_paiement() {
        return id_mode_paiement;
    }

    public void setNom_mode(String nom_mode) {
        this.nom_mode = nom_mode;
    }

    public String getNom_mode() {
        return nom_mode;
    }

    public void setnbr_Mois(int nbr_Mois) {
        this.nbr_mois = nbr_Mois;
    }

    public int getnbr_Mois() {
        return nbr_mois;
    }

    public int calculMontantMinimum(Annee annee) {
        return (annee.getMontant() / 12) * this.nbr_mois;
    }

    public void afficherModePaiement() {
        System.out.println("  ID Mode de Paiement : " + getId_mode_paiement());
        System.out.println("  Nom Mode : " + getNom_mode());
        System.out.println("  Nombre de Mois : " + getnbr_Mois());
    }

}
