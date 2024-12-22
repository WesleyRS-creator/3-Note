package connect.dao.object.finance;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connect.dao.DAOExecuter;
import main.object.finance.Mode_Paiement;
import main.object.personne.Etudiant;
import tools.reflect.GenericObject;

public class Mode_paiementDAO {
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

    // Méthode pour initialiser les connexions et la classe Mode_Paiement
    public void setMyConnections(Map<String, Connection> myConnections) {
        Mode_Paiement mp = new Mode_Paiement(); // Crée une instance de Mode_Paiement
        setC(mp.getClass());         // Définit la classe comme étant Mode_Paiement
        this.myConnections = myConnections;
    }

    // Récupère les connexions
    public Map<String, Connection> getMyConnections() {
        return myConnections;
    }

    // Méthode pour récupérer un mode de paiement par ID_MODE_PAIEMENT
    public Mode_Paiement getMode_PaiementById(int idMode_Paiement) {
        // Crée une instance de DAOExecuter
        DAOExecuter daoE = new DAOExecuter(getMyConnections());

        // Crée un map pour passer la condition ID_MODE_PAIEMENT
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("ID_MODE_PAIEMENT", idMode_Paiement);

        // Utilise fetchTableData pour récupérer les données génériques de la table MODE_PAIEMENT
        List<GenericObject> genericObjects = daoE.fetchTableData("mysql", "MODE_PAIEMENT", conditions);

        if (genericObjects.isEmpty()) {
            return null; // Si aucun mode de paiement n'est trouvé, on retourne null
        }

        // On suppose qu'il n'y a qu'un seul mode de paiement avec cet ID, on prend donc le premier élément
        GenericObject genericObject = genericObjects.get(0);

        // Crée une instance de Mode_Paiement
        Mode_Paiement Mode_Paiement = new Mode_Paiement();

        // Récupère les valeurs du GenericObject et assigne-les à l'objet Mode_Paiement
        Integer id = (Integer) genericObject.get("ID_MODE_PAIEMENT");
        String nomMode = (String) genericObject.get("NOM_MODE");
        Integer nbrMois = (Integer) genericObject.get("NBR_MOIS");

        // Assigner les valeurs récupérées au mode de paiement
        Mode_Paiement.setId_mode_paiement(id);
        Mode_Paiement.setNom_mode(nomMode);
        Mode_Paiement.setnbr_Mois(nbrMois);

        // Retourne l'objet Mode_Paiement
        return Mode_Paiement;
    }

    public List<Mode_Paiement> getAllMode_Paiement() {
        // Crée une instance de DAOExecuter
        DAOExecuter daoE = new DAOExecuter(getMyConnections());

        // Utilise fetchTableData pour récupérer toutes les données de la table MODE_PAIEMENT
        List<GenericObject> genericObjects = daoE.fetchTableData("mysql", "MODE_PAIEMENT", null);

        // Liste pour stocker les modes de paiement
        List<Mode_Paiement> modePaiements = new ArrayList<>();

        // Parcourt chaque GenericObject pour le transformer en Mode_Paiement
        for (GenericObject genericObject : genericObjects) {
            // Crée une instance de Mode_Paiement
            Mode_Paiement modePaiement = new Mode_Paiement();

            // Récupère les valeurs du GenericObject et les assigne à l'objet Mode_Paiement
            Integer id = (Integer) genericObject.get("ID_MODE_PAIEMENT");
            String nomMode = (String) genericObject.get("NOM_MODE");
            Integer nbrMois = (Integer) genericObject.get("NBR_MOIS");

            modePaiement.setId_mode_paiement(id);
            modePaiement.setNom_mode(nomMode);
            modePaiement.setnbr_Mois(nbrMois);

            // Ajoute l'objet Mode_Paiement à la liste
            modePaiements.add(modePaiement);
        }

        // Retourne la liste des modes de paiement
        return modePaiements;
    }

    // Constructeur pour initialiser les connexions
    public Mode_paiementDAO(Map<String, Connection> mc) {
        setMyConnections(mc);
    }
}
