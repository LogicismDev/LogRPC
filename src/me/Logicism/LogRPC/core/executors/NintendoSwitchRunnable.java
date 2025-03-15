package me.Logicism.LogRPC.core.executors;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NintendoSwitchRunnable implements Runnable {
    @Override
    public void run() {
        try {
            BrowserData bd = BrowserClient.executeGETRequest(new URL("https://itunes.apple.com/search?term=Nintendo+Switch+Online&country=us&media=software&limit=1"), new HashMap<>());
            JSONObject itunesObject = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
            String version = itunesObject.getJSONArray("results").getJSONObject(0).getString("version");

            Map<String, String> headers = new HashMap<>();
            headers.put("Accept-Encoding", "gzip");
            headers.put("User-Agent", "OnlineLounge/" + version + " NASDKAPI Android");

            String refreshToken;

            if (LogRPC.INSTANCE.getNintendoRefreshToken() != null) {
                refreshToken = LogRPC.INSTANCE.getNintendoRefreshToken();
            } else {
                String[] sessionData = LogRPC.INSTANCE.grabNintendoSessionToken().get();
                String sessionToken = sessionData[0];
                String sessionTokenVerifier = sessionData[1];

                System.out.println(sessionToken);
                System.out.println(sessionTokenVerifier);

                headers.put("Accept-Language", "en-US");
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Host", "accounts.nintendo.com");
                headers.put("Connection", "Keep-Alive");
                headers.put("X-Platform", "Android");
                headers.put("X-ProductVersion", version);

                bd = BrowserClient.executePOSTRequest(new URL("https://accounts.nintendo.com/connect/1.0.0/api/session_token"), "client_id=71b963c1b7b6d119&session_token_code=" + sessionToken + "&session_token_code_verifier=" + sessionTokenVerifier.replace("=", ""), headers);
                System.out.println(BrowserClient.requestToString(bd.getResponse()));
            }
        } catch (InterruptedException | ExecutionException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
