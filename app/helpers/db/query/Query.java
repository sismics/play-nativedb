package helpers.db.query;

import helpers.reflection.ReflectionUtil;
import org.hibernate.internal.AbstractQueryImpl;
import org.hibernate.internal.SQLQueryImpl;
import org.hibernate.type.StandardBasicTypes;
import play.db.jpa.JPA;

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

    public Query setParameter(String key, Double value) {
        getInternalQuery().setParameter(key, value, StandardBasicTypes.DOUBLE);
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
