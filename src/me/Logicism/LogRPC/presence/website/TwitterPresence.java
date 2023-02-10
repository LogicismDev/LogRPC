package me.Logicism.LogRPC.presence.website;

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
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().equals("https://twitter.com/home")) {
            return "Browsing Homepage";
        } else if (data.getURL().endsWith("https://twitter.com/explore") || data.getURL().startsWith("https://twitter.com/explore/tabs/")) {
            return "Browsing Explore";
        } else if (data.getURL().endsWith("https://twitter.com/notifications")) {
            return "Browsing Notifications";
        } else if (data.getURL().startsWith("https://twitter.com/messages/") || data.getURL().equals("https://twitter.com/messages")) {
            return "Reading DMs";
        } else if (data.getURL().startsWith("https://twitter.com/i/bookmarks")) {
            return "Browsing Bookmarks";
        } else if (data.getURL().endsWith("/lists") || data.getURL().startsWith("https://twitter.com/i/lists/")) {
            return "Browsing Lists";
        } else if (data.getURL().endsWith("/topics")) {
            return "Browsing Topics";
        } else if (data.getURL().startsWith("https://twitter.com/settings/")) {
            return "Browsing Twitter Settings";
        } else if (data.getURL().contains("/status/")) {
            if (data.getURL().contains("/photo/")) {
                return "Viewing Photo";
            } else {
                return "Reading Tweet";
            }
        } else {
            Element userElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[1]/div/div[1]/div/div/span[1]/span").first();
            if (userElement != null) {
                return "Viewing Twitter User";
            }
        }

        return "Browsing Twitter";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().endsWith("https://twitter.com/explore") || data.getURL().startsWith("https://twitter.com/explore/tabs/for-you")) {
            return "For you";
        } else if (data.getURL().startsWith("https://twitter.com/explore/tabs/covid-19")) {
            return "COVID-19";
        } else if (data.getURL().startsWith("https://twitter.com/explore/tabs/trending")) {
            return "Trending";
        } else if (data.getURL().startsWith("https://twitter.com/explore/tabs/news")) {
            return "News";
        } else if (data.getURL().startsWith("https://twitter.com/explore/tabs/sports")) {
            return "Sports";
        } else if (data.getURL().startsWith("https://twitter.com/explore/tabs/entertainment")) {
            return "Entertainment";
        } else if (data.getURL().equals("https://twitter.com/notifications")) {
            return "All";
        } else if (data.getURL().equals("https://twitter.com/notifications/mentions")) {
            return "Mentions";
        } else if (data.getURL().startsWith("https://twitter.com/messages/")) {
            if (data.getURL().equals("https://twitter.com/messages/settings")) {
                return "Settings";
            } else if (data.getURL().equals("https://twitter.com/messages/compose")) {
                return "Composing New Message";
            } else {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[1]/div/div/div/div/div[2]/div/div[2]/div/h2/div/div/div/div/span[1]/span/span").first();
                if (displayNameElement != null) {
                    Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div[1]/div/div/div/div/div[2]/div/div[2]/div/div/span").first();

                    return displayNameElement.text() + " (" + userNameElement.text() + ")";
                }
            }
        } else if (data.getURL().contains("/status/")) {
            if (data.getURL().contains("/photo/")) {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/section/div/div/div[1]/div/div/div[1]/article/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div[1]/div/a/div/div[1]/span/span").first();
                Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/section/div/div/div[1]/div/div/div[1]/article/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div[2]/div/div/a/div/span").first();

                return displayNameElement.text() + " (" + userNameElement.text() + ")";
            } else {
                Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/section/div/div/div[1]/div/div/div[1]/article/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div[1]/div/a/div/div[1]/span/span").first();
                Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/section/div/div/div[1]/div/div/div[1]/article/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div[2]/div/div/a/div/span").first();

                return displayNameElement.text() + " (" + userNameElement.text() + ")";
            }
        } else {
            Element displayNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[1]/div/div[1]/div/div/span[1]/span").first();
            if (displayNameElement != null) {
                Element userNameElement = data.getHTMLDocument().selectXpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[1]/div/div[2]/div/div/div/span").first();

                return displayNameElement.text() + " (" + userNameElement.text() + ")";
            }
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "twitter";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/status/")) {
            return data.getURL();
        } else if (!data.getURL().equals("https://twitter.com/home") && !data.getURL().endsWith("https://twitter.com/explore") && !data.getURL().startsWith("https://twitter.com/explore/tabs/") && !data.getURL().equals("https://twitter.com/notifications") && !data.getURL().equals("https://twitter.com/notifications/mentions") && !data.getURL().equals("https://twitter.com/messages") && !data.getURL().startsWith("https://twitter.com/messages/")) {
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
        } else if (!data.getURL().equals("https://twitter.com/home") && !data.getURL().endsWith("https://twitter.com/explore") && !data.getURL().startsWith("https://twitter.com/explore/tabs/") && !data.getURL().equals("https://twitter.com/notifications") && !data.getURL().equals("https://twitter.com/notifications/mentions") && !data.getURL().equals("https://twitter.com/messages") && !data.getURL().startsWith("https://twitter.com/messages/")) {
            return "View User";
        }

        return null;
    }
}
