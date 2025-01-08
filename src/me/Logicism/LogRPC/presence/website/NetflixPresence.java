package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class NetflixPresence extends Presence {

    public NetflixPresence(PresenceData data) {
        super(499981204045430784L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("?jbv=") || data.getURL().contains("&jbv=")) {
            return "Browsing Netflix Show";
        } else if (data.getURL().startsWith("https://www.netflix.com/search?q=")) {
            return "Browsing Netflix Search";
        } else if (data.getURL().startsWith("https://www.netflix.com/watch/")) {
            Element element = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "video-title").first();
            if (element != null) {
                return element.getElementsByTag("h4").text();
            } else {
                return "";
            }
        }

        return "Browsing Netflix";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("?jbv=") || data.getURL().contains("&jbv=")) {
            return data.getHTMLDocument().getElementsByClass("previewModal--section-header").get(2).getElementsByTag("strong").text();
        } else if (data.getURL().startsWith("https://www.netflix.com/search?q=")) {
            try {
                return URLDecoder.decode(data.getURL().substring("https://www.netflix.com/search?q=".length()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        } else if (data.getURL().startsWith("https://www.netflix.com/watch/")) {
            Element element = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "video-title").first();
            if (element != null) {
                if (element.children().size() == 3) {
                    return element.child(1).text() + " - " + element.child(2).text();
                } else {
                    return "";
                }
            }
        }
        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "https://i.imgur.com/Wf8G0mk.gif";
    }

    @Override
    public String getSmallImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.netflix.com/watch/")) {
            Element element = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-play").first();
            Element element1 = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-pause").first();

            if (element != null) {
                return "pause";
            } else if (element1 != null) {
                return "play";
            }
        }
        return "reading";
    }

    @Override
    public String getSmallImageText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.netflix.com/watch/")) {
            Element element = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-play").first();
            Element element1 = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-pause").first();

            if (element != null) {
                return "Paused";
            } else if (element1 != null) {
                return "Playing";
            }
        }
        return "Reading";
    }

    @Override
    public long getStartTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.netflix.com/watch/")) {
            Element element = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-play").first();
            Element element1 = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-pause").first();

            if (element != null) {
                return -1;
            } else if (element1 != null) {
                return 0;
            }
        }

        return 0;
    }

    @Override
    public long getEndTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.netflix.com/watch/")) {
            Element element = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-play").first();
            Element element1 = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "control-play-pause-pause").first();

            if (element != null) {
                return -1;
            } else if (element1 != null) {
                Element timeRemaining = data.getHTMLDocument().getElementsByAttributeValue("data-uia", "controls-time-remaining").first();

                if (timeRemaining.ownText().split(":").length == 3) {
                    String hours = timeRemaining.ownText().split(":")[0];
                    String minutes = timeRemaining.ownText().split(":")[1];
                    String seconds = timeRemaining.ownText().split(":")[2];

                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;

                    return time;
                } else if (timeRemaining.ownText().split(":").length == 2) {
                    String minutes = timeRemaining.ownText().split(":")[0];
                    String seconds = timeRemaining.ownText().split(":")[1];

                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(lminutes, lseconds) * 1000L;

                    return time;
                }
            }
        }

        return -1;
    }
}
