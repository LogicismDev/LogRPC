package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class TwitterPresence extends Presence {

    public TwitterPresence(PresenceData data) {
        super(802958757909889054L, data);
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
        } else if (data.getURL().startsWith("https://x.com/messages/") || data.getURL().equals("https://x.com/messages")) {
            return "Reading DMs";
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
            Element userElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[2]/div/div/div/div[2]/div/div/div/span").first();
            if (userElement != null) {
                return "Viewing X.com User";
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
        } else if (data.getURL().startsWith("https://x.com/messages/")) {
            if (data.getURL().equals("https://x.com/messages/settings")) {
                return "Settings";
            } else if (data.getURL().equals("https://x.com/messages/compose")) {
                return "Composing New Message";
            } else {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div[1]/div/div[2]/div/div[1]/a/div/div[1]/span/span[1]").first();
                if (displayNameElement != null) {
                    Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div[1]/div/div[2]/div/div[2]/div/a/div/div/span").first();

                    return displayNameElement.text() + " (" + userNameElement.text() + ")";
                }
            }
        } else if (data.getURL().contains("/status/")) {
            if (data.getURL().contains("/photo/")) {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/section/div/div/div[1]/div[1]/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[1]/div/a/div/div[1]/span/span").first();
                Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/section/div/div/div[1]/div[1]/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div/div/a/div/span").first();

                return displayNameElement.text() + " (" + userNameElement.text() + ")";
            } else {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/section/div/div/div[1]/div[1]/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[1]/div/a/div/div[1]/span/span").first();
                Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/section/div/div/div[1]/div[1]/div/article/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div/div/a/div/span").first();

                return displayNameElement.text() + " (" + userNameElement.text() + ")";
            }
        } else {
            Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[2]/div/div/div/div[1]/div/div/span/span[1]").first();
            if (displayNameElement != null) {
                Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[2]/div/div/div/div[2]/div/div/div/span").first();

                return displayNameElement.text() + " (" + userNameElement.text() + ")";
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
