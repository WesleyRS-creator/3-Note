package connect.dao;

import java.util.*;

import java.sql.*;
import java.time.LocalDate;
import java.lang.reflect.*;

import tools.reflect.FieldManager;
import tools.reflect.GenericObject;
import tools.reflect.MethodManager;

public class DAOExecuter {
    private DAORequester daoR;
    private final Map<String, Connection> connections;

    public DAOExecuter(Map<String, Connection> connections) {
        daoR = new DAORequester();
        if (connections == null || connections.isEmpty()) {
            throw new IllegalArgumentException("La map de connexions ne peut pas être null ou vide.");
        }
        this.connections = connections;
    }

    public Map<String, Connection> getConnections() {
        return connections;
    }

    public List<Object> getFROM(String dataType, String table, Class<?> cc, Map<String, Object> conditions, boolean hadBoolean) {
        FieldManager f = new FieldManager();
        List<Object> answer = new ArrayList<>();
        Connection conn = connections.get(dataType.toUpperCase());

        if (conn == null) {
            throw new IllegalArgumentException("dataBase " + dataType + " non trouvée");
        }

        String request = daoR.requestStringGetterConstructor(table, cc, conditions, hadBoolean);
        System.out.println("request " + request);

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(request);

            // if (rs == null) {
            //     return null;
            // }

                Map<String, Class<?>> fields = f.extractMapTypeFromClass(cc);

                while (rs.next()) {
                    Object instance = cc.getDeclaredConstructor().newInstance();
                
                    for (Map.Entry<String, Class<?>> field : fields.entrySet()) {
                        String fieldName = field.getKey();
                        Class<?> fieldType = field.getValue();
                
                        Field classField = cc.getDeclaredField(fieldName);
                        classField.setAccessible(true);
                
                        Object value = null;
                        try {
                            MethodManager mMgr = new MethodManager();
                            Method rsMethod = mMgr.findResultSetGetter(rs, fieldType);
                            if (rsMethod != null) {
                                value = rsMethod.invoke(rs, fieldName);
                            } else {
                                value = rs.getObject(fieldName);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                
                        classField.set(instance, value);
                    }
                    answer.add(instance);
                }
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(stmt, rs);
        }

        return answer;
    }

    public List<GenericObject> fetchTableData(String dataType, String tableName, Map<String, Object> conditions) {
        Connection conn = connections.get(dataType.toUpperCase());
        if (conn == null) {
            throw new IllegalArgumentException("La connexion pour " + dataType + " est introuvable.");
        }
    
        List<GenericObject> objects = new ArrayList<>();
        String query = daoR.buildQuery(tableName, conditions);
    
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Étape 1 : Remplir les paramètres de la requête
            if (conditions != null) {
                int index = 1;
                for (Object value : conditions.values()) {
                    stmt.setObject(index++, value);
                }
            }
    
            // Étape 2 : Exécuter la requête
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                // Étape 3 : Parcourir les résultats
                while (rs.next()) {
                    GenericObject genericObject = new GenericObject();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(columnName);
                        genericObject.set(columnName, value);
                    }
                    objects.add(genericObject);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des données : " + e.getMessage());
            e.printStackTrace();
        }
    
        return objects;
    }

        // Récupère une valeur spécifique d'une colonne d'une table
        public Object getColumnData(String dataType, String table, String columnName, Map<String, Object> conditions) {
            FieldManager f = new FieldManager();
            Object result = null;
            Connection conn = connections.get(dataType.toUpperCase());
    
            if (conn == null) {
                throw new IllegalArgumentException("dataBase " + dataType + " non trouvée");
            }
    
            String request = daoR.getAColumnFrom(table, columnName, conditions);
            System.out.println("request: " + request);
    
            Statement stmt = null;
            ResultSet rs = null;
    
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(request);
    
                if (rs.next()) {
                    result = rs.getObject(columnName);  // Récupère la valeur de la colonne spécifique
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeResources(stmt, rs);
            }
    
            return result;
        }

 
    

    public void insertInto(String dataType, String table, Object o) {
        Connection conn = connections.get(dataType.toUpperCase());

        if (conn == null) {
            throw new IllegalArgumentException("Connection for database type " + dataType + " not found.");
        }

        PreparedStatement pstmt = null;

        try {
            String sql = daoR.generateInsertQuery(table, o);

            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

            System.out.println("Insertion réussie dans la table " + table);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'insertion de l'objet : " + e.getMessage());
        } finally {
            closeResources(pstmt, null);
        }
    }

    public Map<String, Number> calculateColumnValue(String dataType, String tableName, String operation) throws SQLException {
        List<String> numberColumns = getNumericColumns(dataType, tableName);
        Map<String, Number> resultMap = new HashMap<>();

        Connection conn = connections.get(dataType.toUpperCase());
        if (conn == null) {
            throw new IllegalArgumentException("Connection for database type " + dataType + " not found.");
        }

        String query = daoR.buildAggregateQuery(tableName, numberColumns, operation);
        System.out.println("Exécution de la requête : " + query);

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                for (int i = 0; i < numberColumns.size(); i++) {
                    String columnName = numberColumns.get(i);
                    
                    Number value = rs.getDouble(i + 1);
                    

                    if (value != null) {
                        resultMap.put(columnName, value);
                    }
                }
            }

        } finally {
            closeResources(stmt, rs);
        }

        return resultMap;
    }

    public int getRowCount(String dataType, String tableName) throws SQLException {
        Connection conn = connections.get(dataType.toUpperCase());
        
        if (conn == null) {
            throw new IllegalArgumentException("Connection for database type " + dataType + " not found.");
        }
        
        String query = "SELECT COUNT(*) FROM " + tableName;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving row count from table " + tableName, e);
        } finally {
            closeResources(stmt, rs);
        }
        
        return 0;
    }

    public List<String> getNumericColumns(String dataType, String tableName) {
        Connection connection = connections.get(dataType.toUpperCase());
        List<String> numericColumns = new ArrayList<>();
        String query = "";

        if (dataType.equalsIgnoreCase("MYSQL")) {
            query = "SELECT COLUMN_NAME " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_NAME = ? " +
                    "AND TABLE_SCHEMA = DATABASE() " +
                    "AND DATA_TYPE IN ('int', 'bigint', 'decimal', 'float', 'double') " +
                    "AND COLUMN_NAME NOT LIKE 'id%'";
        } else if (dataType.equalsIgnoreCase("ORACLE")) {
            query = "SELECT COLUMN_NAME " +
                    "FROM ALL_TAB_COLUMNS " +
                    "WHERE TABLE_NAME = UPPER(?) " +
                    "AND OWNER = USER " +
                    "AND DATA_TYPE IN ('NUMBER', 'FLOAT', 'BINARY_FLOAT', 'BINARY_DOUBLE') " +
                    "AND COLUMN_NAME NOT LIKE 'ID%'";
        } else {
                    throw new IllegalArgumentException("Unsupported database type: " + dataType);
                }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, tableName.toUpperCase());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                numericColumns.add(rs.getString("COLUMN_NAME").toUpperCase());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        } finally {
            closeResources(pstmt, rs);
        }

        return numericColumns;
    }

    
    public void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture du ResultSet : " + e.getMessage());
        }
    
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
        }
    }
}

