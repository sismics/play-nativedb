package helpers.db;

import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.internal.SessionImpl;
import play.Play;
import play.db.jpa.JPA;

/**
 * @author jtremeaux
 */
public class Db {
    /**
     * Return the Hibernate session.
     *
     * @return The Hibernate session
     */
    public static SessionImpl getSession() {
        return (SessionImpl) ((EntityManagerImpl) JPA.em()).getSession();
    }

    public static String getDateTrunc(String field) {
        if (isDriverH2()) {
            return "parsedatetime (year(" + field + ") || '-' || month(" + field + ") || '-' || day(" + field + "), 'yyyy-MM-dd')";
        } else if (isDriverPostgresql()) {
            return "date_trunc('days', " + field + ")";
        } else {
            throw new RuntimeException("Unknown DB: " + getDriver());
        }
    }

    public static String getDateTimeTrunc(String field) {
        if (isDriverH2()) {
            return "parsedatetime (year(" + field + ") || '-' || month(" + field + ") || '-' || day(" + field + ") || ' ' || hour(" + field + ")  || ':' || minute(" + field + ") || ':' || second(" + field + "), 'yyyy-MM-dd hh:mm:ss')";
        } else if (isDriverPostgresql()) {
            return "date_trunc('seconds', " + field + ")";
        } else {
            throw new RuntimeException("Unknown DB: " + getDriver());
        }
    }

    public static String getStringAgg(String field, String orderBy, String separator) {
        if (isDriverH2()) {
            return "group_concat(" + field + " order by " + orderBy + " separator '" + separator + "')";
        } else if (isDriverPostgresql()) {
            return "string_agg(" + field + ", '" + separator + "' order by " + orderBy + ")";
        } else {
            throw new RuntimeException("Unknown DB: " + getDriver());
        }
    }

    public static String getDateAdd(String field, String add, String unit) {
        if (isDriverH2()) {
            return "dateadd('" + unit + "', " + add + ", " + field + ")";
        } else if (isDriverPostgresql()) {
            return field + " + (" + add + " * interval '1 " + unit + "')";
        } else {
            throw new RuntimeException("Unknown DB: " + getDriver());
        }
    }

    public static String getDateDiff(String field, String diff, String unit) {
        if (isDriverH2()) {
            return "datediff('" + unit + "', " + diff + ", " + field + ")";
        } else if (isDriverPostgresql()) {
            return field + " - (" + diff + " * interval '1 " + unit + "')";
        } else {
            throw new RuntimeException("Unknown DB: " + getDriver());
        }
    }

    public static String getDateAdd(String field, String add) {
        return getDateAdd(field, add, "SECOND");
    }

    public static String getDateDiff(String field, String diff) {
        return getDateDiff(field, diff, "SECOND");
    }

    public static String getStringAgg(String field) {
        return getStringAgg(field, field, ", ");
    }

    public static String escapeLike(String value) {
        return value.replaceAll("([%_])", "\\$1");
    }

    public static String getDriver() {
        return Play.configuration.getProperty("db.driver");
    }

    public static boolean isDriverH2() {
        return getDriver().contains("h2");
    }

    public static boolean isDriverPostgresql() {
        String driver = getDriver();
        return driver.contains("postgresql");
    }
}
