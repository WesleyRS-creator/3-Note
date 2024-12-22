package tools.util;

import java.util.*;

public class MapManager {
        public Map<String, String> convertValueToString(Map<String, Object> conditions, Boolean hadBoolean) {
            Map<String, String> stringConditions = new HashMap<>();
            try {
                if (hadBoolean == null) {
                    hadBoolean = false;
                }
    
                for (Map.Entry<String, Object> entry : conditions.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
    
                    if (value instanceof String || value instanceof Number) {
                        stringConditions.put(key, value.toString());
                    } else if (value instanceof Boolean) {
                        stringConditions.put(key, hadBoolean ? (((Boolean) value) ? "TRUE" : "FALSE") : (((Boolean) value) ? "1" : "0"));
                    } else if (value instanceof java.util.Date) {
                        stringConditions.put(key, "'" + value.toString() + "'");
                    } else {
                        throw new IllegalArgumentException("Type non supporté pour la clé : " + key);
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la conversion des conditions : " + e.getMessage());
            }
    
            return stringConditions;
        }
}
