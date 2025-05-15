package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;

public class TwitchPresence extends Presence {

    public TwitchPresence(PresenceData data) {
        super(802958789555781663L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (data.getURL().equals("https://www.twitch.tv/")) {
                    return "On the Homepage";
                } else if (data.getURL().contains("https://www.twitch.tv/directory")) {
                    if (data.getURL().contains("https://www.twitch.tv/directory/following")) {
                        return "Viewing Following Directory";
                    } else if (data.getURL().contains("https://www.twitch.tv/directory/esports")) {
                        return "Viewing Esports Directory";
                    } else if (data.getURL().contains("https://www.twitch.tv/directory/game")) {
                        return "Viewing Game Directory";
                    } else {
                        return "Browsing Twitch";
                    }
                } else if (data.getURL().contains("https://www.twitch.tv/videos")) {
                    Elements titleElements = data.getHTMLDocument().select("#live-channel-stream-information > div > div > div.metadata-layout__split-top > div.Layout-sc-1xcs6mc-0.iULZCz > div.Layout-sc-1xcs6mc-0.jPmKIH > p");
                    Element titleElement = null;
                    for (Element e : titleElements) {
                        if (e.hasAttr("data-a-target") && e.attr("data-a-target").equals("stream-title")) {
                            titleElement = e;
                            break;
                        }
                    }

                    return titleElement.textNodes().get(0).text();
                } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                    Elements titleElements = data.getHTMLDocument().getElementsByTag("p");
                    Element titleElement = null;
                    for (Element e : titleElements) {
                        if (e.hasAttr("data-test-selector") && e.attr("data-test-selector").equals("stream-info-card-component__subtitle")) {
                            titleElement = e;
                            break;
                        }
                    }
                    Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                    if (liveElement != null) {
                        return titleElement.text();
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                    Element videoElement = videoElements.first();

                    Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.iXRytg > div > div > div.Layout-sc-1xcs6mc-0.kpHsJz.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div");
                    if (videoElement != null && liveElement != null) {
                        Elements titleElements = data.getHTMLDocument().select("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.evfzyg > div.Layout-sc-1xcs6mc-0.iStNQt > div.Layout-sc-1xcs6mc-0.fxsRRM > div > div.Layout-sc-1xcs6mc-0.fAVISI > p");
                        Element titleElement = titleElements.first();

                        if (titleElement.hasAttr("data-a-target") && titleElement.attr("data-a-target").equals("stream-title")) {
                            return titleElement.attr("title");
                        }
                    } else {
                        return "Viewing Channel";
                    }
                }
            } else if (url.getHost().equals("dev.twitch.tv") || url.getHost().equals("devstatus.twitch.tv")) {
                return "Browsing Developer Pages";
            } else if (url.getHost().equals("discuss.dev.twitch.tv")) {
                return "Browsing Developer Discussion";
            }
        } catch (MalformedURLException e) {
            return "Browsing Twitch";
        }

