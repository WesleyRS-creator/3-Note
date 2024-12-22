package connect.dao.object.finance;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connect.dao.DAOExecuter;
import connect.dao.object.temp.AnneeDAO;
import main.object.finance.*;
import main.object.temp.Annee;
import tools.reflect.GenericObject;

public class EcolageDAO {
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

    // Méthode pour initialiser les connexions et la classe Ecolage
    public void setMyConnections(Map<String, Connection> myConnections) {
        Ecolage ec = new Ecolage(); // Crée une instance de Ecolage
        setC(ec.getClass());         // Définit la classe comme étant Ecolage
        this.myConnections = myConnections;
    }

    // Récupère les connexions
    public Map<String, Connection> getMyConnections() {
        return myConnections;
    }

 // Méthode pour récupérer les écotages d'un étudiant par ID_ETUDIANT
 public List<Ecolage> getEcolagesByEtudiantId(int idEtudiant, int idAnnee) {
    // Crée une instance de DAOExecuter
    DAOExecuter daoE = new DAOExecuter(getMyConnections());

    // Crée un map pour passer la condition ID_ETUDIANT
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("ID_ETUDIANT", idEtudiant);
    conditions.put("ID_ANNEE", idAnnee);
    

    // Utilise fetchTableData pour récupérer les données génériques de la table ECOLAGE
    List<GenericObject> genericObjects = daoE.fetchTableData("mysql", "ECOLAGE", conditions);

    List<Ecolage> ecolagesList = new ArrayList<>();

    // Pour chaque objet générique, construisez un objet Ecolage et associez l'année
    for (GenericObject genericObject : genericObjects) {
        // Crée une instance de Ecolage
        Ecolage ecolage = new Ecolage();

        // Récupère les valeurs du GenericObject et assigne-les à l'objet Ecolage
        Integer idEcolage = (Integer) genericObject.get("ID_ECOLAGE");
        Integer montant = (Integer) genericObject.get("MONTANT");

        // Assigner les valeurs récupérées à l'écotage
        ecolage.setId_ecolage(idEcolage);

        ecolage.setMontant(montant);

        // Utiliser l'ID_ANNEE pour récupérer l'objet Annee
        AnneeDAO anneeDAO = new AnneeDAO(getMyConnections());
        Annee annee = anneeDAO.getAnneeById(idAnnee);

        // Assigner l'année à l'écotage
        ecolage.setAnnee(annee);

        // Ajouter l'écotage à la liste
        ecolagesList.add(ecolage);
    }

    // Retourne la liste des écotages
    return ecolagesList;
}

    // Constructeur pour initialiser les connexions
    public EcolageDAO(Map<String, Connection> mc) {
        setMyConnections(mc);
    }
}
