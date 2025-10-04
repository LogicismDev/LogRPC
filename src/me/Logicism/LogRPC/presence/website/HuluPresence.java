package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

public class HuluPresence extends Presence {

    public HuluPresence(PresenceData data) {
        super(607719679011848220L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public DisplayType getDisplayType() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/watch/")) {
            Element element = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div:nth-child(1) > div > div.PlayerMetadata__hitRegion > div.PlayerMetadata__title > div.PlayerMetadata__titleText > div > span");
            if (element != null) {
                return DisplayType.DETAILS;
            } else {
                return DisplayType.NAME;
            }
        }

        return DisplayType.NAME;
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().endsWith("/home")) {
            return "Browsing Hulu Home";
        } else if (data.getURL().endsWith("/tv")) {
            return "Browsing TV";
        } else if (data.getURL().endsWith("/movies")) {
            return "Browsing Movies";
        } else if (data.getURL().endsWith("/news-on-hulu")) {
            return "Browsing News on Hulu";
        } else if (data.getURL().endsWith("/my-stuff")) {
            return "Browsing My Stuff";
        } else if (data.getURL().endsWith("/hubs")) {
            return "Browsing Hubs";
        } else if (data.getURL().endsWith("/latino")) {
            return "Browsing Hispanic and Latin American Stories";
        } else if (data.getURL().endsWith("/lgbtq")) {
            return "Browsing Hulu has Pride";
        } else if (data.getURL().endsWith("/reality-tv")) {
            return "Browsing Hulu Gets Real";
        } else if (data.getURL().endsWith("/asian_stories")) {
            return "Browsing Asian and Pacific Islander Stories";
        } else if (data.getURL().endsWith(("/stand-up"))) {
            return "Browsing Hularious";
        } else if (data.getURL().endsWith("/made_by_her")) {
            return "Browsing Her Stories";
        } else if (data.getURL().endsWith("/adult-animation")) {
            return "Browsing Hulu Animayhem";
        } else if (data.getURL().endsWith("/black-stories")) {
            return "Browsing Hulu Black Stories Always";
        } else if (data.getURL().endsWith("/hotstar_on_hulu")) {
            return "Browsing Hotstar on Hulu";
        } else if (data.getURL().endsWith("/originals")) {
            return "Browsing Hulu Originals";
        } else if (data.getURL().contains("/search")) {
            return "Browsing Hulu Search";
        } else if (data.getURL().contains("/series")) {
            return "Browsing Hulu Series";
        } else if (data.getURL().contains("/watch/")) {
            Element element = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div:nth-child(1) > div > div.PlayerMetadata__hitRegion > div.PlayerMetadata__title > div.PlayerMetadata__titleText > div > span");
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

        if (data.getURL().contains("/watch/")) {
            Element element = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div:nth-child(1) > div > div.PlayerMetadata__hitRegion > div.PlayerMetadata__subTitle");
            if (element != null && element.text().contains(" • ")) {
                return element.text().split(" • ")[0];
            }
        } else if (data.getURL().contains("/series")) {
            Element element = data.getHTMLDocument().selectFirst("#LevelTwo__scroll-area > div.LevelTwo__container > div > div.SimpleModalNav.SimpleModalNav--transparent > div.SimpleModalNav__title-container.SimpleModalNav__title-container--hasScrolled");
            if (element != null) {
                return element.text();
            }
        }
        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "https://i.imgur.com/kJKqfdw.png";
    }

    @Override
    public String getSmallImageKey() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/watch/")) {
            Element element = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PlayButton");
            Element element1 = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PauseButton");

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

        if (data.getURL().contains("/watch/")) {
            Element element = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PlayButton");
            Element element1 = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(4) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PauseButton");

            if (element != null) {
                return "Paused";
            } else if (element1 != null) {
                return "Playing";
            }
        }

        return "Reading";
    }

    @Override
    public long getStartTimestamp() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().contains("/watch/")) {
            Element element = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PlayButton");
            Element element1 = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(4) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PauseButton");

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

        if (data.getURL().contains("/watch/")) {
            Element element = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PlayButton");
            Element element1 = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.BottomUiControls > div.PlaybackControls.BottomUiControls__playbackControls > div.PlayerButton.PlayerControlsButton.PlaybackControls__item.PauseButton");

            if (element != null) {
                return -1;
            } else if (element1 != null) {
                Element timeRemaining = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.Timeline > div.Timestamp.Timeline__remainingTimestamp");
                Element currentTime = data.getHTMLDocument().selectFirst("#web-player-app > div.ControlsContainer > div:nth-child(3) > div > div:nth-child(1) > div.Timeline > div.Timeline__sliderContainer > div.Timestamp.Timeline__currentTimestamp");

                long currentTimestamp = 0;
                if (currentTime.text().split(":").length == 3) {
                    String hours = currentTime.text().split(":")[0];
                    String minutes = currentTime.text().split(":")[1];
                    String seconds = currentTime.text().split(":")[2];

                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    currentTimestamp = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;
                } else if (currentTime.text().split(":").length == 2) {
                    String minutes = currentTime.text().split(":")[0];
                    String seconds = currentTime.text().split(":")[1];

                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    currentTimestamp = Long.sum(lminutes, lseconds) * 1000L;
                }

                long remainingTimestamp = 0;
                if (timeRemaining.text().substring(1).split(":").length == 3) {
                    String hours = timeRemaining.text().substring(1).split(":")[0];
                    String minutes = timeRemaining.text().substring(1).split(":")[1];
                    String seconds = timeRemaining.text().substring(1).split(":")[2];

                    long lhours = Long.parseLong(hours) * 60 * 60;
                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    remainingTimestamp = Long.sum(Long.sum(lhours, lminutes), lseconds) * 1000L;
                } else if (timeRemaining.text().substring(1).split(":").length == 2) {
                    String minutes = timeRemaining.text().substring(1).split(":")[0];
                    String seconds = timeRemaining.text().substring(1).split(":")[1];

                    long lminutes = Long.parseLong(minutes) * 60;
                    long lseconds = Long.parseLong(seconds);
                    remainingTimestamp = Long.sum(lminutes, lseconds) * 1000L;
                }

                return currentTimestamp + remainingTimestamp;
            }
        }

        return -1;
    }
}
