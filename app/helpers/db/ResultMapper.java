package helpers.db;

import org.apache.commons.io.IOUtils;
import org.hibernate.engine.jdbc.SerializableClobProxy;

import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps a result set to a DTO.
 *
 * @author jtremeaux
 */
public abstract class ResultMapper<T> {
    public abstract T map(Object[] cols);

    public List<T> map(List<Object[]> rows) {
        // Map all results
        List<T> resultList = rows.stream()
                .map(this::map)
                .collect(Collectors.toList());

        return resultList;
    }

    protected Character characterValue(Object o) {
        if (o instanceof Character) {
            return (Character) o;
        }
        return null;
    }

    protected String characterValueAsString(Object o) {
        Character value = characterValue(o);
        return value != null ? value.toString() : null;
    }

    protected String stringValue(Object o) {
        if (o instanceof String) {
            return (String) o;
        }
        if (o instanceof Proxy) {
            Object target = Proxy.getInvocationHandler(o);
            if (target instanceof SerializableClobProxy) {
                return getString(((SerializableClobProxy) target).getWrappedClob());
            }
        }
        return null;
    }

    protected Boolean booleanValue(Object o) {
        return o == null ? null :
                o instanceof String ? "true".equalsIgnoreCase((String) o) :
                o instanceof Boolean ? (Boolean) o :
                o instanceof Byte ? o.equals(Byte.valueOf("1")) :
                o instanceof Integer ? o.equals(Integer.valueOf("1")) :
                null;
    }

    protected Integer intValue(Object o) {
        return o == null ? null :
                o instanceof Integer ? (Integer) o :
                o instanceof BigInteger ? ((BigInteger) o).intValue() :
                o instanceof String ? Integer.valueOf((String) o) :
                null;
    }

    protected Long longValue(Object o) {
        return o == null ? null :
                o instanceof Long ? (Long) o :
                o instanceof BigInteger ? ((BigInteger) o).longValue() :
                o instanceof BigDecimal ? ((BigDecimal) o).longValue() :
                null;
    }

    protected Float floatValue(Object o) {
        return o == null ? null :
                o instanceof Float ? (Float) o :
                o instanceof BigDecimal ? ((BigDecimal) o).floatValue() :
                null;
    }

    protected Double doubleValue(Object o) {
        return o == null ? null :
                o instanceof Double ? (Double) o :
                o instanceof BigDecimal ? ((BigDecimal) o).doubleValue() :
                null;
    }

    protected Date dateValue(Object o) {
        return (Date) o;
    }

    protected String getString(Clob clob) {
        try {
            InputStream in = clob.getAsciiStream();
            StringWriter w = new StringWriter();
            IOUtils.copy(in, w);
            return w.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error reading clob", e);
        }
    }

    protected String arrayValue(Object o) {
        return o == null ? null :
                o instanceof String ? (String) o :
                "";
    }
}
