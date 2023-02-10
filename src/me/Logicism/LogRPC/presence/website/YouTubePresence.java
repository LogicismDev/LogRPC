package me.Logicism.LogRPC.presence.website;

import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class YouTubePresence extends Presence {

    public YouTubePresence(PresenceData data) {
        super(463097721130188830L, data);
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().equals("https://www.youtube.com/")) {
            return "Browsing the Home Page!";
        } else if (data.getURL().startsWith("https://www.youtube.com/feed/")) {
            if (data.getURL().startsWith("https://www.youtube.com/feed/storefront")) {
                return "Browsing the Your Movies Page!";
            } else {
                return "Browsing the " + data.getURL().split("/feed/")[1].substring(0, 1).toUpperCase() + data.getURL().split("/feed/")[1].substring(1).split("\\?")[0] + " Page!";
            }
        } else if (data.getURL().startsWith("https://www.youtube.com/c/") || data.getURL().startsWith("https://www.youtube.com/channel/") || data.getURL().startsWith("https://www.youtube.com/user/")) {
            if (data.getURL().contains("/videos")) {
                return "Browsing Channel Videos";
            } else if (data.getURL().contains("/playlists")) {
                return "Browsing Channel Playlists";
            } else if (data.getURL().contains("/community")) {
                return "Browsing Channel Community";
            } else if (data.getURL().contains("/search")) {
                return "Browsing Channel Search";
            } else {
                return "Browsing Channel";
            }
        } else if (data.getURL().startsWith("https://www.youtube.com/results")) {
            return "Browsing YouTube Search";
        } else if (data.getURL().startsWith("https://www.youtube.com/playlist")) {
            return "Browsing YouTube Playlist";
        } else if (data.getURL().startsWith("https://www.youtube.com/gaming")) {
            return "Browsing YouTube Gaming";
        } else if (data.getURL().startsWith("https://www.youtube.com/premium")) {
            return "Browsing YouTube Premium";
        } else if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            Element titleElement = data.getHTMLDocument().selectFirst("#container > h1 > yt-formatted-string");

            try {
                return titleElement.ownText();
            } catch (NullPointerException e) {
                return "";
            }
        } else if (data.getURL().startsWith("https://www.youtube.com/shorts/")) {
            return "Browsing YouTube Shorts";
        }

        return "Browsing YouTube";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.youtube.com/c/") || data.getURL().startsWith("https://www.youtube.com/channel/") || data.getURL().startsWith("https://www.youtube.com/user/")) {
            Elements elements = data.getHTMLDocument().select("#text");

            for (Element element : elements) {
                if (element.hasAttr("title") && element.tagName().equals("yt-formatted-string") && element.hasClass("ytd-channel-name")) {
                    return element.text();
                }
            }
        } else if (data.getURL().startsWith("https://www.youtube.com/results")) {
            try {
                return URLDecoder.decode(data.getURL().substring("https://www.youtube.com/results?search_query=".length()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        } else if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            Element titleElement = data.getHTMLDocument().selectFirst("#text > a");

            try {
                return titleElement.text();
            } catch (NullPointerException e) {
                Element element = data.getHTMLDocument().selectFirst("#watch7-content > span:nth-child(9) > link:nth-child(2)");

                return element.attr("content");
            }
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.youtube.com/watch?v=") || data.getURL().startsWith("https://www.youtube.com/shorts/")) {
            String id = data.getURL().replace("https://www.youtube.com/watch?v=", "").replace("https://www.youtube.com/shorts/", "");
            if (id.contains("&")) {
                id = id.split("&")[0];
            }

            return "https://i1.ytimg.com/vi/" + id + "/sddefault.jpg";
        } else {
            return "yt_lg";
        }
    }

    @Override
    public String getSmallImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            Element element = data.getHTMLDocument().selectFirst(".video-stream").parent().parent();
            Element liveButtonElement = data.getHTMLDocument().getElementsByClass("ytp-clip-watch-full-video-button").first();

            return element.attr("class").contains("playing-mode") ? (liveButtonElement.ownText().startsWith("Watch live") ? "live" : "play") : "pause";
        } else {
            return "reading";
        }
    }

    @Override
    public String getSmallImageText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            Element element = data.getHTMLDocument().selectFirst(".video-stream").parent().parent();
            Element liveButtonElement = data.getHTMLDocument().getElementsByClass("ytp-clip-watch-full-video-button").first();

            return element.attr("class").contains("playing-mode") ? (liveButtonElement.ownText().startsWith("Watch live") ? "Live" : "Playing") : "Paused";
        } else {
            return "Reading";
        }
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            return "Watch Video";
        } else if (data.getURL().startsWith("https://www.youtube.com/c/") ||
                data.getURL().startsWith("https://www.youtube.com/channel/") ||
                data.getURL().startsWith("https://www.youtube.com/user/")) {
            return "View Channel";
        } else if (data.getURL().startsWith("https://www.youtube.com/playlist")) {
            return "View Playlist";
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

        if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            return "Visit Channel";
        } else {
            return null;
        }
    }

    @Override
    public String getSecondaryButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        Element channelElement = data.getHTMLDocument().selectFirst("#text > a");
        if (data.getURL().startsWith("https://www.youtube.com/watch?v=") && channelElement != null) {

            return "https://www.youtube.com" + channelElement.attr("href");
        } else {
            return null;
        }
    }

    @Override
    public long getStartTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            Element element = data.getHTMLDocument().selectFirst(".video-stream").parent().parent();
            Element liveButtonElement = data.getHTMLDocument().getElementsByClass("ytp-clip-watch-full-video-button").first();

            if (element.hasClass("playing-mode") && !liveButtonElement.ownText().startsWith("Watch live")) {
                Element timeElement = data.getHTMLDocument().getElementsByClass("ytp-time-current").first();

                if (timeElement.ownText().split(":").length == 4) {
                    String days = timeElement.ownText().split(":")[0];
                    String hours = timeElement.ownText().split(":")[1];
                    String minutes = timeElement.ownText().split(":")[2];
                    String seconds = timeElement.ownText().split(":")[3];

                    long ldays = Long.parseLong(days) * 24 * 60 * 60;
                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(ldays, Long.sum(Long.sum(lhours, lminutes), lseconds)) * 1000L;

                    return time;
                } else if (timeElement.ownText().split(":").length == 3) {
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
            } else if (!element.attr("class").contains("playing-mode") && !liveButtonElement.ownText().startsWith("Watch live")) {
                return -1;
            }
        }

        return 0;
    }

    @Override
    public long getEndTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.youtube.com/watch?v=")) {
            Element element = data.getHTMLDocument().selectFirst(".video-stream").parent().parent();
            Element liveButtonElement = data.getHTMLDocument().getElementsByClass("ytp-clip-watch-full-video-button").first();

            if (element.hasClass("playing-mode") && !liveButtonElement.ownText().startsWith("Watch live")) {
                Element timeElement = data.getHTMLDocument().getElementsByClass("ytp-time-duration").first();

                if (timeElement.ownText().split(":").length == 4) {
                    String days = timeElement.ownText().split(":")[0];
                    String hours = timeElement.ownText().split(":")[1];
                    String minutes = timeElement.ownText().split(":")[2];
                    String seconds = timeElement.ownText().split(":")[3];

                    long ldays = Long.parseLong(days) * 24 * 60 * 60;
                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(ldays, Long.sum(Long.sum(lhours, lminutes), lseconds)) * 1000L;

                    return time;
                } else if (timeElement.ownText().split(":").length == 3) {
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
