package helpers.json;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * @author jtremeaux
 */
public class JsonUtil {
    /**
     * Get a nested JSON value.
     *
     * @param obj The JSON object
     * @param property The property to get, e.g. "user.address"
     * @return The value
     */
    public static JsonElement getPropertyValue(JsonObject obj, String property) {
        String[] properties = property.split("\\.");
        JsonElement fieldValue = obj.get(properties[0]);
        if (properties.length == 1) {
            return fieldValue;
        } else {
            List<String> remainingProperties = Lists.newArrayList(properties);
            remainingProperties.remove(0);
            if (fieldValue instanceof JsonObject) {
                return getPropertyValue((JsonObject) fieldValue, Joiner.on(".").join(remainingProperties));
            } else {
                throw new RuntimeException("Cannot get field: " + property + " from element, is not an JSON object");
            }
        }
    }
}
