package connect.dao.object.personne;

import main.object.evaluation.Note;
import main.object.finance.Ecolage;
import main.object.finance.Mode_Paiement;
import main.object.personne.Etudiant;
import tools.reflect.GenericObject;
import connect.dao.DAOExecuter;
import connect.dao.object.evaluation.NoteDAO;
import connect.dao.object.finance.EcolageDAO;
import connect.dao.object.finance.Mode_paiementDAO;

import java.util.*;
import java.sql.*;

public class EtudiantDAO {

    private Map<String, Connection> myConnections;
    private Class<?> c;

    // Méthode pour définir la classe de l'objet à récupérer
    public void setC(Class<?> c) {
        this.c = c;
    }

    // Méthode pour récupérer la classe
    public Class<?> getC() {
        return c;
    }

    // Méthode pour initialiser les connexions et la classe Etudiant
    public void setMyConnections(Map<String, Connection> myConnections) {
        Etudiant et = new Etudiant(); // Crée une instance de Etudiant
        setC(et.getClass());         // Définit la classe comme étant Etudiant
        this.myConnections = myConnections;
    }

    // Récupère les connexions
    public Map<String, Connection> getMyConnections() {
        return myConnections;
    }

    // Méthode pour récupérer un étudiant par ID_ETUDIANT
    public Etudiant getEtudiantById(int idEtudiant, int idAnnee) {
        // Crée une instance de DAOExecuter
        DAOExecuter daoE = new DAOExecuter(getMyConnections());

        // Crée un map pour passer la condition ID_ETUDIANT
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("ID_ETUDIANT", idEtudiant);

        // Utilise fetchTableData pour récupérer les données génériques de la table ETUDIANTS
        List<GenericObject> genericObjects = daoE.fetchTableData("mysql", "ETUDIANTS", conditions);

        if (genericObjects.isEmpty()) {
            return null; // Si aucun étudiant n'est trouvé, on retourne null
        }

        // On suppose qu'il n'y a qu'un seul étudiant avec cet ID, on prend donc le premier élément
        GenericObject genericObject = genericObjects.get(0);

        // Crée une instance de Etudiant
        Etudiant etudiant = new Etudiant();

        // Récupère les valeurs du GenericObject et assigne-les à l'objet Etudiant
        Integer id = (Integer) genericObject.get("ID_ETUDIANT");
        String nom = (String) genericObject.get("NOM_ETUDIANT");
        Integer idModePaiement = (Integer) genericObject.get("ID_MODE_PAIEMENT");

        Mode_paiementDAO mode_paiementDAO = new Mode_paiementDAO(myConnections);
        Mode_Paiement mode_Paiement = mode_paiementDAO.getMode_PaiementById(idModePaiement);

        NoteDAO noteDAO = new NoteDAO(myConnections);
        conditions.put("ID_ANNEE", idAnnee);
        List<Note> nl = noteDAO.getNotes(conditions);
        
        EcolageDAO ecolageDAO = new EcolageDAO(myConnections);
        List<Ecolage> el = ecolageDAO.getEcolagesByEtudiantId(idEtudiant, idAnnee);

        // Assigner les valeurs récupérées à l'étudiant
        etudiant.setId_etudiant(id);
        etudiant.setNom_Etudiant(nom);
        etudiant.setNote_list(nl);
        etudiant.setEcolage_list(el);
        etudiant.setMode_paiement(mode_Paiement);


        // Retourne l'objet Etudiant
        return etudiant;
    }

    // Constructeur pour initialiser les connexions
    public EtudiantDAO(Map<String, Connection> mc) {
        setMyConnections(mc);
    }
    
}
