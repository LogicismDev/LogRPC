package me.Logicism.LogRPC.event;

import com.jagrosh.discordipc.entities.RichPresence;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.event.EventHandler;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.network.HypeRateWebSocketClient;
import me.Logicism.LogRPC.presence.game.*;
import me.Logicism.LogRPC.presence.manual.CustomizablePresence;
import me.Logicism.LogRPC.presence.manual.DefaultPresence;
import me.Logicism.LogRPC.presence.music.*;
import me.Logicism.LogRPC.presence.program.*;
import me.Logicism.LogRPC.presence.Presence;
import me.Logicism.LogRPC.presence.website.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.*;

public class UpdatePresenceHandler extends EventHandler {

    @Override
    public void onUpdatePresence(UpdatePresenceEvent e) {
        RichPresence.Builder builder = new RichPresence.Builder();
        Presence presence = null;

        if (e.getType() == PresenceType.MANUAL) {
            if (e.getData() instanceof CustomizablePresence) {
                presence = (CustomizablePresence) e.getData();
            } else {
                if (((JSONData) e.getData()).getDetails().equals("DefaultPresence")) {
                    presence = new DefaultPresence();
                } else if (((JSONData) e.getData()).getDetails().equals("Nintendo3DSPresence")) {
                    presence = new Nintendo3DSPresence(e.getData());
                } else if (((JSONData) e.getData()).getDetails().equals("WiiUPresence")) {
                    presence = new WiiUPresence(e.getData());
                } else if (((JSONData) e.getData()).getDetails().equals("NintendoSwitchPresence")) {
                    presence = new NintendoSwitchPresence(e.getData());
                } else if (((JSONData) e.getData()).getDetails().equals("NintendoSwitchPresence1")) {
                    presence = new NintendoSwitchPresence1(e.getData());
                } else if (((JSONData) e.getData()).getDetails().equals("OverwatchPresence")) {
                    presence = new OverwatchPresence(e.getData());
                }
            }
        } else if (e.getType() == PresenceType.EXTENSION) {
            try {
                URL url = new URL(((BrowserHTMLData) e.getData()).getURL());

                if (url.getHost().equals("www.youtube.com") && ((BrowserHTMLData) e.getData()).isEnabled("youtube")) {
                    presence = new YouTubePresence(e.getData());
                } else if (url.getHost().contains("wikipedia.org") && ((BrowserHTMLData) e.getData()).isEnabled("wikipedia")) {
                    presence = new WikipediaPresence(e.getData());
                } else if ((url.getHost().equals("www.twitch.tv") || url.getHost().equals("dev.twitch.tv") || url.getHost().equals("devstatus.twitch.tv") || url.getHost().equals("discuss.dev.twitch.tv")) && ((BrowserHTMLData) e.getData()).isEnabled("twitch")) {
                    presence = new TwitchPresence(e.getData());
                } else if (url.getHost().equals("www.dailymotion.com") && ((BrowserHTMLData) e.getData()).isEnabled("dailymotion")) {
                    presence = new DailymotionPresence(e.getData());
                } else if (url.getHost().equals("x.com") && ((BrowserHTMLData) e.getData()).isEnabled("twitter")) {
                    presence = new TwitterPresence(e.getData());
                } else if (url.getHost().equals("www.instagram.com") && ((BrowserHTMLData) e.getData()).isEnabled("instagram")) {
                    presence = new InstagramPresence(e.getData());
                } else if (url.getHost().equals("www.netflix.com") && ((BrowserHTMLData) e.getData()).isEnabled("netflix")) {
                    presence = new NetflixPresence(e.getData());
                } else if (url.getHost().equals("www.disneyplus.com") && ((BrowserHTMLData) e.getData()).isEnabled("disneyplus")) {
                    presence = new DisneyPlusPresence(e.getData());
                }
            } catch (MalformedURLException ignored) {

            }
        } else if (e.getType() == PresenceType.PROGRAM) {
            if ((((JSONData) e.getData()).getDetails().startsWith("photoshop_") || ((JSONData) e.getData()).getDetails().equals("photoshop") || ((JSONData) e.getData()).getDetails().startsWith("Photoshop_") || ((JSONData) e.getData()).getDetails().equals("Photoshop")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("photoshop")) {
                presence = new PhotoshopPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("illustrator_") || ((JSONData) e.getData()).getDetails().equals("illustrator")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("illustrator")) {
                presence = new IllustratorPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("aftereffects_") || ((JSONData) e.getData()).getDetails().equals("aftereffects") || ((JSONData) e.getData()).getDetails().startsWith("AfterFX_") || ((JSONData) e.getData()).getDetails().equals("AfterFX")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("aftereffects")) {
                presence = new AfterEffectsPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("indesign_") || ((JSONData) e.getData()).getDetails().equals("indesign") || ((JSONData) e.getData()).getDetails().startsWith("InDesign_") || ((JSONData) e.getData()).getDetails().equals("InDesign")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("indesign")) {
                presence = new InDesignPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("Adobe Premiere Pro_") || ((JSONData) e.getData()).getDetails().equals("Adobe Premiere Pro")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("premierepro")) {
                presence = new PremiereProPresence(e.getData());
            } else if (((JSONData) e.getData()).getDetails().startsWith("Dreamweaver_") || ((JSONData) e.getData()).getDetails().equals("Dreamweaver") && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("dreamweaver")) {
                presence = new DreamweaverPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("acrobat_") || ((JSONData) e.getData()).getDetails().equals("acrobat")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("acrobat")) {
                presence = new AcrobatPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("vegas140_") || ((JSONData) e.getData()).getDetails().equals("vegas140") || ((JSONData) e.getData()).getDetails().startsWith("vegas150_") || ((JSONData) e.getData()).getDetails().equals("vegas150") || ((JSONData) e.getData()).getDetails().startsWith("vegas160_") || ((JSONData) e.getData()).getDetails().equals("vegas160") || ((JSONData) e.getData()).getDetails().startsWith("vegas170_") || ((JSONData) e.getData()).getDetails().equals("vegas170") || ((JSONData) e.getData()).getDetails().startsWith("vegas180_") || ((JSONData) e.getData()).getDetails().equals("vegas180") || ((JSONData) e.getData()).getDetails().startsWith("vegas190_") || ((JSONData) e.getData()).getDetails().equals("vegas190") || ((JSONData) e.getData()).getDetails().startsWith("vegas200_") || ((JSONData) e.getData()).getDetails().equals("vegas200")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("vegaspro")) {
                presence = new MAGIXVEGASProPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("Resolve_") || ((JSONData) e.getData()).getDetails().equals("Resolve")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("davinciresolve")) {
                presence = new DaVinciResolvePresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("Zoom_") || ((JSONData) e.getData()).getDetails().equals("Zoom")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("zoom")) {
                presence = new ZoomPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("WINWORD_") || ((JSONData) e.getData()).getDetails().equals("WINWORD")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("microsoftoffice")) {
                presence = new OfficeWordPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("POWERPNT_") || ((JSONData) e.getData()).getDetails().equals("POWERPNT")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("microsoftpowerpoint")) {
                presence = new OfficePowerpointPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("EXCEL_") || ((JSONData) e.getData()).getDetails().equals("EXCEL")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("microsoftexcel")) {
                presence = new OfficeExcelPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("mpc-hc64_") || ((JSONData) e.getData()).getDetails().equals("mpc-hc64")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("mpchc")) {
                presence = new MPCHCPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("vlc_") || ((JSONData) e.getData()).getDetails().equals("vlc")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("vlc")) {
                presence = new VLCPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("PotPlayer_") || ((JSONData) e.getData()).getDetails().equals("PotPlayer") || ((JSONData) e.getData()).getDetails().startsWith("PotPlayer64_") || ((JSONData) e.getData()).getDetails().equals("PotPlayer64") || ((JSONData) e.getData()).getDetails().startsWith("PotPlayerMini_") || ((JSONData) e.getData()).getDetails().equals("PotPlayerMini") || ((JSONData) e.getData()).getDetails().startsWith("PotPlayerMini64_") || ((JSONData) e.getData()).getDetails().equals("PotPlayerMini64")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("potplayer")) {
                presence = new PotPlayerPresence(e.getData());
            } else if (((JSONData) e.getData()).getDetails().startsWith("soffice.bin_") || ((JSONData) e.getData()).getDetails().equals("soffice.bin")) {
                if (e.getData().getTitle().endsWith(" — LibreOffice Writer") && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("libreofficewriter")) {
                    presence = new LibreWriterPresence(e.getData());
                } else if ((e.getData().getTitle().endsWith(" - LibreOffice Calc") || e.getData().getTitle().endsWith(" — LibreOffice Calc")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("libreofficecalc")) {
                    presence = new LibreCalcPresence(e.getData());
                } else if ((e.getData().getTitle().endsWith(" - LibreOffice Impress") || e.getData().getTitle().endsWith(" — LibreOffice Impress")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("libreofficeimpress")) {
                    presence = new LibreImpressPresence(e.getData());
                } else if ((e.getData().getTitle().endsWith(" - LibreOffice Draw") || e.getData().getTitle().endsWith(" — LibreOffice Draw")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("libreofficedraw")) {
                    presence = new LibreDrawPresence(e.getData());
                } else if ((e.getData().getTitle().endsWith(" - LibreOffice Base") || e.getData().getTitle().endsWith(" — LibreOffice Base")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("libreofficebase")) {
                    presence = new LibreBasePresence(e.getData());
                } else if ((e.getData().getTitle().endsWith(" - LibreOffice Math") || e.getData().getTitle().endsWith(" — LibreOffice Math")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("libreofficemath")) {
                    presence = new LibreMathPresence(e.getData());
                }
            } else if ((((JSONData) e.getData()).getDetails().startsWith("notepad++_") || ((JSONData) e.getData()).getDetails().equals("notepad++")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("notepadplusplus")) {
                presence = new NotepadPlusPlusPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("gimp-2.10_") || ((JSONData) e.getData()).getDetails().equals("gimp-2.10")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("gimp")) {
                presence = new GIMPPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("DeSmuME_") || ((JSONData) e.getData()).getDetails().startsWith("DeSmuME")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("desmume")) {
                presence = new DeSmuMEPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("melonDS_") || ((JSONData) e.getData()).getDetails().startsWith("melonDS")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("melonds")) {
                presence = new MelonDSPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("NO$GBA_") || ((JSONData) e.getData()).getDetails().startsWith("NO$GBA")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("nogba")) {
                presence = new NO$GBAPresence();
            } else if ((((JSONData) e.getData()).getDetails().startsWith("visualboyadvance-m_") || ((JSONData) e.getData()).getDetails().startsWith("visualboyadvance-m")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("vbam")) {
                presence = new VBAMPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("Mesen_") || ((JSONData) e.getData()).getDetails().startsWith("Mesen")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("mesen")) {
                presence = new MesenPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("fceux_") || ((JSONData) e.getData()).getDetails().startsWith("fceux")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("fceux")) {
                presence = new FCEUXPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("snes9x-x64_") || ((JSONData) e.getData()).getDetails().startsWith("snes9x-x64") || ((JSONData) e.getData()).getDetails().startsWith("snes9x_") || ((JSONData) e.getData()).getDetails().startsWith("snes9x")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("snes9x")) {
                System.out.println("HI");
                presence = new Snes9xPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("DOSBox_") || ((JSONData) e.getData()).getDetails().startsWith("DOSBox")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("dosbox")) {
                presence = new DOSBoxPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("mame_") || ((JSONData) e.getData()).getDetails().startsWith("mame")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("mame")) {
                presence = new MAMEPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("Fusion_") || ((JSONData) e.getData()).getDetails().startsWith("Fusion")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("fusion")) {
                presence = new FusionPresence(e.getData());
            } else if ((((JSONData) e.getData()).getDetails().startsWith("Beat Saber_") || ((JSONData) e.getData()).getDetails().startsWith("Beat Saber")) && LogRPC.INSTANCE.getConfig().getEnabledPrograms().contains("beatsaber")) {
                presence = new BeatSaberManualPresence(e.getData());
            }
        } else if (e.getType() == PresenceType.MUSIC) {
            if (((JSONData) e.getData()).getAttribute("app_name").equals("Amazon Music.exe") || ((JSONData) e.getData()).getAttribute("app_name").equals("Amazon.Music")) {
                presence = new AmazonMusicPresence(e.getData());
            } else if (((JSONData) e.getData()).getAttribute("app_name").equals("Apple Music")) {
                presence = new AppleMusicPresence(e.getData());
            } else if (((JSONData) e.getData()).getAttribute("app_name").equals("Deezer.exe") || ((JSONData) e.getData()).getAttribute("app_name").equals("com.deezer.deezer-desktop")) {
                presence = new DeezerMusicPresence(e.getData());
            } else if (((JSONData) e.getData()).getAttribute("app_name").equals("iTunes.exe") || ((JSONData) e.getData()).getAttribute("app_name").equals("49586DaveAntoine.MediaControllerforiTunes_9bzempp7dntjg!App")) {
                presence = new iTunesMusicPresence(e.getData());
            } else if (((JSONData) e.getData()).getAttribute("app_name").equals("Qobuz.exe") || ((JSONData) e.getData()).getAttribute("app_name").equals("com.squirrel.Qobuz.Qobuz")) {
                presence = new QobuzMusicPresence(e.getData());
            } else if (((JSONData) e.getData()).getAttribute("app_name").equals("TIDAL.exe") || ((JSONData) e.getData()).getAttribute("app_name").equals("com.squirrel.TIDAL.TIDAL")) {
                presence = new TIDALMusicPresence(e.getData());
            } else {
                presence = new DefaultMusicPresence(e.getData());
            }
        } else if (e.getType() == PresenceType.BEAT_SABER) {
            presence = new BeatSaberPresence(e.getData());
        } else if (e.getType() == PresenceType.WIIMMFI) {
            presence = new WiimmfiPresence(e.getData());
        } else if (e.getType() == PresenceType.DESMUME) {
            presence = new DeSmuMEPokémonPresence(e.getData());
        } else if (e.getType() == PresenceType.VLC_MEDIA_PLAYER) {
            presence = new VLCPresence(e.getData());
        } else if (e.getType() == PresenceType.MPCHC_MEDIA_PLAYER) {
            presence = new MPCHCPresence(e.getData());
        }

        if (presence != null) {
            builder.setActivityType(presence.getActivityType());

            if (!presence.getState().isEmpty()) {
                builder.setState(new String(presence.getState().length() < 128 ? presence.getState().getBytes(StandardCharsets.UTF_8) : presence.getState().substring(0, 128).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            }

            builder.setDetails(new String(presence.getDetails().length() < 128 ? presence.getDetails().getBytes(StandardCharsets.UTF_8) : presence.getDetails().substring(0, 128).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));

            if (presence.getLargeImageKey() != null && !presence.getLargeImageKey().isEmpty()) {
                builder.setLargeImage(presence.getLargeImageKey(),
                        new String(presence.getLargeImageText().length() < 128 ? presence.getLargeImageText().getBytes(StandardCharsets.UTF_8) : presence.getLargeImageText().substring(0, 128).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));

                if (presence.getSmallImageKey() != null && !presence.getSmallImageKey().isEmpty()) {
                    builder.setSmallImage(presence.getSmallImageKey(),
                            new String(presence.getSmallImageText().length() < 128 ? presence.getSmallImageText().getBytes(StandardCharsets.UTF_8) : presence.getSmallImageText().substring(0, 128).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
                }
            }

            if (presence.getMainButtonText() != null) {
                builder.setMainButtonText(new String(presence.getMainButtonText().length() < 32 ? presence.getMainButtonText().getBytes(StandardCharsets.UTF_8) : presence.getMainButtonText().substring(0, 32).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)).setMainButtonURL(presence.getMainButtonURL());
            }

            if (presence.getSecondaryButtonText() != null) {
                builder.setSecondaryButtonText(new String(presence.getSecondaryButtonText().length() < 32 ? presence.getSecondaryButtonText().getBytes(StandardCharsets.UTF_8) : presence.getSecondaryButtonText().substring(0, 32).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)).setSecondaryButtonURL(presence.getSecondaryButtonURL());
            }

            if (presence.getStartTimestamp() != -1) {
                if (presence.getStartTimestamp() == 0) {
                    builder.setStartTimestamp(OffsetDateTime.now());
                } else {
                    builder.setStartTimestamp(OffsetDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - presence.getStartTimestamp()), ZoneId.of("UTC")));
                }
            }

            if (presence.getEndTimestamp() != -1) {
                builder.setEndTimestamp(OffsetDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - (presence.getStartTimestamp() - presence.getEndTimestamp())), ZoneId.of("UTC")));
            }

            if (presence.getPartySize() > 0 && presence.getPartyMax() > 0) {
                builder.setParty("logrpc-" + LogRPC.VERSION, presence.getPartySize(), presence.getPartyMax(), "PRIVATE");
            }

            if (!presence.getDetails().isEmpty()) {
                System.out.println(builder.build().toJson());

                if (e.getInstance().getClientID() != presence.getClientID()) {
                    e.getInstance().setClientID(presence.getClientID());

                    e.getInstance().setPresence(builder, true);

                    e.getInstance().reinitializeClient();
                } else {
                    if (e.getInstance().getPresence() != null) {
                        JSONObject iPresence = e.getInstance().getPresence().build().toJson();
                        JSONObject ePresence = builder.build().toJson();
                        try {
                            if (!iPresence.getJSONObject("timestamps").has("end")) {
                                iPresence.remove("timestamps");
                            }
                            if (!ePresence.getJSONObject("timestamps").has("end")) {
                                ePresence.remove("timestamps");
                            }
                        } catch (JSONException e1) {

                        }
                        if (!iPresence.toString().equals(ePresence.toString())) {
                            if (!builder.build().toJson().toString().contains("${hrRate}")) {
                                if (LogRPC.INSTANCE.getHypeRateWebSocketClient() != null) {
                                    LogRPC.INSTANCE.getHypeRateWebSocketClient().close();
                                    LogRPC.INSTANCE.setHypeRateWebSocketClient(null);
                                }

                                e.getInstance().setPresence(builder, false);
                            } else {
                                if (LogRPC.INSTANCE.getHypeRateWebSocketClient() == null) {
                                    e.getInstance().setPresence(builder, true);

                                    LogRPC.INSTANCE.setHypeRateWebSocketClient(new HypeRateWebSocketClient());
                                    LogRPC.INSTANCE.getHypeRateWebSocketClient().connect();
                                } else {
                                    LogRPC.INSTANCE.getHypeRateWebSocketClient().setPresence(builder);
                                }
                            }
                        }
                    } else {
                        if (!builder.build().toJson().toString().contains("${hrRate}")) {
                            if (LogRPC.INSTANCE.getHypeRateWebSocketClient() != null) {
                                LogRPC.INSTANCE.getHypeRateWebSocketClient().close();
                                LogRPC.INSTANCE.setHypeRateWebSocketClient(null);
                            }

                            e.getInstance().setPresence(builder, false);
                        } else {
                            if (LogRPC.INSTANCE.getHypeRateWebSocketClient() == null) {
                                e.getInstance().setPresence(builder, true);

                                LogRPC.INSTANCE.setHypeRateWebSocketClient(new HypeRateWebSocketClient());
                                LogRPC.INSTANCE.getHypeRateWebSocketClient().connect();
                            } else {
                                LogRPC.INSTANCE.getHypeRateWebSocketClient().setPresence(builder);
                            }
                        }
                    }
                }
            }
        }
    }
}
