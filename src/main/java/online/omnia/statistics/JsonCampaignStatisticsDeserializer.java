package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 25.11.2017.
 */
public class JsonCampaignStatisticsDeserializer implements JsonDeserializer<List<JsonCampaignStatisticsEntity>>{
    @Override
    public List<JsonCampaignStatisticsEntity> deserialize(JsonElement jsonElement, Type type,
                                                          JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray array = jsonElement.getAsJsonArray();
        List<JsonCampaignStatisticsEntity> statisticsEntities = new ArrayList<>();
        JsonCampaignStatisticsEntity jsonCampaignStatisticsEntity;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (JsonElement element : array) {
            jsonCampaignStatisticsEntity = new JsonCampaignStatisticsEntity();
            jsonCampaignStatisticsEntity.setAmount(element.getAsJsonObject().get("amount").getAsDouble());
            jsonCampaignStatisticsEntity.setClicks(element.getAsJsonObject().get("clicks").getAsInt());
            if(element.getAsJsonObject().get("ctr") != null) jsonCampaignStatisticsEntity.setCtr(element.getAsJsonObject().get("ctr").getAsDouble());
            try {
                jsonCampaignStatisticsEntity.setDay(dateFormat.parse(element.getAsJsonObject().get("day").getAsString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            jsonCampaignStatisticsEntity.setImpressions(element.getAsJsonObject().get("impressions").getAsInt());
            if (element.getAsJsonObject().get("ecpa") != null) jsonCampaignStatisticsEntity.setEcpa(element.getAsJsonObject().get("ecpa").getAsDouble());
            if (element.getAsJsonObject().get("ecpc") != null) jsonCampaignStatisticsEntity.setEcpc(element.getAsJsonObject().get("ecpc").getAsDouble());
            if (element.getAsJsonObject().get("ecpm") != null) jsonCampaignStatisticsEntity.setEcpm(element.getAsJsonObject().get("ecpm").getAsDouble());
            statisticsEntities.add(jsonCampaignStatisticsEntity);
        }

        return statisticsEntities;
    }
}
