package me.Logicism.LogRPC.presence.game;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.core.data.WiimmfiData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

public class WiimmfiPresence extends Presence {

    public WiimmfiPresence(PresenceData data) {
        super(553230242987245569L, data);
    }

    @Override
    public String getDetails() {
        if (this.data instanceof BrowserHTMLData) {
            BrowserHTMLData data = (BrowserHTMLData) this.data;

            Element element = null;

            try {
                for (int i = 2; i < data.getHTMLDocument().selectFirst("body > table.table11 > tbody").children().size(); i++) {
                    Element e = data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(i);

                    if (e.child(0).text().equals(LogRPC.INSTANCE.getConfig().getWiimmfiFriendCode())) {
                        element = e;
                    }
                }
            } catch (NullPointerException e1) {
                return "";
            }

            if (data.getHTMLDocument().text().contains("Last track: ")) {
                try {
                    if (element.child(1).child(0).text().endsWith("viewer")) {
                        return "Spectating Match";
                    } else if (element.child(1).child(0).text().endsWith("connect")) {
                        return "Connecting to Match";
                    } else {
                        return data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(0).child(0).child(data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(0).child(0).children().size() - 1).child(0).text();
                    }
                } catch (IndexOutOfBoundsException e1) {

                }
            } else {
                return "Playing Custom Mode";
            }

            try {
                return data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(0).child(0).child(data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(0).child(0).children().size() - 1).child(0).text();
            } catch (IndexOutOfBoundsException e1) {
                return "";
            }
        } else {
            WiimmfiData data = (WiimmfiData) this.data;

            return data.getTitle();
        }
    }

    @Override
    public String getState() {
        if (this.data instanceof BrowserHTMLData) {
            BrowserHTMLData data = (BrowserHTMLData) this.data;

            if (!data.getHTMLDocument().text().contains("No room found!")) {
                String roomType = "Unknown";

                if (data.getHTMLDocument().text().contains("Worldwide room")) {
                    roomType = "Worldwide Match";
                } else if (data.getHTMLDocument().text().contains("Private room")) {
                    roomType = "Private Match";
                } else if (data.getHTMLDocument().text().contains("Continental room")) {
                    roomType = "Regional Match";
                } else if (data.getHTMLDocument().text().contains("Global room")) {
                    roomType = "Finding Match";
                }


                return roomType;
            } else {
                return "";
            }
        } else {
            WiimmfiData data = (WiimmfiData) this.data;

            return data.getRoomType();
        }
    }

    @Override
    public String getLargeImageKey() {
        return "mariokartwii";
    }

    @Override
    public String getLargeImageText() {
        if (this.data instanceof BrowserHTMLData) {
            BrowserHTMLData data = (BrowserHTMLData) this.data;

            Element element = null;

            try {
                for (int i = 2; i < data.getHTMLDocument().selectFirst("body > table.table11 > tbody").childNodeSize(); i++) {
                    Element e = data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(i);

                    if (e.child(0).child(0).text().equals(LogRPC.INSTANCE.getConfig().getWiimmfiFriendCode())) {
                        element = e;
                        break;
                    }
                }

                if (element.child(2).text().equals("Jap/0") || element.child(2).text().equals("Tai/4")) {
                    return "RMCJ01 (NTSC-J) - " + super.getLargeImageText();
                } else if (element.child(2).text().equals("Ame/1")) {
                    return "RMCE01 (NTSC-U) - " + super.getLargeImageText();
                } else if (element.child(2).text().equals("Eur/2") || element.child(2).text().equals("Aus/3")) {
                    return "RMCP01 (NTSC-E) - " + super.getLargeImageText();
                } else if (element.child(2).text().equals("Kor/5")) {
                    return "RMCK01 (NTSC-K) - " + super.getLargeImageText();
                } else if (element.child(2).text().equals("Chi/6")) {
                    return "Chinese Version - " + super.getLargeImageText();
                } else if (element.child(2).text().equals("CTGP")) {
                    return "CTGP v1.03 - " + super.getLargeImageText();
                }
            } catch (NullPointerException e) {

            }
        } else {
            WiimmfiData data = (WiimmfiData) this.data;

            if (data.getRegionType().equals("Jap/0") || data.getRegionType().equals("Tai/4")) {
                return "RMCJ01 (NTSC-J) - " + super.getLargeImageText();
            } else if (data.getRegionType().equals("Ame/1")) {
                return "RMCE01 (NTSC-U) - " + super.getLargeImageText();
            } else if (data.getRegionType().equals("Eur/2") || data.getRegionType().equals("Aus/3")) {
                return "RMCP01 (NTSC-E) - " + super.getLargeImageText();
            } else if (data.getRegionType().equals("Kor/5")) {
                return "RMCK01 (NTSC-K) - " + super.getLargeImageText();
            } else if (data.getRegionType().equals("Chi/6")) {
                return "Chinese Version - " + super.getLargeImageText();
            } else if (data.getRegionType().equals("CTGP")) {
                return "CTGP v1.03 - " + super.getLargeImageText();
            }
        }

        return super.getLargeImageText();
    }

