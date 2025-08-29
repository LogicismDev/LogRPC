package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

public class DailymotionPresence extends Presence {

    public DailymotionPresence(PresenceData data) {
        super(611668948131512321L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public DisplayType getDisplayType() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            return DisplayType.DETAILS;
        }

        return DisplayType.STATE;
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().substring(0, "https://www.dailymotion.com/".length()).length() == 2) {
            return "Browsing Homepage";
        } else if (data.getURL().startsWith("https://www.dailymotion.com/search")) {
            return "Browsing Search";
        } else if (data.getURL().startsWith("https://www.dailymotion.com/partner")) {
            return "Browsing Partner HQ";
        } else if (data.getURL().startsWith("https://www.dailymotion.com/library")) {
            return "Browsing Library";
        } else if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            Element titleElement = data.getHTMLDocument().selectFirst("#media-title");

            try {
                return titleElement.ownText();
            } catch (NullPointerException e) {
                return "";
            }
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/videos") || data.getURL().contains("/videos?sort="))) {
            return "Browsing Channel Videos";
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/playlists") || data.getURL().contains("/playlists?sort="))) {
            return "Browsing Channel Playlists";
        }

        return "Browsing Dailymotion";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/search")) {
            Element searchTextElement = data.getHTMLDocument().selectFirst("#root > div > header > div > div.ResponsiveSubheader__searchBarContainer___2SoeX > div > button > span");

            return searchTextElement.text();
        } else if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            Element titleElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div.NewWatchingDiscovery__watchingSection___3Bzey > div > div.WatchingSection__safeZone___w2sTV > div > div:nth-child(2) > div > div > div.NewVideoInfo__channelLineWrapper___3SHpY > div:nth-child(1) > div > a > div");

            return titleElement.text();
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/videos") || data.getURL().contains("/videos?sort=")) || data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/playlists") || data.getURL().contains("/playlists?sort="))) {
            Element channelElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div > div.ChannelHeader__container___3oX3T > div:nth-child(2) > div.ChannelHeaderInfo__channelInfoContainer___22LxP > div > div.ChannelHeaderInfo__channelDisplayNameContainer___29sVB > div.ChannelHeaderInfo__userName___3OU4F > h1");

            return channelElement.text();
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            return "https://www.dailymotion.com/thumbnail/video/" + data.getURL().replace("https://www.dailymotion.com/playlist/", "")
                    .replace("https://www.dailymotion.com/video/", "");
        } else {
            return "logo";
        }
    }

    @Override
    public String getSmallImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (!(data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist"))) {
            return "reading";
        }

        return "";
    }

    @Override
    public String getSmallImageText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (!(data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist"))) {
            return "Reading";
        }

        return "";
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/")) {
            return "Watch Video";
        } else if (data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            return "View Playlist";
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/videos") || data.getURL().contains("/videos?sort=")) || data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/playlists") || data.getURL().contains("/playlists?sort="))) {
            return "View Channel";
        } else {
            return null;
        }
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        return data.getURL();
    }

    @Override
    public String getDetailsURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        return data.getURL();
    }

    @Override
    public String getSecondaryButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/")) {
            return "Visit Channel";
        } else {
            return null;
        }
    }

    @Override
    public String getSecondaryButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            Element channelElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div.NewWatchingDiscovery__watchingSection___3Bzey > div > div.WatchingSection__safeZone___w2sTV > div > div:nth-child(2) > div > div > div.NewVideoInfo__channelLineWrapper___3SHpY > div:nth-child(1) > div > a");

            return "https://www.dailymotion.com" + channelElement.attr("href");
        } else {
            return null;
        }
    }

    @Override
    public String getStateURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            Element channelElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div.NewWatchingDiscovery__watchingSection___3Bzey > div > div.WatchingSection__safeZone___w2sTV > div > div:nth-child(2) > div > div > div.NewVideoInfo__channelLineWrapper___3SHpY > div:nth-child(1) > div > a");

            return "https://www.dailymotion.com" + channelElement.attr("href");
        } else {
            return null;
        }
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
