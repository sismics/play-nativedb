package helpers.reflection;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.hibernate.ejb.AbstractQueryImpl;

import javax.persistence.Parameter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author jtremeaux
 */
public class ReflectionUtil {
    /**
     * Get a nested field value à la BeanUtils.
     *
     * @param obj The object to introspect
     * @param field The field to get, can be a nested field, e.g.: "address.streetName"
     * @return The value
     */
    public static Object getFieldValue(Object obj, String field) {
        String[] fields = field.split("\\.");
        Object fieldValue = getFieldSingleValue(obj, fields[0]);
        if (fields.length == 1) {
            return fieldValue;
        } else {
            List<String> remainingFields = Lists.newArrayList(fields);
            remainingFields.remove(0);
            return getFieldValue(fieldValue, Joiner.on(".").join(remainingFields));
        }
    }

    /**
     * Get a field value.
     *
     * @param obj The object to introspect
     * @param field The field to get
     * @return The value
     */
    public static Object getFieldSingleValue(Object obj, String field) {
        return getFieldSingleValue(obj, field, obj.getClass());
    }

    /**
     * Get a field value.
     *
     * @param obj The object to introspect
     * @param field The field to get
     * @param clazz The class
     * @return The value
     */
    public static Object getFieldSingleValue(Object obj, String field, Class clazz) {
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error getting field value", e);
        }
    }

    /**
     * Set a field value.
     *
     * @param obj The object to introspect
     * @param field The field to get
     * @param value The value to set
     * @return The value
     */
    public static void setFieldValue(Object obj, String field, Object value) {
        try {
            Field f = obj.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting field value", e);
        }
    }

    /**
     * Invoke a method.
     *
     * @param obj The object to introspect
     * @param method The method to invoke
     * @param args The method arguments
     * @return The value
     */
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
