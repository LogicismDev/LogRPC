package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class PremiereProPresence extends Presence {

    public PremiereProPresence(PresenceData data) {
        super(887115440353067058L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().startsWith("Adobe Premiere Pro - ") || data.getTitle().startsWith("Adobe Premiere Pro")) {
            return data.getTitle().substring("Adobe Premiere Pro - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro CC 2014 - ") || data.getTitle().startsWith("Adobe Premiere Pro CC 2014")) {
            return data.getTitle().substring("Adobe Premiere Pro CC 2014 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro CC 2014 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro CC 2015 - ") || data.getTitle().startsWith("Adobe Premiere Pro CC 2015")) {
            return data.getTitle().substring("Adobe Premiere Pro CC 2015 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro CC 2015 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro CC 2016 - ") || data.getTitle().startsWith("Adobe Premiere Pro CC 2016")) {
            return data.getTitle().substring("Adobe Premiere Pro CC 2016 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro CC 2016 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro CC 2017 - ") || data.getTitle().startsWith("Adobe Premiere Pro CC 2017")) {
            return data.getTitle().substring("Adobe Premiere Pro CC 2017 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro CC 2017 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro CC 2018 - ") || data.getTitle().startsWith("Adobe Premiere Pro CC 2018")) {
            return data.getTitle().substring("Adobe Premiere Pro CC 2018 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro CC 2018 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro 2019 - ") || data.getTitle().startsWith("Adobe Premiere Pro 2019")) {
            return data.getTitle().substring("Adobe Premiere Pro 2019 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro 2019 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro 2020 - ") || data.getTitle().startsWith("Adobe Premiere Pro 2020")) {
            return data.getTitle().substring("Adobe Premiere Pro 2020 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro 2020 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro 2021 - ") || data.getTitle().startsWith("Adobe Premiere Pro 2021")) {
            return data.getTitle().substring("Adobe Premiere Pro 2021 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro 2021 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro 2022 - ") || data.getTitle().startsWith("Adobe Premiere Pro 2022")) {
            return data.getTitle().substring("Adobe Premiere Pro 2022 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro 2022 - ".length()).split("\\\\").length - 1];
        } else if (data.getTitle().startsWith("Adobe Premiere Pro 2023 - ") || data.getTitle().startsWith("Adobe Premiere Pro 2023")) {
            return data.getTitle().substring("Adobe Premiere Pro 2023 - ".length()).split("\\\\")[data.getTitle().substring("Adobe Premiere Pro 2023 - ".length()).split("\\\\").length - 1];
        } else {
            return "Adobe Premiere Pro CC";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "premierepro_large";
    }

    @Override
    public String getSmallImageKey() {
        return "premierepro_small";
    }

    @Override
    public String getSmallImageText() {
        return "Editing";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
