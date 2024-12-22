package tools.reflect;

import java.lang.reflect.Method;
import java.sql.ResultSet;

public class MethodManager {
    public Method findResultSetGetter(ResultSet rs, Class<?> fieldType) {
        for (Method method : ResultSet.class.getMethods()) {
            if (method.getParameterCount() == 1 
                    && method.getParameterTypes()[0] == String.class 
                    && method.getReturnType().isAssignableFrom(fieldType)) {
                return method;
            }
        }
        return null;
    }

}
