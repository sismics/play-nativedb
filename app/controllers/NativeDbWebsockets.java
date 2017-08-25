package controllers;

import play.Play;
import play.mvc.Util;
import play.mvc.WebSocketController;
import plugins.nativedb.NativeDbPlugin;

/**
 * Native DB Websocket controller.
 *
 * @author jtremeaux
 */
public class NativeDbWebsockets extends WebSocketController {
    public static void logs() {
        while (inbound.isOpen()) {
            outbound.send(await(getPluginInstance().events.nextEvent()));
        }
    }

    @Util
    public static NativeDbPlugin getPluginInstance() {
        return (NativeDbPlugin) Play.pluginCollection.getPluginInstance(NativeDbPlugin.class);
    }
}
