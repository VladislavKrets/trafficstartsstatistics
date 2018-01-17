package online.omnia.statistics;


import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by lollipop on 09.08.2017.
 */
public class Utils {
    public static FileWriter writer;
    static {
        try {
            File file = new File("logTrafficStarts.log");
            if (file.exists()) file.delete();
            writer = new FileWriter("logTrafficStarts.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void logWriter(String data) {
        try {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Map<String, String> iniFileReader() {
        Map<String, String> properties = new HashMap<>();
        try (BufferedReader iniFileReader = new BufferedReader(new FileReader("sources_stat.ini"))) {
            String property;
            String[] propertyArray;
            while ((property = iniFileReader.readLine()) != null) {
                propertyArray = property.split("=");
                if (property.contains("=")) {
                    properties.put(propertyArray[0], propertyArray[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Map<String, String> getUrlParameters(String url) {

        System.out.println(url);
        Map<String, String> parametersMap = new HashMap<>();
        if (url == null || url.isEmpty()) return parametersMap;

        String[] urlParts = url.split("\\?");

        if (urlParts.length != 2) {
            System.out.println("No ?");
            System.out.println(Arrays.asList(urlParts));
            return parametersMap;
        }

        String parameters = urlParts[1];
        if (!parameters.contains("&")) {
            System.out.println("Not found &");
            String[] pair = parameters.split("=");
            if (pair.length == 0) return parametersMap;
            if (pair.length == 2) {
                parametersMap.put(pair[0], pair[1]);
            } else if (pair.length == 1) {
                parametersMap.put(pair[0], "");
            }
            return parametersMap;
        }
        String[] keyValuePairs = parameters.split("&");
        String[] pairs;

        for (String keyValuePair : keyValuePairs) {
            pairs = keyValuePair.split("=");
            if (pairs.length == 2) {
                parametersMap.put(pairs[0], pairs[1]);
            } else if (pairs.length == 1) {
                parametersMap.put(pairs[0], "");
            }
        }
        System.out.println("Parameters have been got");
        return parametersMap;
    }

    public static AdsetEntity getAdset(SourceStatisticsEntity abstractAdsetEntity) {
        AdsetEntity adsetEntity = new AdsetEntity();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC/Etc"));
        adsetEntity.setCampaignId(abstractAdsetEntity.getCampaignId());

        adsetEntity.setDate(new Date(abstractAdsetEntity.getDate().getTime() + 10800000L));

        adsetEntity.setCampaignName(abstractAdsetEntity.getCampaignName());
        adsetEntity.setSpent(abstractAdsetEntity.getSpent());
        adsetEntity.setClicks(abstractAdsetEntity.getClicks());
        adsetEntity.setConversions(abstractAdsetEntity.getConversions());
        adsetEntity.setReceiver(abstractAdsetEntity.getReceiver());
        adsetEntity.setAccountId(abstractAdsetEntity.getAccount_id());
        adsetEntity.setCpc(abstractAdsetEntity.getCpc());
        adsetEntity.setCpm(abstractAdsetEntity.getCpm());
        adsetEntity.setCtr(abstractAdsetEntity.getCtr());
        adsetEntity.setImpressions(abstractAdsetEntity.getImpressions());
        adsetEntity.setAfid(abstractAdsetEntity.getAfid());
        adsetEntity.setBuyerId(abstractAdsetEntity.getBuyerId());
        return adsetEntity;
    }
}
