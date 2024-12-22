import java.sql.Connection;
import java.util.*;
import java.lang.reflect.*;



import connect.GenericDataBaseConnection;
import connect.dao.DAOExecuter;
import connect.dao.object.etude.MatiereDAO;
import connect.dao.object.evaluation.NoteDAO;
import connect.dao.object.finance.EcolageDAO;
import connect.dao.object.finance.Mode_paiementDAO;
import connect.dao.object.personne.EtudiantDAO;
import connect.dao.object.temp.AnneeDAO;
import tools.reflect.FieldManager;

import main.object.personne.Etudiant;
import main.object.etude.Matiere;
import main.object.evaluation.Note;
import main.object.finance.Ecolage;
import main.object.finance.Mode_Paiement;
import main.object.temp.Annee;


public class Main {

    public static void displayCalculationResult(Map<String, Number> resultMap) {
        for (Map.Entry<String, Number> entry : resultMap.entrySet()) {
            String columnName = entry.getKey();
            Number value = entry.getValue();
            
            System.out.println("Colonne : " + columnName + " | Valeur : " + value);
        }
    }
    public static void main(String[] args) {
        GenericDataBaseConnection gdbc = new GenericDataBaseConnection();

        String urlMySQL = "jdbc:mysql://localhost:3306/school_admin";
        String userMySQL = "root";
        String passwordMySQL = "";

        String urlORCL = "jdbc:oracle:thin:@localhost:1521:orcl";
        String userORCL = "school_admin";
        String passwordORCL = "school";

        Etudiant e = new Etudiant();

        try {
            gdbc.openConnection( "mysql", urlMySQL, userMySQL, passwordMySQL);
            //DAOExecuter dE = new DAOExecuter(gdbc.getAllConnection());
            gdbc.openConnection("oracle", urlORCL, userORCL, passwordORCL);




            // Map<String, Connection> mc = dE.getConnections();
            // FieldManager f = new FieldManager();

            // Map<String, Class<?>> cc = f.extractMapTypeFromClass(e.getClass());
            
            // Matiere m = new Matiere();
            // Etudiant et = new Etudiant();
            // // DatabaseMetaData metaData = gdbc.getConnection("oracle").getMetaData();
            // // System.out.println("Driver Name: " + metaData.getDriverName());
            
            // Map<String, Object> lo = new HashMap<>();
            // lo.put("admis", false);

            // //List<Object> lO = dE.getFROM( "mysql", "Etudiants", et.getClass(), lo, false);
            // //Matiere m1 = new Matiere(0, "Français", 4, 2);
            // Map<String, Number> moyenne = dE.calculateColumnValue("mysql", "etudiants", "SUM");
            // //somme = dE.calculateColumnValue("mysql", tableName, "SUM");
            
            // Map<String, Number> mn = dE.calculateColumnValue("oracle", "Matieres", "AVG");
            // displayCalculationResult(mn);
            // //int c = dE.getRowCount("oracle", "Notes");
            // //System.out.println(c);

            EtudiantDAO ecolageDAO = new EtudiantDAO(gdbc.getAllConnection());
            Etudiant eco = ecolageDAO.getEtudiantById(1, 2);
            if (eco == null) {
                System.out.println("valeur nulle");
            }
            eco.afficherEtudiant();

            // //List<String> sL = dE.getNumberColumns("oracle", "Matieres");
            // // for (int i = 0; i < sL.size(); i++) {
            // //     System.out.println(sL.get(i));
            // // }

            // //dE.insertInto("oracle", "Matieres", m1);
            // // for (Object object : lO) {
            // //     Etudiant ma = (Etudiant) object;
            // //     System.out.println(ma.getIdEtudiant() + " - " +  ma.getNomEtudiant() + " : " + ma.getMoyenneAvecCoeff());
            // // }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //gdbc.closeConnection("mysql");
            gdbc.closeAllConnection();
        }
    }
}
