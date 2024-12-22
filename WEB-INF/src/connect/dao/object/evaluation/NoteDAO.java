package connect.dao.object.evaluation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import connect.dao.DAOExecuter;
import connect.dao.object.etude.MatiereDAO;
import connect.dao.object.temp.AnneeDAO;
import main.object.etude.Matiere;
import main.object.evaluation.Note;
import main.object.temp.Annee;
import tools.reflect.GenericObject;

public class NoteDAO {
    private Map<String, Connection> myConnections;
    private Class<?> c;

    public void setC(Class<?> c) {
        this.c = c;
    }

    public Class<?> getC() {
        return c;
    }

    public void setMyConnections(Map<String, Connection> myConnections) {
        Note aa = new Note();
        setC(aa.getClass());
        this.myConnections = myConnections;
    }

    public Map<String, Connection> getMyConnections() {
        return myConnections;
    }

    public List<Note> getNotes(Map<String, Object> conditions) {
        // Étape 1 : Préparer la requête dynamique
        DAOExecuter daoE = new DAOExecuter(getMyConnections());
        List<GenericObject> answer = daoE.fetchTableData("oracle", "notes", conditions);

        if (answer.isEmpty()) {
            return null; // Aucun résultat trouvé
        }

        // Étape 2 : Parcourir les résultats pour créer les objets Note
        List<Note> notesList = new ArrayList<>();
        for (GenericObject obj : answer) {
            // Récupérer les champs nécessaires
            Number idNote = (Number) obj.get("ID_NOTE");
            Number idMatiere = (Number) obj.get("ID_MATIERE");
            Number semestre = (Number) obj.get("SEMESTRE");
            Number noteValeur = (Number) obj.get("NOTE");
            Number idAnnee = (Number) obj.get("ID_ANNEE");

            // Récupérer la matière associée via son ID
            MatiereDAO matiereDAO = new MatiereDAO(myConnections);
            Matiere matiere = matiereDAO.getMatiereById(idMatiere.intValue());

            // Récupérer l'année via son ID
            AnneeDAO anneeDAO = new AnneeDAO(myConnections);
            Annee annee = anneeDAO.getAnneeById(idAnnee.intValue());

            // Créer et configurer l'objet Note
            Note note = new Note();
            note.setId_note(idNote.intValue());
            note.setMatiere(matiere);
            note.setSemestre(semestre.intValue());
            note.setNote(noteValeur.doubleValue());
            note.setAnnee(annee);

            // Ajouter à la liste
            notesList.add(note);
        }

        return notesList;
    }

    public NoteDAO(Map<String, Connection> connMap){
        setMyConnections(connMap);
    }
}
