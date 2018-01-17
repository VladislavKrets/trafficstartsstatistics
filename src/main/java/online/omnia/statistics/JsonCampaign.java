package online.omnia.statistics;

/**
 * Created by lollipop on 24.11.2017.
 */
public class JsonCampaign {
    private int id;
    private String name;
    private String callbackURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }
}
