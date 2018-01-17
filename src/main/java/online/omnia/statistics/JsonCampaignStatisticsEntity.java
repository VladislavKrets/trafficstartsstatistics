package online.omnia.statistics;

import java.util.Date;

/**
 * Created by lollipop on 25.11.2017.
 */
public class JsonCampaignStatisticsEntity {
    private int impressions;
    private int clicks;
    private double amount;
    private double ecpm;
    private Date day;
    private double ecpa;
    private double ecpc;
    private double ctr;

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getEcpm() {
        return ecpm;
    }

    public void setEcpm(double ecpm) {
        this.ecpm = ecpm;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public double getEcpa() {
        return ecpa;
    }

    public void setEcpa(double ecpa) {
        this.ecpa = ecpa;
    }

    public double getEcpc() {
        return ecpc;
    }

    public void setEcpc(double ecpc) {
        this.ecpc = ecpc;
    }

    public double getCtr() {
        return ctr;
    }

    public void setCtr(double ctr) {
        this.ctr = ctr;
    }
}