    @Override
    public String getSmallImageKey() {
        return "mario_m";
    }

    @Override
    public String getSmallImageText() {
        if (this.data instanceof BrowserHTMLData) {
            BrowserHTMLData data = (BrowserHTMLData) this.data;

            Element element = null;

            for (int i = 2; i < data.getHTMLDocument().selectFirst("body > table.table11 > tbody").childNodeSize(); i++) {
                Element e = data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(i);

                if (e.child(0).child(0).text().equals(LogRPC.INSTANCE.getConfig().getWiimmfiFriendCode())) {
                    element = e;
                    break;
                }
            }

            return LogRPC.INSTANCE.getConfig().getWiimmfiFriendCode() + (data.getHTMLDocument().text().contains("Private room") ? "" : (element.child(3).text().equals("bt") ? " (" + element.child(7).text() + " VR)" : " (" + element.child(6).text() + " VR)"));
        } else {
            WiimmfiData data = (WiimmfiData) this.data;

            return LogRPC.INSTANCE.getConfig().getWiimmfiFriendCode() + (data.getPoints() == -1 ? "" : " (" + data.getPoints() + " VR)");
        }
    }

    @Override
    public String getMainButtonText() {
        return "View Room Information";
    }

    @Override
    public String getMainButtonURL() {
        return LogRPC.INSTANCE.getConfig().getWiimmfiPlayerURL();
    }

    @Override
    public long getStartTimestamp() {
        if (this.data instanceof BrowserHTMLData) {
            BrowserHTMLData data = (BrowserHTMLData) this.data;

            if (data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(0).child(0).text().contains("(last start")) {
                long time = Long.parseLong(data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(0).child(0).text().split("\\(last start ")[1].split("s ago\\)")[0]) * 1000;

                return time;
            } else {
                return 0;
            }
        } else {
            WiimmfiData data = (WiimmfiData) this.data;

            return data.getRaceStart();
        }
    }

    @Override
    public long getEndTimestamp() {
        return -1;
    }

    @Override
    public int getPartySize() {
        if (this.data instanceof BrowserHTMLData) {
            BrowserHTMLData data = (BrowserHTMLData) this.data;

            if (!data.getHTMLDocument().text().contains("No room found!")) {
                int playerCount = 0;

                try {
                    for (int i = (data.getHTMLDocument().text().contains("Last track: ") ? 2 : 1); i < data.getHTMLDocument().selectFirst("body > table.table11 > tbody").children().size(); i++) {
                        Element e = data.getHTMLDocument().selectFirst("body > table.table11 > tbody").child(i);

                        System.out.println(i);
                        System.out.println(e.child(1).text());

                        try {
                            if (!e.child(1).text().endsWith("viewer") && !e.child(1).text().endsWith("connect")) {
                                playerCount++;
                                if (e.child(8).html().contains("<br>")) {
                                    playerCount++;
                                }
                            }
                        } catch (IndexOutOfBoundsException e1) {

                        }
                    }

                    return playerCount - 1;
                } catch (NullPointerException e) {
                    return 0;
                }
            }
        } else {
            WiimmfiData data = (WiimmfiData) this.data;

            return data.getPlayersActive();
        }

        return 0;
    }

    @Override
    public int getPartyMax() {
        return 12;
    }
}
