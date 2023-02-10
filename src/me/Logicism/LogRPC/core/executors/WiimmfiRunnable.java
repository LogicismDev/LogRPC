package me.Logicism.LogRPC.core.executors;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.WiimmfiData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WiimmfiRunnable implements Runnable {

    private boolean sessionCreated = false;

    @Override
    public void run() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        while (LogRPC.INSTANCE.getWiimmfiMenuItem().getState()) {
            try {
                if (!sessionCreated) {
                    BrowserData bd = BrowserClient.executePOSTRequest(new URL(LogRPC.INSTANCE.getConfig().getFlareSolverrURL()), new JSONObject().put("cmd", "sessions.list").toString(), headers);
                    JSONObject jsonObject = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
                    if (jsonObject.getJSONArray("sessions").length() == 0) {
                        bd = BrowserClient.executePOSTRequest(new URL(LogRPC.INSTANCE.getConfig().getFlareSolverrURL()), new JSONObject().put("cmd", "sessions.create").put("session", LogRPC.INSTANCE.getConfig().getFlareSolverrSession()).toString(), headers);
                        bd.getResponse().close();
                    }

                    sessionCreated = true;
                }

                BrowserData bd = BrowserClient.executePOSTRequest(new URL(LogRPC.INSTANCE.getConfig().getFlareSolverrURL()), new JSONObject().put("cmd", "request.get").put("url", LogRPC.INSTANCE.getConfig().getWiimmfiPlayerURL() + (LogRPC.INSTANCE.getConfig().getWiimmfiPresenceType().equals("headlessjson") ? "/?m=json" : "")).put("maxTimeout", 30000).put("session", LogRPC.INSTANCE.getConfig().getFlareSolverrSession()).toString(), headers);
                JSONObject jsonObject = new JSONObject(BrowserClient.requestToString(bd.getResponse()));

                Thread.sleep(10000);

                Document document = Jsoup.parse(jsonObject.getJSONObject("solution").getString("response"));

                if (LogRPC.INSTANCE.getConfig().getWiimmfiPresenceType().equals("headlessjson")) {
                    bd = BrowserClient.executePOSTRequest(new URL(LogRPC.INSTANCE.getConfig().getFlareSolverrURL()), new JSONObject().put("cmd", "request.get").put("url", LogRPC.INSTANCE.getConfig().getWiimmfiPlayerURL()).put("maxTimeout", 30000).put("session", LogRPC.INSTANCE.getConfig().getFlareSolverrSession()).toString(), headers);
                    jsonObject = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
                    Document document1 = Jsoup.parse(jsonObject.getJSONObject("solution").getString("response"));

                    System.out.println(document.text());

                    if (!document1.text().contains("No room found!")) {
                        String roomType = "";
                        if (document1.text().contains("Worldwide room")) {
                            roomType = "Worldwide Match";
                        } else if (document1.text().contains("Private room")) {
                            roomType = "Private Match";
                        } else if (document1.text().contains("Continental room")) {
                            roomType = "Regional Match";
                        } else if (document1.text().contains("Global room")) {
                            roomType = "Finding Match";
                        }

                        JSONArray jsonArray = new JSONArray(document.text());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            if (j.getString("type").equals("room")) {
                                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.WIIMMFI, new WiimmfiData(j, roomType)));
                                break;
                            }
                        }
                    }
                } else {
                    if (!document.text().contains("No room found!")) {
                        LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.WIIMMFI, new BrowserHTMLData(LogRPC.INSTANCE.getConfig().getWiimmfiPlayerURL(), document)));
                    } else if (document.text().contains("No room found!")) {
                        LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    }
                }
            } catch (JSONException | InterruptedException | IOException ioException) {
                if (!ioException.getMessage().equals("Read timed out")) {
                    ioException.printStackTrace();
                }
            }
        }

        try {
            BrowserData bd = BrowserClient.executePOSTRequest(new URL(LogRPC.INSTANCE.getConfig().getFlareSolverrURL()), new JSONObject().put("cmd", "sessions.destroy").put("session", LogRPC.INSTANCE.getConfig().getFlareSolverrSession()).toString(), headers);
            bd.getResponse().close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
