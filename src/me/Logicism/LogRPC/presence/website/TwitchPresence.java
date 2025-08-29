package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;
import me.Logicism.LogRPC.presence.Presence;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TwitchPresence extends Presence {

    private JSONObject safeTwitchAPIResult;

    public TwitchPresence(PresenceData data) {
        super(802958789555781663L, data);

        try {
            if (LogRPC.INSTANCE.getConfig().isSafeTwitchAPIEnabled()) {
                BrowserHTMLData bData = (BrowserHTMLData) this.data;
                URL url = new URL(bData.getURL());

                if (url.getHost().equals("www.twitch.tv")) {
                    if (bData.getURL().contains("https://www.twitch.tv/videos")) {
                        String id = bData.getURL().substring("https://www.twitch.tv/videos".length()).split("\\?")[0];

                        Map<String, String> headers = new HashMap<>();

                        BrowserData bd = BrowserClient.executeGETRequest(new URL(LogRPC.INSTANCE.getConfig().getSafeTwitchBackendAPIInstance() + "api/vods/" + id), headers);
                        safeTwitchAPIResult = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
                    } else if (bData.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                        String id = bData.getURL().substring("https://www.twitch.tv/moderator/".length()).split("\\?")[0];

                        Map<String, String> headers = new HashMap<>();

                        BrowserData bd = BrowserClient.executeGETRequest(new URL(LogRPC.INSTANCE.getConfig().getSafeTwitchBackendAPIInstance() + "api/users/" + id), headers);
                        safeTwitchAPIResult = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
                    } else if (!bData.getURL().contains("https://www.twitch.tv/directory") && !bData.getURL().contains("https://www.twitch.tv/settings") && !bData.getURL().contains("https://www.twitch.tv/p/") && !bData.getURL().contains("https://www.twitch.tv/turbo") && !bData.getURL().contains("https://www.twitch.tv/popout")) {
                        String id = bData.getURL().substring("https://www.twitch.tv/".length()).split("\\?")[0];

                        Map<String, String> headers = new HashMap<>();

                        BrowserData bd = BrowserClient.executeGETRequest(new URL(LogRPC.INSTANCE.getConfig().getSafeTwitchBackendAPIInstance() + "api/users/" + id), headers);
                        safeTwitchAPIResult = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
                    }
                }
            }
        } catch (IOException e) {

        }
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.STATE;
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
                    if (safeTwitchAPIResult != null) {
                        return safeTwitchAPIResult.getJSONObject("data").getString("title");
                    } else {
                        Elements titleElements = data.getHTMLDocument().select("#live-channel-stream-information > div > div > div.metadata-layout__split-top > div.Layout-sc-1xcs6mc-0.iULZCz > div.Layout-sc-1xcs6mc-0.jPmKIH > p");
                        Element titleElement = null;
                        for (Element e : titleElements) {
                            if (e.hasAttr("data-a-target") && e.attr("data-a-target").equals("stream-title")) {
                                titleElement = e;
                                break;
                            }
                        }

                        return titleElement.textNodes().get(0).text();
                    }
                } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return safeTwitchAPIResult.getJSONObject("data").getJSONObject("stream").getString("title");
                        }
                    } else {
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
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return safeTwitchAPIResult.getJSONObject("data").getJSONObject("stream").getString("title");
                        }
                    } else {
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
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return safeTwitchAPIResult.getJSONObject("data").getJSONObject("streamer").getString("username");
                        }
                    } else {
                        Elements titleElements = data.getHTMLDocument().getElementsByTag("h1");
                        Element titleElement = null;
                        for (Element e : titleElements) {
                            if (e.hasClass("tw-title")) {
                                titleElement = e;
                                break;
                            }
                        }

                        return titleElement.textNodes().get(0).text();
                    }
                } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                    if (safeTwitchAPIResult != null) {
                        return safeTwitchAPIResult.getJSONObject("data").getString("username");
                    } else {
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
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    if (safeTwitchAPIResult != null) {
                        return safeTwitchAPIResult.getJSONObject("data").getString("username");
                    } else {
                        Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                        Element videoElement = videoElements.first();

                        Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.eTyMgg > div.Layout-sc-1xcs6mc-0.hHGLGY > div > div > div.Layout-sc-1xcs6mc-0.fcLIrX.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div > div");
                        if (videoElement != null && liveElement != null) {
                            Element titleElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.eTyMgg > div.Layout-sc-1xcs6mc-0.ceqRQg > div.Layout-sc-1xcs6mc-0.foHQyB > div.Layout-sc-1xcs6mc-0.kxWyUQ > div > div.Layout-sc-1xcs6mc-0.fsLBGq > p");

                            return titleElement.ownText();
                        } else {
                            return data.getTitle().substring(0, data.getTitle().indexOf(" - Twitch"));
                        }
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
            } else if (data.getURL().startsWith("https://www.twitch.tv/moderator/")) {
                if (safeTwitchAPIResult != null && safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                    return "https://static-cdn.jtvnw.net/previews-ttv/live_user_" + data.getURL().substring("https://www.twitch.tv/moderator/".length()).split("\\?")[0] + "-1280x720.jpg";
                } else {
                    return "twitch";
                }
            } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                if (safeTwitchAPIResult != null && safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                    return "https://static-cdn.jtvnw.net/previews-ttv/live_user_" + data.getURL().substring("https://www.twitch.tv/".length()).split("\\?")[0] + "-1280x720.jpg";
                } else {
                    return "twitch";
                }
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
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return "live";
                        }
                    } else {
                        Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                        if (liveElement != null) {
                            return "live";
                        }
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return "live";
                        }
                    } else {
                        Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                        Element videoElement = videoElements.first();

                        Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.iXRytg > div > div > div.Layout-sc-1xcs6mc-0.kpHsJz.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div");
                        if (videoElement != null && liveElement != null) {
                            return "live";
                        }
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
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return "Live";
                        }
                    } else {
                        Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                        if (liveElement != null) {
                            return "Live";
                        }
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return "Live";
                        }
                    } else {
                        Elements videoElements = data.getHTMLDocument().select("#root > div > div.Layout-sc-1xcs6mc-0.lcpZLv > div > main > div.root-scrollable.scrollable-area.scrollable-area--suppress-scroll-x > div.simplebar-scroll-content > div > div > div.InjectLayout-sc-1i43xsx-0.persistent-player > div > div.Layout-sc-1xcs6mc-0.video-player > div > div.Layout-sc-1xcs6mc-0.video-ref > div > div");
                        Element videoElement = videoElements.first();

                        Element liveElement = data.getHTMLDocument().selectFirst("#live-channel-stream-information > div > div > div.Layout-sc-1xcs6mc-0.kYbRHX > div.Layout-sc-1xcs6mc-0.iXRytg > div > div > div.Layout-sc-1xcs6mc-0.kpHsJz.avatar--t0iT1 > a > div > div.Layout-sc-1xcs6mc-0.liveIndicator--x8p4l > div");
                        if (videoElement != null && liveElement != null) {
                            return "Live";
                        }
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
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return "Watch Stream";
                        } else {
                            return "Visit Channel";
                        }
                    } else {
                        Element liveElement = data.getHTMLDocument().getElementsMatchingOwnText("Live Now").get(0);

                        if (liveElement != null) {
                            return "Watch Stream";
                        }
                    }
                } else if (!data.getURL().contains("https://www.twitch.tv/directory") && !data.getURL().contains("https://www.twitch.tv/settings") && !data.getURL().contains("https://www.twitch.tv/p/") && !data.getURL().contains("https://www.twitch.tv/turbo") && !data.getURL().contains("https://www.twitch.tv/popout")) {
                    if (safeTwitchAPIResult != null) {
                        if (safeTwitchAPIResult.getJSONObject("data").getBoolean("isLive")) {
                            return "Watch Stream";
                        } else {
                            return "Visit Channel";
                        }
                    } else {
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
    public String getDetailsURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (!data.getURL().contains("/moderator/")) {
                    return data.getURL();
                }
            }
        } catch (MalformedURLException e) {
            return null;
        }

        return null;
    }

    @Override
    public String getStateURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        try {
            URL url = new URL(data.getURL());

            if (url.getHost().equals("www.twitch.tv")) {
                if (data.getURL().contains("/moderator/")) {
                    return data.getURL().replace("/moderator", "");
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
