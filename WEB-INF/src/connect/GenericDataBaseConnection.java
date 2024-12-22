package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.*;

public class GenericDataBaseConnection {
    private final Map<String, Connection> Myconnections = new HashMap<>();

    public void openConnection(String dbName, String urlString, String user, String password) throws SQLException {
        try {
            if (dbName.equalsIgnoreCase("MYSQL")) { // MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
            } else if (dbName.equalsIgnoreCase("ORACLE")) { // Oracle
                Class.forName("oracle.jdbc.OracleDriver");
            } else {
                throw new IllegalArgumentException("Type de base de données " + dbName + " non reconnu.");
            }
            System.out.println("Chargement du driver " + dbName.toUpperCase() + "...");
            Myconnections.put(dbName.toUpperCase(), DriverManager.getConnection(urlString, user, password));
            System.out.println("Connexion " + dbName.toUpperCase() + " ouverte avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ouverture de la connexion à " + dbName.toUpperCase() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeAllConnection() {
        for (Map.Entry<String, Connection> entry : Myconnections.entrySet()) {
            try {
                if (entry.getValue() != null && !entry.getValue().isClosed()) {
                    entry.getValue().close();
                    System.out.println("Connexion " + entry.getKey() + " fermée avec succès.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion " + entry.getKey() + ": " + e.getMessage());
            }
        }
        Myconnections.clear();
    }

    public void closeConnection(String dbName) {
        Connection connection = Myconnections.get(dbName.toUpperCase());
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Connexion " + dbName + " fermée avec succès.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion " + dbName + ": " + e.getMessage());
            } finally {
                Myconnections.remove(dbName.toUpperCase());
            }
        } else {
            System.out.println("Aucune connexion active trouvée pour " + dbName + ".");
        }
    }

    public Connection getConnection(String dbName) {
        return Myconnections.get(dbName.toUpperCase());
    }

    public Map<String, Connection> getAllConnection() {
        return Myconnections;
    }
}
