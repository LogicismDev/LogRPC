package me.Logicism.LogRPC.core.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BrowserHTMLData implements PresenceData {

    private JSONObject data;
    private String title;
    private String url;
    private Document document;

    public BrowserHTMLData(JSONObject data) {
        try {
            this.data = data;
            title = data.getString("state");
            url = data.getString("url");
            document = Jsoup.parse(data.getString("html"));
        } catch (JSONException e) {

        }
    }

    public BrowserHTMLData(String url, Document document) {
        this.url = url;
        this.document = document;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getURL() {
        return url;
    }

    public Document getHTMLDocument() {
        return document;
    }

    public boolean isEnabled(String website) {
        return data.getBoolean(website);
    }
}
