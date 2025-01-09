package me.Logicism.LogRPC.core.executors;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;
import me.Logicism.LogRPC.presence.music.TIDALMusicPresence;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.internal.StringUtil;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class MusicRunnable implements Runnable {

    private int cachedPlaybackStatus = 0;

    private String cachedAlbumName = "";
    private String cachedAlbumArtistName = "";
    private String cachedSongURL = "";
    private String cachedArtworkURL = "";

    @Override
    public void run() {
        while (LogRPC.INSTANCE.getMusicMenuItem().getState()) {
            String[] command = null;
            if (System.getProperty("os.name").startsWith("Windows")) {
                command = new String[]{"\"" + new File("LogRPC Music Scripts/Windows Script/python/python.exe").getAbsolutePath() + "\" \"" + new File("LogRPC Music Scripts/Windows Script/music.py").getAbsolutePath() + "\" \"" + LogRPC.INSTANCE.getConfig().getMusicProgram() + "\""};
            } else if (System.getProperty("os.name").startsWith("Mac OS X")) {
                command = new String[]{"/usr/local/bin/deno", "run", "--allow-env", "--allow-run", "--allow-net", "--allow-read", "--allow-write", new File("LogRPC Music Scripts/macOS Script/music.ts").getAbsolutePath()};
            }
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            try {

                Process musicProcess;
                try {
                    musicProcess = processBuilder.start();
                } catch (IOException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(sw);
                    e.printStackTrace(printWriter);
                    JOptionPane.showMessageDialog(null, System.getProperty("os.name").startsWith("Windows") ? "We are missing the embedded python and the script! Please download the LogRPC Music Scripts folder manually from the website or the mirror link!" : "Deno is not installed! Please install homebrew from brew.sh on your system and install Deno with the command \"brew install deno\"!", "LogRPC", JOptionPane.ERROR_MESSAGE);

                    LogRPC.INSTANCE.setMusicExecutor(null);
                    LogRPC.INSTANCE.getMusicMenuItem().setState(false);
                    LogRPC.INSTANCE.getManualMenuItem().setState(true);

                    LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(true);
                    LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(true);
                    LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(true);
                    LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(true);
                    LogRPC.INSTANCE.getPCGamesMenu().setEnabled(true);

                    return;
                }

                musicProcess.waitFor();
                Scanner scanner = new Scanner(musicProcess.getInputStream());

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    JSONObject jsonObject = new JSONObject(line);

                    if (System.getProperty("os.name").startsWith("Windows")) {
                        if (jsonObject.getInt("playback_status") == 4) {
                            String album_artwork_url = "";
                            String song_url = "";
                            JSONObject result = null;
                            if (!jsonObject.getString("album").isEmpty() && !jsonObject.getString("album_artist").isEmpty()) {
                                result = getTIDALAlbumInfo(jsonObject.getString("album"), jsonObject.getString("album_artist"));
                                if (result == null) {
                                    result = getiTunesAlbumInfo(jsonObject.getString("album"), jsonObject.getString("album_artist"));
                                }
                            } else if (!jsonObject.getString("title").isEmpty() && !jsonObject.getString("artist").isEmpty()) {
                                result = getTIDALTrackInfo((jsonObject.getString("app_name").equals("Amazon Music.exe") && (jsonObject.getString("title").endsWith(" [Explicit]") || jsonObject.getString("title").endsWith(" [Clean]")) ? jsonObject.getString("title").substring(0, jsonObject.getString("title").length() - (jsonObject.getString("title").endsWith(" [Explicit]") ? " [Explicit]".length() : " [Clean]".length())) : jsonObject.getString("title")), jsonObject.getString("artist"));
                                if (result == null) {
                                    result = getiTunesTrackInfo((jsonObject.getString("app_name").equals("Amazon Music.exe") && (jsonObject.getString("title").endsWith(" [Explicit]") || jsonObject.getString("title").endsWith(" [Clean]")) ? jsonObject.getString("title").substring(0, jsonObject.getString("title").length() - (jsonObject.getString("title").endsWith(" [Explicit]") ? " [Explicit]".length() : " [Clean]".length())) : jsonObject.getString("title")), jsonObject.getString("artist"));
                                }
                            }

                            if (result != null) {
                                if (jsonObject.getString("album").isEmpty() || jsonObject.getString("album_artist").isEmpty()) {
                                    JSONObject result1;
                                    if (result.has("id")) {
                                        result1 = getTIDALAlbumInfo(result.getJSONObject("album").getLong("id"));

                                        if (result.getJSONArray("artists").length() > 1) {
                                            List<String> artistNames = new ArrayList<>();
                                            for (int i = 0; i < result.getJSONArray("artists").length(); i++) {
                                                artistNames.add(result.getJSONArray("artists").getJSONObject(i).getString("name"));
                                            }

                                            jsonObject.remove("artist");
                                            jsonObject.put("artist", StringUtil.join(artistNames, ", "));
                                        }

                                        if (result1 != null) {
                                            if (jsonObject.getString("album").isEmpty()) {
                                                jsonObject.remove("album");
                                                jsonObject.put("album", result.getJSONObject("album").getString("title"));
                                            }
                                            if (jsonObject.getString("album_artist").isEmpty()) {
                                                jsonObject.remove("album_artist");

                                                List<String> artistNames = new ArrayList<>();
                                                for (int i = 0; i < result1.getJSONArray("artists").length(); i++) {
                                                    artistNames.add(result1.getJSONArray("artists").getJSONObject(i).getString("name"));
                                                }

                                                jsonObject.put("album_artist", StringUtil.join(artistNames, ", "));
                                            }
                                        }
                                    } else {
                                        result1 = getiTunesAlbumInfo(result.getLong("trackId"));

                                        if (result1 != null) {
                                            if (jsonObject.getString("album").isEmpty()) {
                                                jsonObject.remove("album");
                                                jsonObject.put("album", result1.getString("collectionName"));
                                            }
                                            if (jsonObject.getString("album_artist").isEmpty()) {
                                                jsonObject.remove("album_artist");
                                                jsonObject.put("album_artist", result1.getString("artistName"));
                                            }
                                        }
                                    }
                                }

                                if (!cachedAlbumArtistName.equals(jsonObject.getString("album_artist")) || !cachedAlbumName.equals(jsonObject.getString("album"))) {
                                    if (result.has("id") && !jsonObject.getString("app_name").equals("iTunes.exe")) {
                                        album_artwork_url = "https://resources.tidal.com/images/" + result.getJSONObject("album").getString("cover").replace("-", "/") + "/1280x1280.jpg";

                                        JSONObject songDotLinkResponse = getSongDotLinkInfo(result.getString("url"));

                                        if (jsonObject.getString("app_name").equals("Amazon Music.exe")) {
                                            song_url = songDotLinkResponse.getJSONObject("linksByPlatform").getJSONObject("amazonMusic").getString("url");
                                        } else if (jsonObject.getString("app_name").equals("Deezer.exe")) {
                                            song_url = songDotLinkResponse.getJSONObject("linksByPlatform").getJSONObject("deezer").getString("url");
                                        } else if (jsonObject.getString("app_name").equals("TIDAL.exe")) {
                                            song_url = songDotLinkResponse.getJSONObject("linksByPlatform").getJSONObject("tidal").getString("url");
                                        }
                                    } else {
                                        album_artwork_url = result.getString("artworkUrl100");

                                        JSONObject songDotLinkResponse = getSongDotLinkInfo(result.has("collectionViewUrl") ? result.getString("collectionViewUrl") : result.getString("trackViewUrl"));

                                        if (jsonObject.getString("app_name").equals("iTunes.exe")) {
                                            song_url = result.has("collectionViewUrl") ? result.getString("collectionViewUrl") : result.getString("trackViewUrl");
                                        } else {
                                            if (jsonObject.getString("app_name").equals("Amazon Music.exe")) {
                                                song_url = songDotLinkResponse.getJSONObject("linksByPlatform").getJSONObject("amazonMusic").getString("url");
                                            } else if (jsonObject.getString("app_name").equals("Deezer.exe")) {
                                                song_url = songDotLinkResponse.getJSONObject("linksByPlatform").getJSONObject("deezer").getString("url");
                                            } else if (jsonObject.getString("app_name").equals("TIDAL.exe")) {
                                                song_url = songDotLinkResponse.getJSONObject("linksByPlatform").getJSONObject("tidal").getString("url");
                                            }
                                        }
                                    }

                                    cachedAlbumName = jsonObject.getString("album");
                                    cachedAlbumArtistName = jsonObject.getString("album_artist");
                                    cachedArtworkURL = album_artwork_url;
                                    cachedSongURL = song_url;
                                } else {
                                    album_artwork_url = cachedArtworkURL;
                                    song_url = cachedSongURL;
                                }
                            }

                            try {
                                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MUSIC, new JSONData(new JSONObject().put("title", jsonObject.getString("title")).put("details", jsonObject.getString("artist")).put("album", jsonObject.getString("album")).put("album_artist", jsonObject.getString("album_artist")).put("app_name", jsonObject.getString("app_name")).put("album_artwork_url", album_artwork_url).put("song_url", song_url).put("start_time", jsonObject.getInt("start_time")).put("end_time", jsonObject.getInt("end_time")).put("position", jsonObject.getInt("position")))));
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        } else {
                            try {
                                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                    } else if (System.getProperty("os.name").startsWith("Mac OS X")) {
                        if (jsonObject.getString("playerState").equals("playing")) {
                            String album_artwork_url = "";
                            String song_url = "";
                            JSONObject result = null;
                            if (!jsonObject.getString("album").isEmpty() && !jsonObject.getString("albumArtist").isEmpty()) {
                                result = getiTunesAlbumInfo(jsonObject.getString("album"), jsonObject.getString("albumArtist"));
                            } else if (!jsonObject.getString("name").isEmpty() && !jsonObject.getString("artist").isEmpty()) {
                                result = getiTunesTrackInfo(jsonObject.getString("name"), jsonObject.getString("artist"));
                            }

                            if (result != null) {
                                if (jsonObject.getString("album").isEmpty() || jsonObject.getString("albumArtist").isEmpty()) {
                                    JSONObject result1;
                                    result1 = getiTunesAlbumInfo(result.getLong("trackId"));

                                    if (result1 != null) {
                                        if (jsonObject.getString("album").isEmpty()) {
                                            jsonObject.remove("album");
                                            jsonObject.put("album", result1.getString("collectionName"));
                                        }
                                        if (jsonObject.getString("album_artist").isEmpty()) {
                                            jsonObject.remove("album_artist");
                                            jsonObject.put("album_artist", result1.getString("artistName"));
                                        }
                                    }
                                }

                                if (!cachedAlbumArtistName.equals(jsonObject.getString("album_artist")) || !cachedAlbumName.equals(jsonObject.getString("album"))) {
                                    album_artwork_url = result.getString("artworkUrl100");
                                    song_url = result.has("collectionViewUrl") ? result.getString("collectionViewUrl") : result.getString("trackViewUrl");

                                    cachedAlbumName = jsonObject.getString("album");
                                    cachedAlbumArtistName = jsonObject.getString("album_artist");
                                    cachedArtworkURL = album_artwork_url;
                                    cachedSongURL = song_url;
                                } else {
                                    album_artwork_url = cachedArtworkURL;
                                    song_url = cachedSongURL;
                                }
                            }

                            System.out.println(jsonObject);

                            try {
                                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MUSIC, new JSONData(new JSONObject().put("title", jsonObject.getString("name")).put("details", jsonObject.getString("artist")).put("album", jsonObject.getString("album")).put("album_artist", jsonObject.getString("albumArtist")).put("app_name", jsonObject.getString("playerName").equals("Music") ? "Apple Music" : "iTunes").put("album_artwork_url", album_artwork_url).put("song_url", song_url).put("start_time", 0).put("end_time", ((int) jsonObject.getDouble("duration"))).put("position", ((int) jsonObject.getDouble("playerPosition"))))));
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        } else if (jsonObject.getString("playerState").equals("stopped")) {
                            try {
                                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                    }

                    cachedPlaybackStatus = jsonObject.getInt("playback_status");
                }

                scanner.close();

                Thread.sleep(2000);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private JSONObject getTIDALAlbumInfo(String title, String artist) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        headers.put("x-tidal-token", "CzET4vdadNUFQ5JU");
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://api.tidal.com/v1/search/albums?query=" + URLEncoder.encode(artist + " - " + title, "UTF-8") + "&countryCode=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry().toUpperCase() + "&limit=20"), headers);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject TIDALResponse = new JSONObject(response);

        if (TIDALResponse.getInt("totalNumberOfItems") == 0) {
            return null;
        } else {
            for (int i = 0; i < TIDALResponse.getInt("limit"); i++) {
                if (TIDALResponse.getJSONArray("items").getJSONObject(i).getJSONObject("artist").getString("name").equals(artist) && TIDALResponse.getJSONArray("items").getJSONObject(i).getString("title").equals(title)) {
                    return TIDALResponse.getJSONArray("items").getJSONObject(i);
                }
            }
        }

        return TIDALResponse.getJSONArray("items").getJSONObject(0);
    }

    private JSONObject getTIDALTrackInfo(String title, String artist) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        headers.put("x-tidal-token", "CzET4vdadNUFQ5JU");
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://api.tidal.com/v1/search/tracks?query=" + URLEncoder.encode(artist + " - " + title, "UTF-8").replace("+", "%20") + "&countryCode=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry().toUpperCase() + "&limit=20"), headers);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject TIDALResponse = new JSONObject(response);

        if (TIDALResponse.getInt("totalNumberOfItems") == 0) {
            return null;
        } else {
            for (int i = 0; i < TIDALResponse.getInt("limit"); i++) {
                if (TIDALResponse.getJSONArray("items").getJSONObject(i).getJSONObject("artist").getString("name").equals(artist) && TIDALResponse.getJSONArray("items").getJSONObject(i).getString("title").equals(title)) {
                    return TIDALResponse.getJSONArray("items").getJSONObject(i);
                }
            }
        }

        return TIDALResponse.getJSONArray("items").getJSONObject(0);
    }

    private JSONObject getTIDALAlbumInfo(long identifier) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        headers.put("x-tidal-token", "CzET4vdadNUFQ5JU");
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://api.tidal.com/v1/albums/" + identifier + "?countryCode=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry().toUpperCase() + "&limit=20"), headers);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject TIDALResponse = new JSONObject(response);

        if (data.getResponseCode() != 200) {
            return null;
        }

        return TIDALResponse;
    }

    private JSONObject getTIDALTrackInfo(long identifier) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        headers.put("x-tidal-token", "CzET4vdadNUFQ5JU");
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://api.tidal.com/v1/tracks/" + identifier + "?countryCode=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry().toUpperCase() + "&limit=20"), headers);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject TIDALResponse = new JSONObject(response);

        if (data.getResponseCode() != 200) {
            return null;
        }

        return TIDALResponse;
    }

    private JSONObject getiTunesAlbumInfo(String title, String artist) throws IOException {
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://itunes.apple.com/search?term=" + URLEncoder.encode(artist + " - " + title, "UTF-8") + "&country=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry() + "&entity=album&limit=20"), null);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject iTunesResponse = new JSONObject(response);

        if (iTunesResponse.getInt("resultCount") == 0) {
            return null;
        } else {
            for (int i = 0; i < 20; i++) {
                if (iTunesResponse.getJSONArray("results").getJSONObject(i).getString("artistName").equals(artist) && iTunesResponse.getJSONArray("results").getJSONObject(i).getString("collectionName").equals(title)) {
                    return iTunesResponse.getJSONArray("results").getJSONObject(i);
                }
            }
        }

        return iTunesResponse.getJSONArray("results").getJSONObject(0);
    }

    private JSONObject getiTunesTrackInfo(String title, String artist) throws IOException {
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://itunes.apple.com/search?term=" + URLEncoder.encode(artist + " - " + title, "UTF-8") + "&country=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry() + "&entity=song&limit=20"), null);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject iTunesResponse = new JSONObject(response);

        if (iTunesResponse.getInt("resultCount") == 0) {
            return null;
        } else {
            for (int i = 0; i < 20; i++) {
                if (iTunesResponse.getJSONArray("results").getJSONObject(i).getString("artistName").equals(artist) && iTunesResponse.getJSONArray("results").getJSONObject(i).getString("collectionName").equals(title)) {
                    return iTunesResponse.getJSONArray("results").getJSONObject(i);
                }
            }
        }

        return iTunesResponse.getJSONArray("results").getJSONObject(0);
    }

    private JSONObject getiTunesTrackInfo(long identifier) throws IOException {
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://itunes.apple.com/lookup?id=" + identifier + "&country=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry() + "&entity=song&limit=20"), null);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject iTunesResponse = new JSONObject(response);

        if (iTunesResponse.getInt("resultCount") == 0) {
            return null;
        }

        return iTunesResponse.getJSONArray("results").getJSONObject(0);
    }

    private JSONObject getiTunesAlbumInfo(long identifier) throws IOException {
        BrowserData data = BrowserClient.executeGETRequest(new URL("https://itunes.apple.com/lookup?id=" + identifier + "&country=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry() + "&entity=song&limit=20"), null);
        String response = BrowserClient.requestToString(data.getResponse());
        JSONObject iTunesResponse = new JSONObject(response);

        if (iTunesResponse.getInt("resultCount") == 0) {
            return null;
        }

        return iTunesResponse.getJSONArray("results").getJSONObject(0);
    }

    private JSONObject getSongDotLinkInfo(String songLink) throws IOException {
        Map<String, String> headers = new HashMap<>();

        BrowserData data = BrowserClient.executeGETRequest(new URL("https://api.song.link/v1-alpha.1/links?userCountry=" + LogRPC.INSTANCE.getConfig().getMusicArtworkCountry() + "&type=song&songIfSingle=true&url=" + songLink), headers);
        String response = BrowserClient.requestToString(data.getResponse());

        return new JSONObject(response);
    }
}