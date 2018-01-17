package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 24.11.2017.
 */
public class JsonCampaignListDeserializer implements JsonDeserializer<JsonCampaign> {
    @Override
    public JsonCampaign deserialize(JsonElement jsonElement, Type type,
                                    JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject().get("response").getAsJsonObject();
        JsonCampaign jsonCampaign = new JsonCampaign();
        jsonCampaign.setId(object.get("id").getAsInt());
        jsonCampaign.setName(object.get("name").getAsString());
        jsonCampaign.setCallbackURL(object.get("callback_url").getAsString());

        return jsonCampaign;
    }
}
