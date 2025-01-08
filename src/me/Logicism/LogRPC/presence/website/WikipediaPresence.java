package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WikipediaPresence extends Presence {

    public WikipediaPresence(PresenceData data) {
        super(609364070684033044L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().equals("https://www.wikipedia.org/")) {
            return "Browsing Home Page";
        } else if (data.getURL().contains("/wiki/")) {
            String subString = data.getURL().split("/wiki/")[1];
            if (subString.startsWith("User_talk:")) {
                return "Visiting a User Talk Page";
            } else if (subString.startsWith("Special:Contributions")) {
                return "Visiting User Contributions";
            } else {
                return "Visiting a Page";
            }
        } else if (data.getURL().contains("/w/index.php")) {
            if (data.getURL().contains("&action=edit")) {
                return "Editing a Page";
            } else if (data.getURL().contains("&action=history") || data.getURL().contains("&type=revision")) {
                return "Viewing Page History";
            } else if (data.getURL().contains("?title=Special:UserLogin")) {
                return "Logging In";
            } else if (data.getURL().contains("?title=Special:CreateAccount")) {
                return "Creating an Account";
            }
        }

        return "Visiting Wikipedia";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/wiki/")) {
            String subString = data.getURL().split("/wiki/")[1];
            if (subString.startsWith("User_talk:")) {
                try {
                    return URLDecoder.decode(subString.substring("User_talk:".length()), "UTF-8").replace("_", " ");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if (subString.startsWith("Special:Contributions")) {
                return subString.split("/")[1];
            } else {
                if (subString.contains("#")) {
                    subString = subString.split("#")[0];
                }
                try {
                    return URLDecoder.decode(subString, "UTF-8").replace("_", " ");
                } catch (UnsupportedEncodingException e) {
                    return "";
                }
            }
        } else if (data.getURL().contains("/w/index.php")) {
            if (data.getURL().contains("&action=edit")) {
                String subString = data.getURL().split("title=")[1].split("&action=edit")[0];

                try {
                    return URLDecoder.decode(subString, "UTF-8").replace("_", " ");
                } catch (UnsupportedEncodingException e) {
                    return "";
                }
            } else if (data.getURL().contains("&action=history")) {
                String subString = data.getURL().split("title=")[1].split("&action=history")[0];

                try {
                    return URLDecoder.decode(subString, "UTF-8").replace("_", " ");
                } catch (UnsupportedEncodingException e) {
                    return "";
                }
            } else if (data.getURL().contains("&type=revision")) {
                String subString = data.getURL().split("title=")[1].split("&type=revision")[0];

                return subString;
            }
        }

        return "";
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (!data.getURL().equals("https://www.wikipedia.org/")) {
            return "Visit Wikipedia Page";
        } else {
            return null;
        }
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (!data.getURL().equals("https://www.wikipedia.org/")) {
            String url = data.getURL();
            if (data.getURL().contains("#/media/")) {
                url = url.split("#/media/")[0];
            }
            return url;
        } else {
            return null;
        }
    }

    @Override
    public String getLargeImageKey() {
        return "lg";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
