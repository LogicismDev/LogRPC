package me.Logicism.LogRPC.core.executors;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.NintendoSwitchData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NintendoSwitchRunnable implements Runnable {

    private String ZNCA_VERSION = "";
    private String ZNCA_API_VERSION = "3.0.2";
    
    private String NXAPI_TOKEN = "";
    private long NXAPI_TOKEN_TIMESTAMP;

    private String NA_ACCESS_TOKEN = "";
    private String NA_ID_TOKEN = "";

    private String WEBAPI_TOKEN = "";
    private long WEBAPI_TOKEN_TIMESTAMP = 0;
    private long WEBAPI_TOKEN_TIMESTAMP_EXP = 0;

    private String SPLATNET_WEBAPI_TOKEN = "";
    private long SPLATNET_WEBAPI_TOKEN_TIMESTAMP = 0;
    private long SPLATNET_WEBAPI_TOKEN_TIMESTAMP_EXP = 0;

    private String BULLET_TOKEN;
    private long BULLET_TOKEN_TIMESTAMP = 0;
    private long BULLET_TOKEN_TIMESTAMP_EXP = 0;

    private String lastStatus = "";
    private String lastGame = "";

    private String lastSplatoon3Status = "";
    private JSONObject splatnet3Friend;

    @Override
    public void run() {
        try {
            BrowserData bd = BrowserClient.executeGETRequest(new URL("https://www.apkmirror.com/apk/nintendo-co-ltd/nintendo-switch-online/"), new HashMap<>());
            Document document = Jsoup.parse(BrowserClient.requestToString(bd.getResponse()));
            ZNCA_VERSION = document.selectFirst("#primary > div.card-with-tabs.unroll > div.tab-content.unroll > div:nth-child(2) > div > h3").text().substring("About Nintendo Switch App ".length());

            System.out.println("ZNCA Version: " + ZNCA_VERSION);
            System.out.println("ZNCA API Version: " + ZNCA_API_VERSION);

            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "NASDKAPI; Android");
            String refreshToken;
            JSONObject meObj;
            if (LogRPC.INSTANCE.getNintendoRefreshToken() != null) {
                refreshToken = LogRPC.INSTANCE.getNintendoRefreshToken();
            } else {
                System.out.println("Grabbing Nintendo Account Session Token");
                String[] sessionData = LogRPC.INSTANCE.grabNintendoSessionToken().get();
                String sessionToken = sessionData[0];
                String sessionTokenVerifier = sessionData[1];

                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");

                bd = BrowserClient.executePOSTRequest(new URL("https://accounts.nintendo.com/connect/1.0.0/api/session_token"), "client_id=71b963c1b7b6d119&session_token_code=" + sessionToken + "&session_token_code_verifier=" + sessionTokenVerifier, headers);
                JSONObject sessionTokenObj = new JSONObject(BrowserClient.requestToString(bd.getResponse()));

                LogRPC.INSTANCE.setNintendoRefreshToken(sessionTokenObj.getString("session_token"));

                refreshToken = sessionTokenObj.getString("session_token");
            }

            System.out.println("Grabbing nxapi Token");
            String[] nxapiTokens = getNXAPIAccessToken(LogRPC.INSTANCE.getNXAPIRefreshToken());
            NXAPI_TOKEN = nxapiTokens[0];
            JSONObject nxapiObj = new JSONObject(new String(Base64.getDecoder().decode(NXAPI_TOKEN.split("\\.")[1])));
            NXAPI_TOKEN_TIMESTAMP = nxapiObj.getLong("exp");

            LogRPC.INSTANCE.setNXAPIRefreshToken(nxapiTokens[1]);

            if ((System.currentTimeMillis() / 1000L) > LogRPC.INSTANCE.getNintendoWebServiceTokenTimestampExp()) {
                System.out.println("Grabbing Nintendo Account Tokens");
                String[] naTokens = getNATokens(refreshToken);

                NA_ACCESS_TOKEN = naTokens[0];
                NA_ID_TOKEN = naTokens[1];

                headers.put("Authorization", "Bearer " + NA_ACCESS_TOKEN);

                bd = BrowserClient.executeGETRequest(new URL("https://api.accounts.nintendo.com/2.0.0/users/me"), headers);
                meObj = new JSONObject(BrowserClient.requestToString(bd.getResponse()));

                LogRPC.INSTANCE.setNintendoAccountID(meObj.getString("id"));
                LogRPC.INSTANCE.setNintendoAccountCountry(meObj.getString("country"));

                String[] fParameters = getFParameter(1, meObj.getString("id"), null, NA_ID_TOKEN, new JSONObject().put("url", "/v3/Account/Login").put("parameter", new JSONObject().put("naIdToken", NA_ID_TOKEN).put("naBirthday", meObj.getString("birthday")).put("naCountry", meObj.getString("country")).put("language", meObj.getString("language")).put("timestamp", 0).put("requestId", "").put("f", "")));
                if (fParameters[0] != null) {
                    System.out.println("Grabbing Web Service Token");

                    Object[] webApiResults = getWebAPITokens(Base64.getDecoder().decode(fParameters[1]));
                    WEBAPI_TOKEN = (String) webApiResults[0];
                    meObj = (JSONObject) webApiResults[1];
                    JSONObject webAPITokenObj = new JSONObject(new String(Base64.getDecoder().decode(WEBAPI_TOKEN.split("\\.")[1])));
                    WEBAPI_TOKEN_TIMESTAMP = webAPITokenObj.getLong("iat");
                    WEBAPI_TOKEN_TIMESTAMP_EXP = webAPITokenObj.getLong("exp");

                    LogRPC.INSTANCE.setNintendoWebServiceToken(WEBAPI_TOKEN);
                    LogRPC.INSTANCE.setNintendoWebServiceObject((JSONObject) webApiResults[1]);
                    LogRPC.INSTANCE.setNintendoWebServiceTokenTimestamp(WEBAPI_TOKEN_TIMESTAMP);
                    LogRPC.INSTANCE.setNintendoWebServiceTokenTimestampExp(WEBAPI_TOKEN_TIMESTAMP_EXP);
                } else {
                    JOptionPane.showMessageDialog(null, "We can't login at the moment! Please try again in a bit!", "LogRPC", JOptionPane.ERROR_MESSAGE);

                    return;
                }
            } else {
                System.out.println("Using cached Web Service Token");
                WEBAPI_TOKEN = LogRPC.INSTANCE.getNintendoWebServiceToken();
                WEBAPI_TOKEN_TIMESTAMP = LogRPC.INSTANCE.getNintendoWebServiceTokenTimestamp();
                WEBAPI_TOKEN_TIMESTAMP_EXP = LogRPC.INSTANCE.getNintendoWebServiceTokenTimestampExp();
                SPLATNET_WEBAPI_TOKEN = LogRPC.INSTANCE.getNintendoGameWebServiceToken();
                SPLATNET_WEBAPI_TOKEN_TIMESTAMP = LogRPC.INSTANCE.getNintendoGameWebServiceTokenTimestamp();
                SPLATNET_WEBAPI_TOKEN_TIMESTAMP_EXP = LogRPC.INSTANCE.getNintendoGameWebServiceTokenTimestampExp();
                BULLET_TOKEN = LogRPC.INSTANCE.getNintendoBulletToken();
                BULLET_TOKEN_TIMESTAMP = LogRPC.INSTANCE.getNintendoBulletTokenTimestamp();
                BULLET_TOKEN_TIMESTAMP_EXP = LogRPC.INSTANCE.getNintendoBulletTokenTimestampExp();

                meObj = LogRPC.INSTANCE.getNintendoWebServiceObject();
            }

            LogRPC.INSTANCE.saveCachedData(PresenceType.NINTENDO_SWITCH, LogRPC.INSTANCE.getDesmumeRPCFile());

            byte[] friendsEncrypted = encryptResponse("https://api-lp1.znc.srv.nintendo.net/v4/Friend/List", WEBAPI_TOKEN, new JSONObject().put("parameter", new JSONObject()).toString());
            JSONObject jsonObject = getZNCResult("/v4/Friend/List", friendsEncrypted);

            NSOFriend friend = null;
            if (LogRPC.INSTANCE.getConfig().getNintendoSwitchUsername().equals("none")) {
                NSOFriend[] friends = new NSOFriend[jsonObject.getJSONObject("result").getJSONArray("friends").length()];
                for (int i = 0; i < jsonObject.getJSONObject("result").getJSONArray("friends").length(); i++) {
                    friends[i] = new NSOFriend(jsonObject.getJSONObject("result").getJSONArray("friends").getJSONObject(i).getString("name"), jsonObject.getJSONObject("result").getJSONArray("friends").getJSONObject(i).getString("nsaId"));
                }

                friend = (NSOFriend) JOptionPane.showInputDialog(null, "Choose the User to display on your Discord", "LogRPC", JOptionPane.INFORMATION_MESSAGE, null, friends, friends[0]);
            } else {
                for (int i = 0; i < jsonObject.getJSONObject("result").getJSONArray("friends").length(); i++) {
                    if (jsonObject.getJSONObject("result").getJSONArray("friends").getJSONObject(i).getString("name").equals(LogRPC.INSTANCE.getConfig().getNintendoSwitchUsername())) {
                        friend = new NSOFriend(jsonObject.getJSONObject("result").getJSONArray("friends").getJSONObject(i).getString("name"), jsonObject.getJSONObject("result").getJSONArray("friends").getJSONObject(i).getString("nsaId"));
                        break;
                    }
                }
            }
            if (friend != null) {
                LogRPC.INSTANCE.getNintendoSwitchMenuItem().setName("Nintendo Switch (Auto) - " + friend.getName());

                while (LogRPC.INSTANCE.getNintendoSwitchMenuItem().getState()) {
                    if ((System.currentTimeMillis()) / 1000L > NXAPI_TOKEN_TIMESTAMP) {
                        nxapiTokens = getNXAPIAccessToken(LogRPC.INSTANCE.getNXAPIRefreshToken());
                        NXAPI_TOKEN = nxapiTokens[0];

                        LogRPC.INSTANCE.setNXAPIRefreshToken(nxapiTokens[1]);
                        LogRPC.INSTANCE.saveCachedData(PresenceType.NINTENDO_SWITCH, LogRPC.INSTANCE.getDesmumeRPCFile());
                    }
                    if ((System.currentTimeMillis() / 1000L) > WEBAPI_TOKEN_TIMESTAMP_EXP) {
                        String[] naTokens = getNATokens(refreshToken);

                        String[] fParameters = getFParameter(1, LogRPC.INSTANCE.getNintendoAccountID(), null, NA_ID_TOKEN, new JSONObject().put("url", "/v3/Account/Login").put("parameter", new JSONObject().put("naIdToken", NA_ID_TOKEN).put("naBirthday", meObj.getString("birthday")).put("naCountry", meObj.getString("country")).put("language", meObj.getString("language")).put("timestamp", 0).put("requestId", "").put("f", "")));

                        NA_ACCESS_TOKEN = naTokens[0];
                        NA_ID_TOKEN = naTokens[1];

                        Object[] webApiResults = getWebAPITokens(Base64.getDecoder().decode(fParameters[1]));
                        WEBAPI_TOKEN = (String) webApiResults[0];
                        JSONObject webAPITokenObj = new JSONObject(new String(Base64.getDecoder().decode(WEBAPI_TOKEN.split("\\.")[1])));
                        WEBAPI_TOKEN_TIMESTAMP = webAPITokenObj.getLong("iat");
                        WEBAPI_TOKEN_TIMESTAMP_EXP = webAPITokenObj.getLong("exp");

                        LogRPC.INSTANCE.setNintendoWebServiceToken(WEBAPI_TOKEN);
                        LogRPC.INSTANCE.setNintendoWebServiceObject((JSONObject) webApiResults[1]);
                        LogRPC.INSTANCE.setNintendoWebServiceTokenTimestamp(WEBAPI_TOKEN_TIMESTAMP);
                        LogRPC.INSTANCE.setNintendoWebServiceTokenTimestampExp(WEBAPI_TOKEN_TIMESTAMP_EXP);

                        LogRPC.INSTANCE.saveCachedData(PresenceType.NINTENDO_SWITCH, LogRPC.INSTANCE.getDesmumeRPCFile());
                    }

                    byte[] friendEncrypted = encryptResponse("https://api-lp1.znc.srv.nintendo.net/v4/Friend/Show", WEBAPI_TOKEN, new JSONObject().put("parameter", new JSONObject().put("nsaId", friend.getNSAID())).toString());
                    JSONObject jsonObject1 = getZNCResult("/v4/Friend/Show", friendEncrypted);
                    if (jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").has("name") && jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("name").equals("Splatoon 3")) {
                        if (LogRPC.INSTANCE.getConfig().isEnableShowingSplatoon3Presence()) {
                            if ((System.currentTimeMillis() / 1000L) > SPLATNET_WEBAPI_TOKEN_TIMESTAMP_EXP) {
                                String[] fParameters = getFParameter(2, LogRPC.INSTANCE.getNintendoAccountID(), String.valueOf(meObj.getLong("id")), WEBAPI_TOKEN, new JSONObject().put("url", "https://api-lp1.znc.srv.nintendo.net/v4/Game/GetWebServiceToken").put("parameter", new JSONObject().put("id", Long.valueOf("4834290508791808")).put("registrationToken", "").put("timestamp", 0L).put("requestId", "").put("f", "")));
                                SPLATNET_WEBAPI_TOKEN = getGameWebAPIToken(Base64.getDecoder().decode(fParameters[1]));
                                JSONObject splatnetWebAPIObj = new JSONObject(new String(Base64.getDecoder().decode(SPLATNET_WEBAPI_TOKEN.split("\\.")[1])));
                                SPLATNET_WEBAPI_TOKEN_TIMESTAMP = splatnetWebAPIObj.getLong("iat");
                                SPLATNET_WEBAPI_TOKEN_TIMESTAMP_EXP = splatnetWebAPIObj.getLong("exp");
                                LogRPC.INSTANCE.setNintendoGameWebServiceToken(SPLATNET_WEBAPI_TOKEN);
                                LogRPC.INSTANCE.setNintendoGameWebServiceTokenTimestamp(SPLATNET_WEBAPI_TOKEN_TIMESTAMP);
                                LogRPC.INSTANCE.setNintendoGameWebServiceTokenTimestampExp(SPLATNET_WEBAPI_TOKEN_TIMESTAMP_EXP);
                                LogRPC.INSTANCE.saveCachedData(PresenceType.NINTENDO_SWITCH, LogRPC.INSTANCE.getDesmumeRPCFile());
                            }
                            if ((System.currentTimeMillis() / 1000L) > BULLET_TOKEN_TIMESTAMP_EXP) {
                                BULLET_TOKEN = getBulletToken().getString("bulletToken");
                                JSONObject bulletTokenObj = new JSONObject(new String(Base64.getDecoder().decode(Base64.getDecoder().decode(BULLET_TOKEN.split("\\.")[1]))));
                                BULLET_TOKEN_TIMESTAMP = bulletTokenObj.getLong("iat");
                                BULLET_TOKEN_TIMESTAMP_EXP = bulletTokenObj.getLong("exp");
                                LogRPC.INSTANCE.setNintendoBulletToken(BULLET_TOKEN);
                                LogRPC.INSTANCE.setNintendoBulletTokenTimestamp(BULLET_TOKEN_TIMESTAMP);
                                LogRPC.INSTANCE.setNintendoBulletTokenTimestampExp(BULLET_TOKEN_TIMESTAMP_EXP);
                                LogRPC.INSTANCE.saveCachedData(PresenceType.NINTENDO_SWITCH, LogRPC.INSTANCE.getDesmumeRPCFile());
                            }

                            JSONObject splatnet3FriendObj;
                            if (splatnet3Friend != null) {
                                splatnet3FriendObj = getSplatNet3Result("/graphql", new JSONObject().put("variables", "").put("extensions", new JSONObject().put("persistedQuery", new JSONObject().put("version", 1).put("sha256Hash", "411b3fa70a9e0ff083d004b06cc6fad2638a1a24326cbd1fb111e7c72a529931"))).toString());
                            } else {
                                splatnet3FriendObj = getSplatNet3Result("/graphql", new JSONObject().put("variables", "").put("extensions", new JSONObject().put("persistedQuery", new JSONObject().put("version", 1).put("sha256Hash", "ea1297e9bb8e52404f52d89ac821e1d73b726ceef2fd9cc8d6b38ab253428fb3"))).toString());
                            }
                            for (int i = 0; i < splatnet3FriendObj.getJSONObject("data").getJSONObject("friends").getJSONArray("node").length(); i++) {
                                if (friend.getName().equals(splatnet3FriendObj.getJSONObject("data").getJSONObject("friends").getJSONArray("node").getJSONObject(i).getString("nickname"))) {
                                    splatnet3Friend = splatnet3FriendObj.getJSONObject("data").getJSONObject("friends").getJSONArray("node").getJSONObject(i);
                                    break;
                                }
                            }

                            if (!lastSplatoon3Status.equals(splatnet3Friend.getString("onlineStatus"))) {
                                lastSplatoon3Status = splatnet3Friend.getString("onlineStatus");
                                if (splatnet3Friend != null) {
                                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.NINTENDO_SWITCH, new NintendoSwitchData("Splatoon 3", jsonObject1.getJSONObject("result").getJSONObject("presence").getInt("platform"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("totalPlayTime"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("firstPlayedAt"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("imageUri"), jsonObject1.getJSONObject("result").getString("imageUri"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("shopUri"), splatnet3Friend)));
                                } else {
                                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.NINTENDO_SWITCH, new NintendoSwitchData(jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("name"), jsonObject1.getJSONObject("result").getJSONObject("presence").getInt("platform"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("totalPlayTime"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("firstPlayedAt"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("imageUri"), jsonObject1.getJSONObject("result").getString("imageUri"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("shopUri"), jsonObject1)));
                                }
                            }
                        } else {
                            lastGame = jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("name");
                            if ((jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state").equals("ONLINE") || jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state").equals("PLAYING")) && !lastStatus.equals(jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state"))) {
                                lastStatus = jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state");
                            }

                            LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.NINTENDO_SWITCH, new NintendoSwitchData(jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("name"), jsonObject1.getJSONObject("result").getJSONObject("presence").getInt("platform"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("totalPlayTime"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("firstPlayedAt"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("imageUri"), jsonObject1.getJSONObject("result").getString("imageUri"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("shopUri"), jsonObject1)));
                        }
                    } else {
                        if (jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").has("name") && !lastGame.equals(jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("name"))) {
                            lastGame = jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("name");
                            if ((jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state").equals("ONLINE") || jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state").equals("PLAYING")) && !lastStatus.equals(jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state"))) {
                                lastStatus = jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state");
                            }

                            LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.NINTENDO_SWITCH, new NintendoSwitchData(jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("name"), jsonObject1.getJSONObject("result").getJSONObject("presence").getInt("platform"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("totalPlayTime"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getLong("firstPlayedAt"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("imageUri"), jsonObject1.getJSONObject("result").getString("imageUri"), jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").getString("shopUri"), jsonObject1)));
                        } else if (!jsonObject1.getJSONObject("result").getJSONObject("presence").getJSONObject("game").has("name")) {
                            if (!lastGame.isEmpty()) {
                                lastGame = "";
                            }
                            if (!lastStatus.equals(jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state"))) {
                                lastStatus = jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state");
                                if (jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state").equals("OFFLINE")) {
                                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                                } else if (jsonObject1.getJSONObject("result").getJSONObject("presence").getString("state").equals("INACTIVE")) {
                                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.NINTENDO_SWITCH, new NintendoSwitchData("Not Playing", 1, -1, -1, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Nintendo_switch_logo.png/500px-Nintendo_switch_logo.png", jsonObject1.getJSONObject("result").getString("imageUri"), null, jsonObject1)));
                                }
                            }
                        }
                    }

                    Thread.sleep(30000);
                }
            }
        } catch (InterruptedException | ExecutionException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private byte[] encryptResponse(String url, String token, String data) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + NXAPI_TOKEN);
        headers.put("Accept", "application/octet-stream");
        headers.put("Content-Type", "application/json");
        headers.put("X-znca-Version", ZNCA_VERSION);
        headers.put("X-znca-Client-Version", ZNCA_VERSION);

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://nxapi-znca-api.fancy.org.uk/api/znca/encrypt-request"), new JSONObject().put("url", url).put("token", token).put("data", data).toString(), headers);

        return BrowserClient.requestToBytes(bd.getResponse());
    }

    private String decryptResponse(byte[] bytes, boolean requestAssertion) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + NXAPI_TOKEN);
        headers.put("Accept", requestAssertion ? "application/json" : "text/plain");
        headers.put("Content-Type", "application/json");
        headers.put("X-znca-Version", ZNCA_VERSION);
        headers.put("X-znca-Client-Version", ZNCA_VERSION);

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://nxapi-znca-api.fancy.org.uk/api/znca/decrypt-response"), new JSONObject().put("data", Base64.getUrlEncoder().encodeToString(bytes)).toString(), headers);

        return BrowserClient.requestToString(bd.getResponse());
    }

    private String[] getNXAPIAccessToken(String refreshToken) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.replace("Content-Type", "applications/x-www-form-urlencoded");
        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://nxapi-auth.fancy.org.uk/api/oauth/token"), (refreshToken != null ? "grant_type=refresh_token&client_id=awgLgMaC_np6PS0KMVGSUg&refresh_token=" + refreshToken : "grant_type=client_credentials&client_id=awgLgMaC_np6PS0KMVGSUg&scope=ca:gf+ca:er+ca:dr"), headers);

        JSONObject obj = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
        if (obj.has("error_description") && obj.getString("error_description").equals("Refresh token expired")) {
            bd = BrowserClient.executePOSTRequest(new URL("https://nxapi-auth.fancy.org.uk/api/oauth/token"), "grant_type=client_credentials&client_id=awgLgMaC_np6PS0KMVGSUg&scope=ca:gf+ca:er+ca:dr", headers);
            obj = new JSONObject(BrowserClient.requestToString(bd.getResponse()));
        }
        return new String[]{obj.getString("access_token"), obj.getString("refresh_token")};
    }

    private String[] getNATokens(String refreshToken) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.replace("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 8.0.0)");

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://accounts.nintendo.com/connect/1.0.0/api/token"), new JSONObject().put("client_id", "71b963c1b7b6d119").put("session_token", refreshToken).put("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer-session-token").toString(), headers);
        JSONObject tokenObj = new JSONObject(BrowserClient.requestToString(bd.getResponse()));

        return new String[]{tokenObj.getString("access_token"), tokenObj.getString("id_token")};
    }

    private String[] getFParameter(int hash_method, String naID, String coralID, String token, JSONObject encryptTokenRequest) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + NXAPI_TOKEN);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("X-znca-Platform", "Android");
        headers.put("X-znca-Version", ZNCA_VERSION);
        headers.put("X-znca-Client-Version", ZNCA_API_VERSION);

        JSONObject obj = new JSONObject().put("hash_method", hash_method).put("token", token).put("na_id", naID).put("encrypt_token_request", encryptTokenRequest);
        if (hash_method == 1) {
            obj.put("url", "https://nxapi-znca-api.fancy.org.uk/api/znca/f").put("znca_platform", "Android").put("znca_version", ZNCA_API_VERSION);
        }
        if (coralID != null) {
            obj.put("coral_user_id", coralID);
        }

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://nxapi-znca-api.fancy.org.uk/api/znca/f"), obj.toString(), headers);
        String s = BrowserClient.requestToString(bd.getResponse());
        JSONObject fObj = new JSONObject(s);
        System.out.println(fObj);
        if (fObj.has("error_message")) {
            return new String[]{null, null};
        } else {
            return new String[]{fObj.getString("f"), fObj.getString("encrypted_token_request")};
        }
    }

    private Object[] getWebAPITokens(byte[] body) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/octet-stream, application/json");
        headers.put("Content-Type", "application/octet-stream");
        headers.put("X-Platform", "Android");
        headers.put("X-ProductVersion", ZNCA_VERSION);
        headers.put("User-Agent", "com.nintendo.znca/" + ZNCA_VERSION + "(Android/12)");

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://api-lp1.znc.srv.nintendo.net/v3/Account/Login"), body, headers);
        byte[] resultB = BrowserClient.requestToBytes(bd.getResponse());
        String result = decryptResponse(resultB, false);
        JSONObject webAPIObj = new JSONObject(result);
        return new Object[]{webAPIObj.getJSONObject("result").getJSONObject("webApiServerCredential").getString("accessToken"), webAPIObj.getJSONObject("result").getJSONObject("user")};
    }

    private String getGameWebAPIToken(byte[] body) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + WEBAPI_TOKEN);
        headers.put("Accept", "application/octet-stream,application/json");
        headers.put("Content-Type", "application/octet-stream");
        headers.put("X-Platform", "Android");
        headers.put("X-ProductVersion", ZNCA_VERSION);
        headers.put("User-Agent", "com.nintendo.znca/" + ZNCA_VERSION + "(Android/12)");

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://api-lp1.znc.srv.nintendo.net/v4/Game/GetWebServiceToken"), body, headers);
        byte[] resultB = BrowserClient.requestToBytes(bd.getResponse());
        String result = decryptResponse(resultB, true);
        JSONObject webAPIObj = new JSONObject(new JSONObject(result).getString("data"));
        return webAPIObj.getJSONObject("result").getString("accessToken");
    }

    private JSONObject getZNCResult(String endpoint, byte[] bytes) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/octet-stream,application/json");
        headers.put("Accept-Language", "en-US");
        headers.put("Content-Type", "application/octet-stream");
        headers.put("User-Agent", "com.nintendo.znca/" + ZNCA_VERSION + "(Android/12)");
        headers.put("Authorization", "Bearer " + WEBAPI_TOKEN);
        headers.put("X-Platform", "Android");

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://api-lp1.znc.srv.nintendo.net" + endpoint), bytes, headers);
        return new JSONObject(decryptResponse(BrowserClient.requestToBytes(bd.getResponse()), false));
    }

    private JSONObject getBulletToken() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", "en-US");
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36");
        headers.put("X-GameWebToken", SPLATNET_WEBAPI_TOKEN);
        headers.put("Referrer", "https://api.lp1.av5ja.srv.nintendo.net/");
        headers.put("X-NACOUNTRY", LogRPC.INSTANCE.getNintendoAccountCountry());
        headers.put("X-Web-View-Ver", "6.0.0");
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        headers.put("X-Requested-With", "XMLHttpRequest");

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://api.lp1.av5ja.srv.nintendo.net/api/bullet_tokens"), "", headers);
        return new JSONObject(BrowserClient.requestToString(bd.getResponse()));
    }

    private JSONObject getSplatNet3Result(String endpoint, String body) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", "en-US");
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36");
        headers.put("Authorization", "Bearer " + BULLET_TOKEN);
        headers.put("Referrer", "https://api.lp1.av5ja.srv.nintendo.net/");
        headers.put("X-Web-View-Ver", "6.0.0");
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        headers.put("X-Requested-With", "XMLHttpRequest");

        BrowserData bd = BrowserClient.executePOSTRequest(new URL("https://api.lp1.av5ja.srv.nintendo.net/api" + endpoint), body, headers);
        return new JSONObject(BrowserClient.requestToString(bd.getResponse()));
    }
}

class NSOFriend {

    private String name;
    private String nsaId;

    public NSOFriend(String name, String nsaId) {
        this.name = name;
        this.nsaId = nsaId;
    }

    public String getName() {
        return name;
    }

    public String getNSAID() {
        return nsaId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}