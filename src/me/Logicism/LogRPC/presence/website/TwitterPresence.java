package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TwitterPresence extends Presence {

    private String nitterDisplayName;
    private String nitterUsername;

    public TwitterPresence(PresenceData data) {
        super(802958757909889054L, data);

        try {
            if (LogRPC.INSTANCE.getConfig().isNitterScraperEnabled()) {
                BrowserHTMLData bData = (BrowserHTMLData) this.data;
                URL url = new URL(bData.getURL());

                if (url.getHost().equals("x.com")) {
                    Map<String, String> headers = new HashMap<>();

                    if (!bData.getURL().startsWith("https://x.com/i/") && !bData.getURL().startsWith("https://x.com/explore/") && !bData.getURL().startsWith("https://x.com/notifications/")) {
                        BrowserData bd = BrowserClient.executeGETRequest(new URL(LogRPC.INSTANCE.getConfig().getNitterScraperInstance() + bData.getURL().substring("https://x.com/".length())), headers);
                        Document document = Jsoup.parse(BrowserClient.requestToString(bd.getResponse()));

                        nitterDisplayName = document.getElementsByClass("profile-card-fullname").text();
                        nitterUsername = document.getElementsByClass("profile-card-username").text();
                    } else {
                        BrowserData bd = BrowserClient.executeGETRequest(new URL(LogRPC.INSTANCE.getConfig().getNitterScraperInstance() + bData.getURL().substring("https://x.com/".length())), headers);
                        Document document = Jsoup.parse(BrowserClient.requestToString(bd.getResponse()));

                        nitterDisplayName = document.getElementsByClass("fullname").text();
                        nitterUsername = document.getElementsByClass("username").text();
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
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().equals("https://x.com/home")) {
            return "Browsing Homepage";
        } else if (data.getURL().endsWith("https://x.com/explore") || data.getURL().startsWith("https://x.com/explore/tabs/")) {
            return "Browsing Explore";
        } else if (data.getURL().endsWith("https://x.com/notifications") || data.getURL().endsWith("https://x.com/notifications/verified") || data.getURL().endsWith("https://x.com/notifications/mentions")) {
            return "Browsing Notifications";
        } else if (data.getURL().startsWith("https://x.com/i/chat/") || data.getURL().equals("https://x.com/i/chat/")) {
            return "Reading Chats";
        } else if (data.getURL().startsWith("https://x.com/i/bookmarks")) {
            return "Browsing Bookmarks";
        } else if (data.getURL().endsWith("/lists") || data.getURL().startsWith("https://x.com/i/lists/")) {
            return "Browsing Lists";
        } else if (data.getURL().endsWith("/topics")) {
            return "Browsing Topics";
        } else if (data.getURL().startsWith("https://x.com/settings/")) {
            return "Browsing X.com Settings";
        } else if (data.getURL().contains("/status/")) {
            if (data.getURL().contains("/photo/")) {
                return "Viewing Photo";
            } else {
                return "Reading Tweet";
            }
        } else if (data.getURL().endsWith("/i/grok")) {
            return "Using Grok";
        } else if (data.getURL().endsWith("/jobs")) {
            return "Browsing Job Search";
        } else if (data.getURL().contains("/communities/")) {
            return "Browsing Communities";
        } else {
            if (LogRPC.INSTANCE.getConfig().isNitterScraperEnabled()) {
                return "Viewing X.com User";
            } else {
                Element userNameElement = data.getHTMLDocument().select("#react-root > div > div > div.css-175oi2r.r-1f2l425.r-13qz1uu.r-417010.r-18u37iz > main > div > div > div > div.css-175oi2r.r-kemksi.r-1kqtdi0.r-1ua6aaf.r-th6na.r-1phboty.r-16y2uox.r-184en5c.r-1abdc3e.r-1lg4w6u.r-f8sm7e.r-13qz1uu.r-1ye8kvj > div > div:nth-child(3) > div > div > div:nth-child(1) > div > div.css-175oi2r.r-18u37iz.r-1w6e6rj.r-6gpygo.r-14gqq1x > div.css-175oi2r.r-eqz5dr.r-1wbh5a2.r-1wron08 > div > div > div.css-175oi2r.r-1awozwy.r-18u37iz.r-1wbh5a2 > div > div > div > span").first();
                if (userNameElement != null) {
                    return "Viewing X.com User";
                }
            }
        }

        return "Browsing X.com";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().endsWith("https://x.com/explore") || data.getURL().startsWith("https://x.com/explore/tabs/for-you")) {
            return "For you";
        } else if (data.getURL().startsWith("https://x.com/explore/tabs/trending")) {
            return "Trending";
        } else if (data.getURL().startsWith("https://x.com/explore/tabs/news")) {
            return "News";
        } else if (data.getURL().startsWith("https://x.com/explore/tabs/sports")) {
            return "Sports";
        } else if (data.getURL().startsWith("https://x.com/explore/tabs/entertainment")) {
            return "Entertainment";
        } else if (data.getURL().equals("https://x.com/notifications")) {
            return "All";
        } else if (data.getURL().equals("https://x.com/notifications/verified")) {
            return "Verified";
        } else if (data.getURL().equals("https://x.com/notifications/mentions")) {
            return "Mentions";
        } else if (data.getURL().startsWith("https://x.com/i/chat")) {
            if (data.getURL().equals("https://x.com/i/chat/settings")) {
                return "Settings";
            } else {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div/div/div[2]/div/div/div[1]/div[2]/div[2]/div/div[1]").first();
                return displayNameElement.text();
            }
        } else if (data.getURL().contains("/status/")) {
            if (LogRPC.INSTANCE.getConfig().isNitterScraperEnabled()) {
                return nitterDisplayName + " (" + nitterUsername + ")";
            } else {
                if (data.getURL().contains("/photo/")) {
                    Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/section/div/div/div[1]/div/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[1]/div/a/div/div[1]/span/span").first();
                    Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/section/div/div/div[1]/div/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div/div/a/div/span").first();

                    return displayNameElement.text() + " (" + userNameElement.text() + ")";
                } else {
                    Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[1]/div/section/div/div/div[1]/div/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[1]/div/a/div/div[1]/span/span").first();
                    Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[1]/div/section/div/div/div[1]/div/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div/div/a/div/span").first();

                    return displayNameElement.text() + " (" + userNameElement.text() + ")";
                }
            }
        } else {
            if (LogRPC.INSTANCE.getConfig().isNitterScraperEnabled()) {
                return nitterDisplayName + " (" + nitterUsername + ")";
            } else {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]/div/div/span/span[1]    ").first();
                if (displayNameElement != null) {
                    Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div/div[2]/div[1]/div/div/div[2]/div/div/div/span").first();

                    return displayNameElement.text() + " (" + userNameElement.text() + ")";
                }
            }
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "X.com";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

    @Override
    public String getStateURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/status/")) {
            return data.getURL();
        } else if (!data.getURL().equals("https://x.com/home") && !data.getURL().endsWith("https://x.com/explore") && !data.getURL().startsWith("https://x.com/explore/tabs/") && !data.getURL().equals("https://x.com/notifications") && !data.getURL().equals("https://x.com/notifications/verified") && !data.getURL().equals("https://x.com/notifications/mentions") && !data.getURL().equals("https://x.com/notifications/mentions") && !data.getURL().equals("https://x.com/messages") && !data.getURL().startsWith("https://x.com/messages/") && !data.getURL().endsWith("/i/grok") && !data.getURL().endsWith("/jobs") && !data.getURL().contains("/communities/")) {
            return data.getURL();
        }

        return null;
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/status/")) {
            return data.getURL();
        } else if (!data.getURL().equals("https://x.com/home") && !data.getURL().endsWith("https://x.com/explore") && !data.getURL().startsWith("https://x.com/explore/tabs/") && !data.getURL().equals("https://x.com/notifications") && !data.getURL().equals("https://x.com/notifications/verified") && !data.getURL().equals("https://x.com/notifications/mentions") && !data.getURL().equals("https://x.com/notifications/mentions") && !data.getURL().equals("https://x.com/messages") && !data.getURL().startsWith("https://x.com/messages/") && !data.getURL().endsWith("/i/grok") && !data.getURL().endsWith("/jobs") && !data.getURL().contains("/communities/")) {
            return data.getURL();
        }

        return null;
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/status/")) {
            if (data.getURL().contains("/photo/")) {
                return "View Photo";
            } else {
                return "View Tweet";
            }
        } else if (!data.getURL().equals("https://x.com/home") && !data.getURL().endsWith("https://x.com/explore") && !data.getURL().startsWith("https://x.com/explore/tabs/") && !data.getURL().equals("https://x.com/notifications") && !data.getURL().equals("https://x.com/notifications/verified") && !data.getURL().equals("https://x.com/notifications/mentions") && !data.getURL().equals("https://x.com/notifications/mentions") && !data.getURL().equals("https://x.com/messages") && !data.getURL().startsWith("https://x.com/messages/") && !data.getURL().endsWith("/i/grok") && !data.getURL().endsWith("/jobs") && !data.getURL().contains("/communities/")) {
            return "View User";
        }

        return null;
    }
}
