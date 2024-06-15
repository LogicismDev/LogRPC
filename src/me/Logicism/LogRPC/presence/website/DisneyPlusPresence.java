package me.Logicism.LogRPC.presence.website;

import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DisneyPlusPresence extends Presence {

    public DisneyPlusPresence(PresenceData data) {
        super(630236276829716483L, data);
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().endsWith("/home")) {
            return "Browsing Disney+ Home";
        } else if (data.getURL().endsWith("/brand/disney")) {
            return "Browsing Disney";
        } else if (data.getURL().endsWith("/brand/pixar")) {
            return "Browsing Pixar";
        } else if (data.getURL().endsWith("/brand/marvel")) {
            return "Browsing Marvel";
        } else if (data.getURL().endsWith("/brand/star-wars")) {
            return "Browsing Star Wars";
        } else if (data.getURL().endsWith("/brand/national-geographic")) {
            return "Browsing National Geographic";
        } else if (data.getURL().endsWith("/watchlist")) {
            return "Browsing Disney+ Watchlist";
        } else if (data.getURL().endsWith("/originals")) {
            return "Browsing Disney+ Originals";
        } else if (data.getURL().endsWith(("/movies")) || data.getURL().contains("/movies/")) {
            return "Browsing Disney+ Movies";
        } else if (data.getURL().endsWith("/series") || data.getURL().contains("/series/")) {
            return "Browsing Disney+ Series";
        } else if (data.getURL().contains("/search")) {
            return "Browsing Disney+ Search";
        } else if (data.getURL().contains("/groupwatch/")) {
            return "Starting Disney+ GroupWatch";
        } else if (data.getURL().contains("/video/") || data.getURL().contains("/play/")) {
            Element element = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__header > div.title-bug-area > div > button.control-icon-btn.title-btn > div.title-field.body-copy");
            if (element != null) {
                return element.text();
            } else {
                return "";
            }
        }

        return "Browsing Disney+";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/video/") || data.getURL().contains("/play/")) {
            Element element = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__header > div.title-bug-area > div > button.control-icon-btn.title-btn > div.subtitle-field");
            if (element != null) {
                return element.text();
            } else {
                return "";
            }
        }
        return "";
    }

    @Override
    public String getLargeImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;
        if (data.getURL().startsWith("https://www.hotstar.com/")) {
            return "disneyplus-hotstar-logo";
        } else {
            return "disneyplus-logo";
        }
    }

    @Override
    public String getSmallImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/video/") || data.getURL().contains("/play/")) {
            Element element = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.play-icon.play-pause-icon");
            Element element1 = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.pause-icon.play-pause-icon");

            if (element != null) {
                return "pause";
            } else if (element1 != null) {
                return "play";
            }
        }

        return "";
    }

    @Override
    public String getSmallImageText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/video/") || data.getURL().contains("/play/")) {
            Element element = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.play-icon.play-pause-icon");
            Element element1 = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.pause-icon.play-pause-icon");

            if (element != null) {
                return "Paused";
            } else if (element1 != null) {
                return "Playing";
            }
        }

        return "Reading";
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/groupwatch/") || data.getURL().contains("/video/") && data.getURL().contains("?groupwatchId=")) {
            return "Join GroupWatch";
        }

        return null;
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/groupwatch/")) {
            return data.getURL();
        } else if ((data.getURL().contains("/video/") || data.getURL().contains("/play/")) && data.getURL().contains("?groupwatchId=")) {
            return (data.getURL().contains("hotstar") ? "https://www.hotstar.com/groupwatch/" : "https://www.disneyplus.com/groupwatch/") + data.getURL().split("\\?groupwatchId=")[1];
        }

        return null;
    }

    @Override
    public int getPartySize() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if ((data.getURL().contains("/video/") || data.getURL().contains("/play/")) && data.getURL().contains("?groupwatchId=")) {
            Element element = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div.overlay.overlay__controls.overlay__controls--visually-hide > div > div.controls__header > div.group-profiles-control > button > div > div");

            return Integer.parseInt(element.text());
        }

        return super.getPartySize();
    }

    @Override
    public int getPartyMax() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if ((data.getURL().contains("/video/") || data.getURL().contains("/play/")) && data.getURL().contains("?groupwatchId=")) {
            return 7;
        }

        return -1;
    }

    @Override
    public long getStartTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/video/") || data.getURL().contains("/play/")) {
            Element element = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.play-icon.play-pause-icon");
            Element element1 = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.pause-icon.play-pause-icon");

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

        if (data.getURL().contains("/video/") || data.getURL().contains("/play/")) {
            Element element = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.play-icon.play-pause-icon");
            Element element1 = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__wrapper.display-flex > div.controls__center > button.control-icon-btn.pause-icon.play-pause-icon");

            if (element != null) {
                return -1;
            } else if (element1 != null) {
                Element timeRemaining = data.getHTMLDocument().selectFirst("#hudson-wrapper > div > div > div.btm-media-overlays-container > div > div > div.controls__footer.display-flex > div.controls__footer__progressWrapper.display-flex > div.time-remaining-label.body-copy");

                if (timeRemaining.text().split(":").length == 3) {
                    String hours = timeRemaining.text().split(":")[0];
                    String minutes = timeRemaining.text().split(":")[1];
                    String seconds = timeRemaining.text().split(":")[2];

                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    long time = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;

                    return time;
                } else if (timeRemaining.text().split(":").length == 2) {
                    String minutes = timeRemaining.text().split(":")[0];
                    String seconds = timeRemaining.text().split(":")[1];

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
