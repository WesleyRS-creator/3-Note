package main.object.personne;

import java.util.*;

import main.object.finance.Ecolage;
import main.object.finance.Mode_Paiement;
import main.object.temp.Annee;
import main.object.evaluation.Note;

public class Etudiant {
    private int id_etudiant;
    private String nom_etudiant;
    private Mode_Paiement mode_paiement;
    private List<Ecolage> ecolage_list;
    private List<Note> note_list;

    public void setId_etudiant(int id){
        this.id_etudiant = id;
    }

    public void setNom_Etudiant(String nom){
        this.nom_etudiant = nom;
    }

    public void setEcolage_list(List<Ecolage> e_l){
        this.ecolage_list = e_l;
    }

    public void setNote_list(List<Note> n_l){
        this.note_list = n_l;
    }

    public String getNom_etudiant(){
        return this.nom_etudiant;
    }

    public int getId_etudiant(){
        return this.id_etudiant;
    }

    public List<Ecolage> getEcolage_list(){
        return this.ecolage_list;
    }

    public List<Note> getNote_list(){
        return this.note_list;
    }

    public void setMode_paiement(Mode_Paiement mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public void setNom_etudiant(String nom_etudiant) {
        this.nom_etudiant = nom_etudiant;
    }

    public Mode_Paiement getMode_paiement() {
        return mode_paiement;
    }


        // Méthode pour calculer le total payé pour une année donnée
        private int calculerTotalPayePourAnnee(Annee annee) {
            return ecolage_list.stream()
                    .filter(ecolage -> ecolage.estAssocieA(annee))
                    .mapToInt(Ecolage::getMontant)
                    .sum();
        }
    
        // Méthode pour vérifier si le paiement d'un semestre est suffisant
        public boolean peutVoirSemestre(Annee annee, int semestre) {
            int montantRequisParSemestre = annee.getMontant() / 2; // Montant pour 6 mois
            int totalPaye = calculerTotalPayePourAnnee(annee);
    
            // Si c'est le premier semestre, on vérifie la moitié
            if (semestre == 1) {
                return totalPaye >= montantRequisParSemestre;
            }
    
            // Si c'est le deuxième semestre, on vérifie l'intégralité
            if (semestre == 2) {
                return totalPaye >= annee.getMontant();
            }
    
            throw new IllegalArgumentException("Semestre invalide : " + semestre);
        }

        public void afficherEtudiant() {
            System.out.println("ID Étudiant : " + id_etudiant);
            System.out.println("Nom Étudiant : " + nom_etudiant);
    
            // Affichage du mode de paiement
            if (mode_paiement != null) {
                System.out.println("Mode de Paiement :");
                mode_paiement.afficherModePaiement();
            } else {
                System.out.println("Mode de Paiement : Aucun");
            }
    
            // Affichage des écolages
            System.out.println("Liste des Écolages :");
            if (ecolage_list != null && !ecolage_list.isEmpty()) {
                for (Ecolage ecolage : ecolage_list) {
                    ecolage.afficherEcolage();
                }
            } else {
                System.out.println("Aucun écolage.");
            }
    
            // Affichage des notes
            System.out.println("Liste des Notes :");
            if (note_list != null && !note_list.isEmpty()) {
                for (Note note : note_list) {
                    note.afficherNote();
                }
            } else {
                System.out.println("Aucune note.");
            }
        }

        public String generateEtudiantForm(List<Mode_Paiement> modes) {
            StringBuilder formBuilder = new StringBuilder();
    
            // Début du formulaire
            formBuilder.append("<form action=\"/insertEtudiant\" method=\"post\">");
    
            // Champ pour le nom de l'étudiant
            formBuilder.append("<label for=\"nom\">Nom de l'étudiant :</label>");
            formBuilder.append("<input type=\"text\" id=\"nom\" name=\"nom_etudiant\" required><br><br>");
    
            // Menu déroulant pour les modes de paiement
            formBuilder.append("<label for=\"mode_paiement\">Mode de paiement :</label>");
            formBuilder.append("<select id=\"mode_paiement\" name=\"id_mode_paiement\" required>");
            
            // Ajouter une option pour chaque mode de paiement
            for (Mode_Paiement mode : modes) {
                formBuilder.append("<option value=\"").append(mode.getId_mode_paiement()).append("\">")
                           .append(mode.getNom_mode())
                           .append("</option>");
            }
    
            formBuilder.append("</select><br><br>");
    
            // Bouton de soumission
            formBuilder.append("<button type=\"submit\">Insérer l'étudiant</button>");
    
            // Fin du formulaire
            formBuilder.append("</form>");
    
            return formBuilder.toString();
        }
}
