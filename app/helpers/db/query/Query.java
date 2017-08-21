package helpers.db.query;

import helpers.reflection.ReflectionUtil;
import org.hibernate.internal.AbstractQueryImpl;
import org.hibernate.internal.SQLQueryImpl;
import org.hibernate.type.StandardBasicTypes;
import play.db.jpa.JPA;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Query builder that encapsulates JPA native query builder, and handles null values properly.
 *
 * @author jtremeaux
 */
public class Query {
    private javax.persistence.Query jpaQuery;

    public Query(String queryString) {
        jpaQuery = JPA.em().createNativeQuery(queryString);
    }

    public Query setParameter(String key, String value) {
        jpaQuery.setParameter(key, value);
        return this;
    }

    public Query setParameter(String key, Short value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.SHORT);
        return this;
    }

    public Query setParameter(String key, Integer value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.INTEGER);
        return this;
    }

    public Query setParameter(String key, Long value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.BIG_INTEGER);
        return this;
    }

    public Query setParameter(String key, Float value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.FLOAT);
        return this;
    }

    public Query setParameter(String key, Double value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.DOUBLE);
        return this;
    }

    public Query setParameter(String key, BigDecimal value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.BIG_DECIMAL);
        return this;
    }

    public Query setParameter(String key, Boolean value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.BOOLEAN);
        return this;
    }

    public Query setParameter(String key, Date value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.TIMESTAMP);
        return this;
    }

    public Query setParameter(String key, Object value) {
        jpaQuery.setParameter(key, value);
        return this;
    }

    public AbstractQueryImpl getInternalQuery() {
        return (SQLQueryImpl) ReflectionUtil.getFieldValue(jpaQuery, "query");
    }

    public int executeUpdate() {
        return jpaQuery.executeUpdate();
    }
}
