package me.Logicism.LogRPC.core.executors;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.DeSmuMEData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class DeSmuMERunnable implements Runnable {
    @Override
    public void run() {
        while (LogRPC.INSTANCE.getDesmumeMenuItem().getState()) {
            try {
                List<String> list = Files.readAllLines(LogRPC.INSTANCE.getDesmumeRPCFile().toPath());

                String gameName = list.get(0);
                String imageKey = "";

                switch(gameName) {
                    case "0":
                        gameName = "Pokémon Diamond / Pearl Demo";
                        imageKey = "dp";
                        break;
                    case "1":
                        gameName = "Pokémon Diamond / Pearl";
                        imageKey = "dp";
                        break;
                    case "2":
                        gameName = "Pokémon Platinum";
                        imageKey = "plat";
                        break;
                    case "3":
                        gameName = "Pokémon HeartGold / SoulSilver";
                        imageKey = "hgss";
                        break;
                    case "4":
                        gameName = "Pokémon Diamond / Pearl Debug";
                        imageKey = "dp";
                        break;
                }

                String gameVer = list.get(1);

                switch (gameVer) {
                    case "0":
                        gameVer = "DE";
                        break;
                    case "1":
                        gameVer = "US / EU";
                        break;
                    case "2":
                        gameVer = "FR";
                        break;
                    case "3":
                        gameVer = "IT";
                        break;
                    case "4":
                        gameVer = "JP";
                        break;
                    case "5":
                        gameVer = "KS";
                        break;
                    case "6":
                        gameVer = "ES";
                        break;
                }

                String mapName = LogRPC.INSTANCE.getDesmumeMapHeaders().get(Integer.parseInt(list.get(2)));

                if (!mapName.equals(LogRPC.INSTANCE.getPresence().build().toJson().getString("state"))) {
                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.DESMUME, new DeSmuMEData(gameName, gameVer, imageKey, mapName)));
                }

                Thread.sleep(2000);
            } catch (IOException | JSONException | InterruptedException ex) {

            }
        }
    }
}
