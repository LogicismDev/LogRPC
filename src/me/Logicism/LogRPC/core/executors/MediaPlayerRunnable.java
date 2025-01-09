package me.Logicism.LogRPC.core.executors;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MediaPlayerRunnable implements Runnable {
    @Override
    public void run() {
        while (LogRPC.INSTANCE.getVLCMediaPlayerMenuItem().getState() || LogRPC.INSTANCE.getMPCHCMediaPlayerMenuItem().getState()) {
            try {
                String playbackState;
                String title;
                String start_time = "-1";
                String end_time = "-1";

                if (LogRPC.INSTANCE.getVLCMediaPlayerMenuItem().getState()) {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString((":" + LogRPC.INSTANCE.getConfig().getVlcWebInterfacePassword()).getBytes(StandardCharsets.UTF_8)));

                    BrowserData bd = BrowserClient.executeGETRequest(new URL("http://" + LogRPC.INSTANCE.getConfig().getVlcWebInterfaceHost() + ":" + LogRPC.INSTANCE.getConfig().getVlcWebInterfacePort() + "/requests/status.xml"), headers);
                    String xml = BrowserClient.requestToString(bd.getResponse());

                    Document document = Jsoup.parse(xml, "", Parser.xmlParser());
                    playbackState = document.getElementsByTag("state").text();

                    title = "VLC media player";
                    switch (playbackState) {
                        case "playing":
                        case "paused":
                            title = document.getElementsByTag("information").first().getElementsByAttributeValue("name", "title").size() != 0 ? document.getElementsByTag("information").first().getElementsByAttributeValue("name", "title").first().text() : document.getElementsByTag("information").first().getElementsByAttributeValue("name", "filename").first().text();
                            start_time = document.getElementsByTag("time").text();
                            end_time = document.getElementsByTag("length").text();
                            break;
                    }

                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.VLC_MEDIA_PLAYER, new JSONData(new JSONObject().put("title", title).put("details", playbackState).put("start_time", Integer.parseInt(start_time)).put("end_time", Integer.parseInt(end_time)))));
                } else if (LogRPC.INSTANCE.getMPCHCMediaPlayerMenuItem().getState()) {
                    Map<String, String> headers = new HashMap<>();

                    BrowserData bd = BrowserClient.executeGETRequest(new URL("http://" + LogRPC.INSTANCE.getConfig().getMpchcWebInterfaceHost() + ":" + LogRPC.INSTANCE.getConfig().getMpchcWebInterfacePort() + "/variables.html"), headers);
                    String html = BrowserClient.requestToString(bd.getResponse());

                    Document document = Jsoup.parse(html);
                    playbackState = document.getElementById("state").text();

                    title = "Media Player Classic";
                    switch (playbackState) {
                        case "1":
                        case "2":
                            title = document.getElementById("file").text();
                            start_time = document.getElementById("position").text();
                            end_time = document.getElementById("duration").text();
                    }

                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MPCHC_MEDIA_PLAYER, new JSONData(new JSONObject().put("title", title).put("details", playbackState).put("playbackState", Integer.parseInt(playbackState)).put("start_time", Integer.parseInt(start_time)).put("end_time", Integer.parseInt(end_time)))));
                }

                Thread.sleep(2000);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
