package online.omnia.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lollipop on 24.11.2017.
 */
public class Main {
    public static int days;
    public static long deltaTime = 24 * 60 * 60 * 1000;

    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        if (!args[0].matches("\\d+")) return;
        if (Integer.parseInt(args[0]) == 0) {
            deltaTime = 0;
        }
        days = Integer.parseInt(args[0]);

        List<AccountsEntity> accountsEntities = MySQLDaoImpl.getInstance().getAccountsEntities("Trafficstars");
        String answer;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(JsonTokenEntity.class, new JsonTokenDeserializer());
        builder.registerTypeAdapter(JsonCampaign.class, new JsonCampaignListDeserializer());
        builder.registerTypeAdapter(List.class, new JsonBannerListDeserializer());
        builder.registerTypeAdapter(String.class, new JsonUrlDeserializer());
        GsonBuilder statisticsBuilder = new GsonBuilder();
        statisticsBuilder.registerTypeAdapter(List.class, new JsonCampaignStatisticsDeserializer());
        Gson gsonStatistics = statisticsBuilder.create();
        Gson gson = builder.create();
        JsonTokenEntity jsonTokenEntity;
        List<JsonBannerEntity> jsonBannerEntities;
        SourceStatisticsEntity sourceStatisticsEntity;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<JsonCampaignStatisticsEntity> campaignStatisticsEntities = new ArrayList<>();
        SourceStatisticsEntity entity;
        for (AccountsEntity accountsEntity : accountsEntities) {
            answer = HttpMethodUtils.postMethod("https://api.trafficstars.com/v1/auth/token?grant_type=password&client_id="
                    + /*"83866221707130234114806847755644"*/ accountsEntity.getClientId()
                    + "&client_secret=" + accountsEntity.getClientSecret()
                    /*+ "yM9fcKnRMYahZn03tAVnB9eawP9S3Zs2"*/
                    + "&username=" + accountsEntity.getUsername()
                    /*+ "ts@thehighestroi.club"*/
                    + "&password=" + accountsEntity.getPassword()
                   /* + "ASDj324sd!"*/,
                    new ArrayList<>());
            System.out.println(answer);
            jsonTokenEntity = gson.fromJson(answer, JsonTokenEntity.class);
            answer = HttpMethodUtils.getMethod("https://api.trafficstars.com/v1/banner/list", jsonTokenEntity.getAccessToken());
            jsonBannerEntities = gson.fromJson(answer, List.class);
            JsonCampaign jsonCampaign;
            String url;
            Map<String, String> parameters;
            AdsetEntity tempAdsetEntity;
            AdsetEntity adsetEntity;
            for (JsonBannerEntity jsonBannerEntity : jsonBannerEntities) {
                answer = HttpMethodUtils.getMethod("https://api.trafficstars.com/v1.1/advertiser/custom/report/by-day?date_from="
                        + dateFormat.format(new java.util.Date(System.currentTimeMillis() - deltaTime - days * 24L * 60 * 60 * 1000))
                        + "&date_to="
                        + dateFormat.format(new java.util.Date(System.currentTimeMillis() - deltaTime))
                        + "&campaign_id="
                        + jsonBannerEntity.getCampaignId(),
                        jsonTokenEntity.getAccessToken());
                campaignStatisticsEntities = gsonStatistics.fromJson(answer, List.class);
                for (JsonCampaignStatisticsEntity statisticsEntity : campaignStatisticsEntities) {

                    sourceStatisticsEntity = new SourceStatisticsEntity();
                    answer = HttpMethodUtils.getMethod("https://api.trafficstars.com/v1/campaign/" + jsonBannerEntity.getCampaignId(), jsonTokenEntity.getAccessToken());
                    jsonCampaign = gson.fromJson(answer, JsonCampaign.class);
                    answer = HttpMethodUtils.getMethod("https://api.trafficstars.com/v1/banner/" + jsonBannerEntity.getId(), jsonTokenEntity.getAccessToken());
                    url = gson.fromJson(answer, String.class);
                    System.out.println(url);
                    parameters = Utils.getUrlParameters(url);
                    if (parameters.containsKey("affid")) {
                        if (parameters.get("affid").matches("\\d+")
                                && MySQLDaoImpl.getInstance().getAffiliateByAfid(Integer.parseInt(parameters.get("affid"))) != null) {
                            sourceStatisticsEntity.setAfid(Integer.parseInt(parameters.get("affid")));
                        } else {
                            sourceStatisticsEntity.setAfid(0);
                        }
                    } else sourceStatisticsEntity.setAfid(2);

                    sourceStatisticsEntity.setAccount_id(accountsEntity.getAccountId());
                    sourceStatisticsEntity.setBuyerId(accountsEntity.getBuyerId());
                    sourceStatisticsEntity.setCampaignName(jsonCampaign.getName());
                    sourceStatisticsEntity.setClicks(statisticsEntity.getClicks());
                    sourceStatisticsEntity.setCampaignId(String.valueOf(jsonBannerEntity.getCampaignId()));
                    sourceStatisticsEntity.setSpent(statisticsEntity.getAmount());
                    sourceStatisticsEntity.setCpc(statisticsEntity.getEcpc());
                    sourceStatisticsEntity.setCpm(statisticsEntity.getEcpm());
                    sourceStatisticsEntity.setCtr(statisticsEntity.getCtr());
                    sourceStatisticsEntity.setImpressions(statisticsEntity.getImpressions());
                    sourceStatisticsEntity.setReceiver("API");
                    sourceStatisticsEntity.setDate(new Date(statisticsEntity.getDay().getTime()));
                    System.out.println(sourceStatisticsEntity);
                    if (Main.days != 0) {
                        entity = MySQLDaoImpl.getInstance().getSourceStatistics(sourceStatisticsEntity.getAccount_id(),
                                sourceStatisticsEntity.getCampaignName(), sourceStatisticsEntity.getDate());
                        if (entity != null) {
                            sourceStatisticsEntity.setId(entity.getId());
                            MySQLDaoImpl.getInstance().updateSourceStatistics(sourceStatisticsEntity);
                            entity = null;
                        } else MySQLDaoImpl.getInstance().addSourceStatistics(sourceStatisticsEntity);
                    }
                    else {
                        adsetEntity = Utils.getAdset(sourceStatisticsEntity);
                        tempAdsetEntity = MySQLDaoImpl.getInstance().isDateInTodayAdsets(adsetEntity.getDate(), adsetEntity.getAccountId(), adsetEntity.getCampaignId());
                        if (tempAdsetEntity != null) {
                            adsetEntity.setId(tempAdsetEntity.getId());
                            MySQLDaoImpl.getInstance().updateTodayAdset(adsetEntity);
                        } else MySQLDaoImpl.getInstance().addTodayAdset(Utils.getAdset(sourceStatisticsEntity));
                        tempAdsetEntity = null;
                    }
                }
            }
        }
        MySQLDaoImpl.getSessionFactory().close();
    }
}
