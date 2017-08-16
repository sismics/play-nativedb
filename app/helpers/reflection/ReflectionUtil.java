package helpers.reflection;

import org.hibernate.ejb.AbstractQueryImpl;

import javax.persistence.Parameter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author jtremeaux
 */
public class ReflectionUtil {
    public static Object getFieldValue(Object obj, String field) {
        try {
            Field f = obj.getClass().getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error getting field value", e);
        }
    }

    public static void setFieldValue(Object obj, String field, Object value) {
        try {
            Field f = obj.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting field value", e);
        }
    }

    public static void invokeMethod(Object obj, String method, Object... args) {
        try {
            Method m = AbstractQueryImpl.class.getDeclaredMethod(method, Parameter.class, Object.class);
            m.setAccessible(true);
            m.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException("Error invoking method", e);
        }
    }
}