        return "Browsing Twitch";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (data.getURL().contains("https://www.twitch.tv/videos")) {
                    Elements titleElements = data.getHTMLDocument().getElementsByTag("h1");
                    Element titleElement = null;
                    for (Element e : titleElements) {
                        if (e.hasClass("tw-title")) {
                            titleElement = e;
                            break;
                        }
                    }

                    return titleElement.textNodes().get(0).text();
                } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                    Elements titleElements = data.getHTMLDocument().getElementsByTag("a");
                    Element titleElement = null;
                    for (Element e : titleElements) {
                        if (e.hasAttr("data-test-selector") && e.attr("data-test-selector").equals("stream-info-card-component__title-link")) {
                            titleElement = e;
                            break;
                        }
                    }
                    Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                    if (liveElement != null) {
                        return titleElement.text();
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                    Element videoElement = videoElements.first();

                    Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.iXRytg > div > div > div.Layout-sc-1xcs6mc-0.kpHsJz.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div");
                    if (videoElement != null && liveElement != null) {
                        Element titleElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.evfzyg > div.Layout-sc-1xcs6mc-0.denZNh.metadata-layout__support > div.Layout-sc-1xcs6mc-0.jjAyLi > div > a > h1");

                        return titleElement.ownText();
                    } else {
                        return data.getTitle().substring(0, data.getTitle().indexOf(" - Twitch"));
                    }
                }
            }
        } catch (MalformedURLException e) {
            return "";
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("dev.twitch.tv") || url.getHost().equals("devstatus.twitch.tv") || url.getHost().equals("discuss.dev.twitch.tv")) {
                return "dev-main";
            }
        } catch (MalformedURLException e) {
            return "twitch";
        }

        return "twitch";
    }

    @Override
    public String getSmallImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (data.getURL().contains("https://www.twitch.tv/videos")) {
                    Elements elements = data.getHTMLDocument().select("#channel-player > div > div.Layout-sc-1xcs6mc-0.cFsYRp.player-controls__left-control-group > div.InjectLayout-sc-1i43xsx-0.kBtJDm > button");
                    Element element = null;
                    for (Element e : elements) {
                        if (e.hasAttr("data-a-player-state")) {
                            element = e;
                            break;
                        }
                    }

                    if (element != null) {
                        return "play";
                    } else {
                        return "pause";
                    }
                } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                    Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                    if (liveElement != null) {
                        return "live";
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                    Element videoElement = videoElements.first();

                    Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.iXRytg > div > div > div.Layout-sc-1xcs6mc-0.kpHsJz.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div");
                    if (videoElement != null && liveElement != null) {
                        return "live";
                    }
                }
            }
        } catch (MalformedURLException e) {
            return "none";
        }

        return "none";
    }

    @Override
    public String getSmallImageText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (data.getURL().contains("https://www.twitch.tv/videos")) {
                    Elements elements = data.getHTMLDocument().select("#channel-player > div > div.Layout-sc-1xcs6mc-0.cFsYRp.player-controls__left-control-group > div.InjectLayout-sc-1i43xsx-0.kBtJDm > button");
                    Element element = null;
                    for (Element e : elements) {
                        if (e.hasAttr("data-a-player-state")) {
                            element = e;
                            break;
                        }
                    }

                    if (element != null) {
                        if (element.attr("data-a-player-state").equals("paused")) {
                            return "Paused";
                        } else {
                            return "Playing";
                        }
                    }
                } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                    Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                    if (liveElement != null) {
                        return "Live";
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                    Element videoElement = videoElements.first();

                    Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.iXRytg > div > div > div.Layout-sc-1xcs6mc-0.kpHsJz.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div");
                    if (videoElement != null && liveElement != null) {
                        return "Live";
                    }
                }
            }
        } catch (MalformedURLException e) {
            return "Reading";
        }

        return "Reading";
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (data.getURL().contains("https://www.twitch.tv/videos")) {
                    return "Watch Video";
                } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                    Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                    if (liveElement != null) {
                        return "Watch Stream";
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                    Element videoElement = videoElements.first();

                    Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.iXRytg > div > div > div.Layout-sc-1xcs6mc-0.kpHsJz.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div");
                    if (videoElement != null && liveElement != null) {
                        return "Watch Stream";
                    } else {
                        return "Visit Channel";
                    }
                }
            }
        } catch (MalformedURLException e) {
            return null;
        }

        return null;
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (data.getURL().contains("/moderator/")) {
                    return data.getURL().replace("/moderator", "");
                } else {
                    return data.getURL();
                }
            }
        } catch (MalformedURLException e) {
            return null;
        }

        return null;
    }

    @Override
    public long getStartTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("https://www.twitch.tv/videos")) {
            Elements elements = data.getHTMLDocument().select("#channel-player > div > div.Layout-sc-1xcs6mc-0.cFsYRp.player-controls__left-control-group > div.InjectLayout-sc-1i43xsx-0.kBtJDm > button");
            Element vElement = null;
            for (Element e : elements) {
                if (e.hasAttr("data-a-player-state")) {
                    vElement = e;
                    break;
                }
            }

            if (vElement != null) {
                Element element = data.getHTMLDocument().selectFirst("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div > div:nth-child(5) > div > div > div > div.Layout-sc-1xcs6mc-0.iULZCz.vod-seekbar-time-labels > p:nth-child(1)");

                String hours = element.ownText().split(":")[0];
                String minutes = element.ownText().split(":")[1];
                String seconds = element.ownText().split(":")[2];

                long lhours = Long.parseLong(hours) * 60 * 60;
                long lminutes = Long.parseLong(minutes) * 60;
                long lseconds = Long.parseLong(seconds);
                long time = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;

                return time;
            } else {
                return -1;
            }
        }

        return 0;
    }

    @Override
    public long getEndTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("https://www.twitch.tv/videos")) {
            Elements elements = data.getHTMLDocument().select("#channel-player > div > div.Layout-sc-1xcs6mc-0.cFsYRp.player-controls__left-control-group > div.InjectLayout-sc-1i43xsx-0.kBtJDm > button");
            Element vElement = null;
            for (Element e : elements) {
                if (e.hasAttr("data-a-player-state")) {
                    vElement = e;
                    break;
                }
            }

            if (vElement != null) {
                Element element = data.getHTMLDocument().selectFirst("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div > div:nth-child(5) > div > div > div > div.Layout-sc-1xcs6mc-0.iULZCz.vod-seekbar-time-labels > p:nth-child(2)");

                String hours = element.ownText().split(":")[0];
                String minutes = element.ownText().split(":")[1];
                String seconds = element.ownText().split(":")[2];

                long lhours = Long.parseLong(hours) * 60 * 60;
                long lminutes = Long.parseLong(minutes) * 60;
                long lseconds = Long.parseLong(seconds);
                long time = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;

                return time;
            }
        }

        return -1;
    }
}
