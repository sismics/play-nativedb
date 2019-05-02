package plugins.nativedb;

import helpers.db.PaginatedLists;
import helpers.stream.CappedEventStream;
import org.hibernate.query.internal.QueryImpl;
import play.Play;
import play.PlayPlugin;
import play.mvc.Router;

import javax.persistence.Parameter;

/**
 * Native DB plugin.
 *
 * @author jtremeaux
 */
public class NativeDbPlugin extends PlayPlugin {
    public boolean consoleEnabled;

    public boolean logQuery;

    public CappedEventStream<String> events = new CappedEventStream<>();

    private static final String NATIVEDB_PREFIX = "nativedb";

    @Override
    public void onConfigurationRead() {
        consoleEnabled = Boolean.parseBoolean(Play.configuration.getProperty(NATIVEDB_PREFIX + ".console.enabled", String.valueOf(Play.mode.isDev())));
        setLogQuery(Boolean.parseBoolean(Play.configuration.getProperty(NATIVEDB_PREFIX + ".logQuery", String.valueOf(Play.mode.isDev()))));
    }

    public String getLog(QueryImpl query) {
        try {
            String queryString = query.getQueryString();
            for (Object parameter : query.getParameters()) {
                Parameter param = (Parameter) parameter;
                queryString = queryString.replaceFirst(":" + param.getName(), getReplacement(query.getParameterValue(param.getName())));
            }
            return queryString;
        } catch (Exception e) {
            return "Error logging query";
        }
    }

    private String getReplacement(Object value) {
        if (value instanceof String) {
            return "'" + ((String) value).replaceAll("'", "''") + "'";
        } else {
            return value.toString();
        }
    }

    @Override
    public void onRoutesLoaded() {
        Router.prependRoute("GET", "/@nativedb", "NativeDbs.index");
        Router.prependRoute("WS", "/@nativedb/logs", "NativeDbWebsockets.logs");
    }

    public void setLogQuery(boolean logQuery) {
        this.logQuery = logQuery;
        if (logQuery) {
            PaginatedLists.onNativeQuery = query -> {
                events.publish(getLog((QueryImpl) query));
            };
        } else {
            PaginatedLists.onNativeQuery = null;
        }
    }
}