package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by lollipop on 27.11.2017.
 */
public class JsonUrlDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject().get("response").getAsJsonObject();


        return object.get("content").getAsString();
    }
}
