package tools.reflect;

import java.util.HashMap;
import java.util.Map;

public class GenericObject {
    private Map<String, Object> data = new HashMap<>();

    public void set(String field, Object value) {
        data.put(field, value);
    }

    public Object get(String field) {
        return data.get(field);
    }
}
