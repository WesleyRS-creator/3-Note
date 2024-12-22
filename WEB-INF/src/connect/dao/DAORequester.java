package connect.dao;

import java.util.*;
import java.lang.reflect.*;

import tools.reflect.FieldManager;
import tools.util.MapManager;

public class DAORequester {
    private MapManager mapManager = new MapManager();
    private FieldManager  fieldManager = new FieldManager();

    public void setFieldManager(FieldManager fieldManager) {
        this.fieldManager = fieldManager;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public FieldManager getFieldManager() {
        return fieldManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public String requestStringGetterConstructor(String table, Class<?> cc, Map<String, Object> conditions, boolean hadBoolean) {
        StringBuilder query = new StringBuilder();
        Map<String, String> conditions_String = null;
        if (conditions != null) {
           conditions_String = this.getMapManager().convertValueToString(conditions, hadBoolean);
        }

        if (cc != null) {
            System.out.println("Bnjour");
        }

        try {
            query.append("SELECT ");

            if (cc instanceof Class<?>) {
                List<String> columns = this.getFieldManager().extractColumnsFromClass((Class<?>) cc);
                if (columns == null || columns.isEmpty()) {
                    query.append("*");
                } else {
                    query.append(String.join(", ", columns));
                }
            } else {
                throw new IllegalArgumentException("Le paramètre cc doit être un tableau de colonnes ou une classe.(si null, * default)");
            }

            query.append(" FROM ").append(table);

            if (conditions_String != null && !conditions_String.isEmpty()) {
                query.append(" WHERE ");
                int index = 0;
                for (Map.Entry<String, String> entry : conditions_String.entrySet()) {
                    query.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
                    if (index < conditions_String.size() - 1) {
                        query.append(" AND ");
                    }
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la construction de la requête SELECT : " + e.getMessage());
        }

        return query.toString();
    }

    

    public String generateSelectQuery(String table, List<String> columnsToRetrieve, Map<String, Object> conditions) {
        if (table == null || table.isEmpty()) {
            throw new IllegalArgumentException("Le nom de la table est requis.");
        }
    
        StringBuilder query = new StringBuilder();
    
        // Clause SELECT
        query.append("SELECT ");
        if (columnsToRetrieve != null && !columnsToRetrieve.isEmpty()) {
            query.append(String.join(", ", columnsToRetrieve)); // Colonnes spécifiées
        } else {
            query.append("*"); // Toutes les colonnes
        }
    
        // Clause FROM
        query.append(" FROM ").append(table);
    
        // Clause WHERE
        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            int index = 0;
            for (Map.Entry<String, Object> entry : conditions.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
    
                // Vérifie si la valeur est une chaîne, un entier, etc.
                if (value instanceof String) {
                    query.append(key).append(" = '").append(value).append("'");
                } else if (value instanceof Number || value instanceof Boolean) {
                    query.append(key).append(" = ").append(value);
                } else {
                    throw new IllegalArgumentException("Type de valeur non pris en charge pour la condition : " + value.getClass());
                }
    
                // Ajoute "AND" entre les conditions, sauf pour la dernière
                if (index < conditions.size() - 1) {
                    query.append(" AND ");
                }
                index++;
            }
        }
    
        return query.toString();
    }

        // Construit la requête SQL en fonction des conditions
        public String getAColumnFrom(String table, String columnName, Map<String, Object> conditions) {
            StringBuilder query = new StringBuilder("SELECT " + columnName + " FROM " + table);
    
            if (conditions != null && !conditions.isEmpty()) {
                query.append(" WHERE ");
                conditions.forEach((key, value) -> {
                    query.append(key).append(" = '").append(value).append("' AND ");
                });
                query.setLength(query.length() - 5); // Retire le dernier ' AND'
            }
    
            return query.toString();
        }
    
    

    public String generateInsertQuery(String table, Object o) throws IllegalAccessException {
        if (table == null || table.isEmpty()) {
            throw new IllegalArgumentException("Le nom de la table ne peut pas être null ou vide.");
        }
        if (o == null) {
            throw new IllegalArgumentException("L'objet à insérer ne peut pas être null.");
        }

        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        String idFieldName = "id" + table.substring(0, table.length() - 1); 
        idFieldName = idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(o);


            if (value != null && !field.getName().equalsIgnoreCase(idFieldName)) {
                if (columns.length() > 0) {
                    columns.append(", ");
                    values.append(", ");
                }
                columns.append(field.getName());


                if (value instanceof String || value instanceof Character) {
                    values.append("'").append(value).append("'");
                } else {
                    values.append(value);
                }
            }
        }

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, values);
        System.out.println("Generated query: " + query);

        return query;
    }

    public String buildAggregateQuery(String tableName, List<String> numberColumns, String operation) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        for (int i = 0; i < numberColumns.size(); i++) {
            queryBuilder.append(operation.toUpperCase()).append("(").append(numberColumns.get(i)).append(")");
            if (i < numberColumns.size() - 1) {
                queryBuilder.append(", ");
            }
        }

        queryBuilder.append(" FROM ").append(tableName);
        return queryBuilder.toString();
    }

    public String buildQuery(String tableName, Map<String, Object> conditions) {
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(tableName);
    
        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            int count = 0;
            for (String key : conditions.keySet()) {
                if (count > 0) {
                    query.append(" AND ");
                }
                query.append(key).append(" = ?");
                count++;
            }
        }
    
        return query.toString();
    }


}
