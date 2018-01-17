package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 27.11.2017.
 */
public class JsonBannerListDeserializer implements JsonDeserializer<List<JsonBannerEntity>>{
    @Override
    public List<JsonBannerEntity> deserialize(JsonElement jsonElement, Type type,
                                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonArray array = object.get("response").getAsJsonArray();
        List<JsonBannerEntity> jsonBannerEntities = new ArrayList<>();
        JsonBannerEntity jsonBannerEntity;

        for (JsonElement element : array) {
            jsonBannerEntity = new JsonBannerEntity();
            jsonBannerEntity.setId(element.getAsJsonObject().get("id").getAsInt());
            jsonBannerEntity.setCampaignId(element.getAsJsonObject().get("campaign_id").getAsInt());
            jsonBannerEntities.add(jsonBannerEntity);
        }
        return jsonBannerEntities;
    }
}
