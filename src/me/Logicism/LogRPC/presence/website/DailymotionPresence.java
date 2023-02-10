package me.Logicism.LogRPC.presence.website;

import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

public class DailymotionPresence extends Presence {

    public DailymotionPresence(PresenceData data) {
        super(611668948131512321L, data);
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
            Element titleElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div > div > div:nth-child(1) > div > div.col.large-5.WatchingSafeZone__videoInfoActionsSection___2rrHC > div > div > div > div > div:nth-child(2) > div > h1");

            try {
                return titleElement.ownText();
            } catch (NullPointerException e) {
                return "";
            }
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/videos") || data.getURL().contains("/videos?sort="))) {
            return "Browsing Channel Videos";
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/playlists") || data.getURL().contains("/playlists?sort="))) {
            return "Browsing Channel Playlists";
        } else if (data.getURL().substring(0, "https://www.dailymotion.com/".length()).length() != 2 && !data.getURL().startsWith("https://www.dailymotion.com/signin")) {
            return "Browsing Channel";
        }

        return "Browsing Dailymotion";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/search")) {
            Element searchTextElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div > div > div.Search__headerContainer___MFxvg > div > span > b");

            return searchTextElement.text();
        } else if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            Element titleElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div > div > div:nth-child(1) > div > div.col.large-5.WatchingSafeZone__videoInfoActionsSection___2rrHC > div > div > div > div > div:nth-child(3) > div > a > div");

            return titleElement.text();
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/videos") || data.getURL().contains("/videos?sort=")) || data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/playlists") || data.getURL().contains("/playlists?sort=")) || data.getURL().substring(0, "https://www.dailymotion.com/".length()).length() != 2 && !data.getURL().startsWith("https://www.dailymotion.com/signin")) {
            Element channelElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div.ChannelHeader__header___1zf7_.Header__header___3Lb4q > div.ChannelHeader__wrapper___3mRCh > div:nth-child(1) > div > h1 > a");

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

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            if (data.getHTMLDocument().html().contains("<use xlink:href=\"#dmp_Play\" style=\"pointer-events: none;\">")) {
                return "pause";
            } else {
                return "play";
            }
        } else {
            return "reading";
        }
    }

    @Override
    public String getSmallImageText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            if (data.getHTMLDocument().html().contains("<use xlink:href=\"#dmp_Play\" style=\"pointer-events: none;\">")) {
                return "Paused";
            } else {
                return "Playing";
            }
        } else {
            return "Reading";
        }
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/")) {
            return "Watch Video";
        } else if (data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            return "View Playlist";
        } else if (data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/videos") || data.getURL().contains("/videos?sort=")) || data.getURL().startsWith("https://www.dailymotion.com") && (data.getURL().endsWith("/playlists") || data.getURL().contains("/playlists?sort=")) || data.getURL().substring(0, "https://www.dailymotion.com/".length()).length() != 2 && !data.getURL().startsWith("https://www.dailymotion.com/signin")) {
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
            Element channelElement = data.getHTMLDocument().selectFirst("#root > div > main > div > div > div > div:nth-child(1) > div > div.col.large-5.WatchingSafeZone__videoInfoActionsSection___2rrHC > div > div > div > div > div:nth-child(3) > div > a");

            return "https://www.dailymotion.com" + channelElement.attr("href");
        } else {
            return null;
        }
    }

    @Override
    public long getStartTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            if (data.getHTMLDocument().html().contains("<use xlink:href=\"#dmp_Pause\" style=\"pointer-events: none;\">")) {
                Element timeElement = data.getHTMLDocument().selectFirst("#render_root_regular > div.np_SplitView > div.np_SplitView-left > div > div.np_Player-row--bottom > div > span.np_TimerContent-current");

                if (timeElement.ownText().split(":").length == 3) {
                    String hours = timeElement.ownText().split(":")[0];
                    String minutes = timeElement.ownText().split(":")[1];
                    String seconds = timeElement.ownText().split(":")[2];

                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;

                    return time;
                } else if (timeElement.ownText().split(":").length == 2) {
                    String minutes = timeElement.ownText().split(":")[0];
                    String seconds = timeElement.ownText().split(":")[1];

                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(lminutes, lseconds) * 1000L;

                    return time;
                }
            } else if (data.getHTMLDocument().html().contains("<use xlink:href=\"#dmp_Play\" style=\"pointer-events: none;\">")) {
                return -1;
            }
        }

        return 0;
    }

    @Override
    public long getEndTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.dailymotion.com/video/") || data.getURL().startsWith("https://www.dailymotion.com/playlist")) {
            if (data.getHTMLDocument().html().contains("<use xlink:href=\"#dmp_Pause\" style=\"pointer-events: none;\">")) {
                Element timeElement = data.getHTMLDocument().selectFirst("#render_root_regular > div.np_SplitView > div.np_SplitView-left > div > div.np_Player-row--bottom > div > span.np_TimerContent-duration");

                if (timeElement.ownText().split(":").length == 3) {
                    String hours = timeElement.ownText().split(":")[0];
                    String minutes = timeElement.ownText().split(":")[1];
                    String seconds = timeElement.ownText().split(":")[2];

                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;

                    return time;
                } else if (timeElement.ownText().split(":").length == 2) {
                    String minutes = timeElement.ownText().split(":")[0];
                    String seconds = timeElement.ownText().split(":")[1];

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
