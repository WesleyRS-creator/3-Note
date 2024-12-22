package tools.reflect;

import java.util.*;
import java.lang.reflect.*;
import java.sql.*;

public class FieldManager {
        public List<String> extractColumnsFromClass(Class<?> clazz) {
            List<String> columns = new ArrayList<>();
            try {
                for (Field field : clazz.getDeclaredFields()) {
                    columns.add(field.getName());
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de l'extraction des colonnes de la classe : " + e.getMessage());
            }
            return columns;
        }

        public Map<String, Class<?>> extractMapTypeFromClass(Class<?> clazz) {
            
            Map<String, Class<?>> answer = new HashMap<>();
            try {
                for (Field field : clazz.getDeclaredFields()) {
                    answer.put(field.getName(), field.getType());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return answer;
        }
        
    public List<String> getNumericAttributeNames(Class<?> clazz) {
        List<String> numericAttributes = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (fieldType == int.class || fieldType == double.class || fieldType == float.class || fieldType == long.class ||
                Number.class.isAssignableFrom(fieldType)) {
                numericAttributes.add(field.getName());
            }
        }

        return numericAttributes;
    }

    public List<String> extractMatchingColumns(Class<?> clazz, ResultSetMetaData metaData) throws Exception {
        List<String> matchingColumns = new ArrayList<>();

        // Récupérer tous les champs de la classe
        Field[] fields = clazz.getDeclaredFields();

        // Parcourir les colonnes des métadonnées
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i).toLowerCase();

            // Vérifier si le nom de la colonne correspond à un champ de la classe
            for (Field field : fields) {
                if (field.getName().equalsIgnoreCase(columnName)) {
                    matchingColumns.add(columnName); // Ajouter le nom correspondant
                    break;
                }
            }
        }

        return matchingColumns;
    }
}
