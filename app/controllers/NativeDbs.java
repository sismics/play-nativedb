package controllers;

import nativedb.BasicAuthHelper;
import play.Play;
import play.mvc.Controller;
import play.mvc.Util;
import plugins.nativedb.NativeDbPlugin;

/**
 * Access logs controller.
 *
 * @author jtremeaux
 */
public class NativeDbs extends Controller {
    public static void index() {
        NativeDbPlugin nativeDbPlugin = getPluginInstance();
        if (!nativeDbPlugin.consoleEnabled) {
            notFound();
        }
        checkBasicAuth();
        render(nativeDbPlugin);
    }

    public static void updateNativeDb(boolean logQuery) {
        NativeDbPlugin nativeDbPlugin = getPluginInstance();
        if (!nativeDbPlugin.consoleEnabled) {
            notFound();
        }
        checkBasicAuth();
        nativeDbPlugin.logQuery = logQuery;
        flash.put("success", true);
        index();
    }

    @Util
    public static NativeDbPlugin getPluginInstance() {
        return (NativeDbPlugin) Play.pluginCollection.getPluginInstance(NativeDbPlugin.class);
    }

    @Util
    private static void checkBasicAuth() {
        String username = Play.configuration.getProperty("nativedb.console.username");
        String password = Play.configuration.getProperty("nativedb.console.password");
        if (!BasicAuthHelper.checkAuthenticationHeaders(request, username, password)) {
            unauthorized("Native DB console");
        }
    }
}
