package main.object.evaluation;

import java.util.List;

import main.object.etude.Matiere;
import main.object.temp.Annee;

public class Note {
    private int id_note;
    private int semestre;
    private Annee annee;
    private Matiere matiere;
    private double note;


    public void setId_note(int id_note) {
        this.id_note = id_note;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public int getId_note() {
        return id_note;
    }

    public Matiere getMatiere() {
        return matiere;
    }
    
    public Annee getAnnee() {
        return annee;
    }

    public double getNote() {
        return note;
    }

    public int getSemestre() {
        return semestre;
    }

    public void afficherNote() {
        // Affichage des informations de la note
        System.out.println("Informations de la note:");
        System.out.println("--------------------------");
        System.out.println("ID Note : " + this.getId_note());
        System.out.println("Matière : " + this.getMatiere().getNom_matiere());
        System.out.println("Crédits : " + this.getMatiere().getCredits());
        System.out.println("Semestre : " + this.getSemestre());
        System.out.println("Note : " + this.getNote());
        System.out.println("Année académique : " + 
                           this.getAnnee().getAnnee_debut() + "-" + this.getAnnee().getAnnee_fin());
    }
    
    public String generateNotesForm(List<String> matieres, List<String> semestres, List<Annee> annees) {
        StringBuilder formBuilder = new StringBuilder();

        // Début du formulaire
        formBuilder.append("<form action=\"/insertNotes\" method=\"post\">");

        // Ajout des champs pour les matières
        for (String matiere : matieres) {
            formBuilder.append("<label for=\"").append(matiere).append("\">")
                       .append(matiere).append(" :</label>");
            formBuilder.append("<input type=\"number\" id=\"").append(matiere).append("\" name=\"notes[").append(matiere).append("]\" ")
                       .append("min=\"0\" max=\"20\" step=\"0.01\" required><br><br>");
        }

        // Menu déroulant pour le semestre
        formBuilder.append("<label for=\"semestre\">Semestre :</label>");
        formBuilder.append("<select id=\"semestre\" name=\"semestre\" required>");
        for (String semestre : semestres) {
            formBuilder.append("<option value=\"").append(semestre).append("\">").append(semestre).append("</option>");
        }
        formBuilder.append("</select><br><br>");

        // Menu déroulant pour l'année
        formBuilder.append("<label for=\"annee\">Année académique :</label>");
        formBuilder.append("<select id=\"annee\" name=\"id_annee\" required>");
        for (Annee annee : annees) {
            formBuilder.append("<option value=\"").append(annee.getId_annee()).append("\">")
                       .append(annee.getAnnee_debut()).append("-").append(annee.getAnnee_fin())
                       .append("</option>");
        }
        formBuilder.append("</select><br><br>");

        // Bouton de soumission
        formBuilder.append("<button type=\"submit\">Insérer les notes</button>");

        // Fin du formulaire
        formBuilder.append("</form>");

        return formBuilder.toString();
    }
}
