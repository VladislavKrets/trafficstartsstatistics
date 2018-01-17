package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by lollipop on 24.11.2017.
 */
public class JsonTokenDeserializer implements JsonDeserializer<JsonTokenEntity>{
    @Override
    public JsonTokenEntity deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonTokenEntity jsonTokenEntity = new JsonTokenEntity();
        jsonTokenEntity.setAccessToken(object.get("access_token").getAsString());
        jsonTokenEntity.setExpiresIn(object.get("expires_in").getAsInt());
        jsonTokenEntity.setRefreshToken(object.get("refresh_token").getAsString());
        jsonTokenEntity.setTokenType(object.get("token_type").getAsString());
        return jsonTokenEntity;
    }
}
