package me.Logicism.LogRPC.presence.website;

import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

public class InstagramPresence extends Presence {

    public InstagramPresence(PresenceData data) {
        super(547436289960574977L, data);
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().equals("https://www.instagram.com/")) {
            return "Browsing Homepage";
        } else if (data.getURL().equals("https://www.instagram.com/direct/inbox/") || data.getURL().startsWith("https://www.instagram.com/direct/t/")) {
            return "Reading DMs";
        } else if (data.getURL().equals("https://www.instagram.com/direct/new/")) {
            return "Composing New DM";
        } else if (data.getURL().equals("https://www.instagram.com/explore/")) {
            return "Browsing Explore";
        } else if (data.getURL().startsWith("https://www.instagram.com/p/")) {
            return "Viewing a Post";
        } else if (data.getURL().startsWith("https://www.instagram.com/accounts/")) {
            return "Browsing Account Settings";
        } else if (data.getURL().startsWith("https://www.instagram.com/stories/")) {
            return "Viewing a Story";
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_yt > div > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div > div > div.alzwoclg.cqf1kptm.p1t2w4gn.fawcizw8.om3e55n1.g4tp4svg > section > main > div > header > section > div._aa_m > h2");

            if (element != null) {
                return "Viewing a Profile";
            }
        }

        return "Browsing Instagram";
    }

    @Override
    public String getState() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.instagram.com/direct/t/")) {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_yt > div > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div > div > div.alzwoclg.cqf1kptm.p1t2w4gn.fawcizw8.om3e55n1.g4tp4svg > div:nth-child(1) > section > div > div._ab8w._ab94._ab99._ab9f._ab9m._ab9p._abcm > div > div > div._ab8s._ab8w._ab94._ab99._ab9f._ab9m._ab9o._abcm > div._ab61 > div > div > div._aa4o > div > div._ab8w._ab94._ab99._ab9f._ab9m._ab9q._abb1._abcm > button > div > div._ab8w._ab94._ab99._ab9f._ab9k._ab9p._abcm > div");

            return element.text();
        } else if (data.getURL().startsWith("https://www.instagram.com/p/")) {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_WH > div > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div > div > div.alzwoclg.cqf1kptm.p1t2w4gn.fawcizw8.om3e55n1.g4tp4svg > section > main > div._aa6b._ad9f._aa6d > div._aa6e > article > div > div._ab8w._ab94._ab99._ab9f._ab9m._ab9p._abcm > div > div._aasi > div > header > div._aaqy._aaqz > div._aar0._ad95._aar1 > div.alzwoclg > div > div > div._aacl._aaco._aacw._adda._aacx._aad6._aade > span > a");

            if (element == null) {
                element = data.getHTMLDocument().selectFirst("#mount_0_0_WH > div > div > div > div:nth-child(4) > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div.th8rvtx1.f7rl1if4.adechonz.rufpak1n.qtovjlwq.qbmienfq.rfyhaz4c.rdmi1yqr.ohrdq8us.nswx41af.fawcizw8.l1aqi3e3.om3e55n1.sdu1flz4.dahkl6ri > div > div > div > div > div.hw7435fk.ba4ynyj4.mm05nxu8.l2tm8nht.o9w3sbdw.nu7423ey.dl2p71xr.h0c7ht3v.j8nb7h05.gffp4m6x.l10tt5db.mfclru0v.mv53e3c5.q6ul9yy4.pry8b2m5.i7rjuzed.p8zq7ayg.q2git0d3.fdbvkn0i > div > article > div > div._ae65 > div > div > div._aasi > div > header > div._aaqy._aaqz > div._aar0._ad95._aar1 > div.alzwoclg > div > div > div._aacl._aaco._aacw._adda._aacx._aad6._aade > span > a");
            }

            return element.text();
        } else if (data.getURL().startsWith("https://www.instagram.com/stories/")) {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_BF > div > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div > div > div.alzwoclg.cqf1kptm.p1t2w4gn.fawcizw8.om3e55n1.g4tp4svg > div:nth-child(1) > section > div._ab8w._ab94._ab97._ab9h._ab9m._ab9o._abcm > div > section > div > header > div._ab8w._ab94._ab97._ab9i._ab9k._ab9p._abcm > div._ac0l > div > div > div > div > a");

            return element.text();
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_yt > div > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div > div > div.alzwoclg.cqf1kptm.p1t2w4gn.fawcizw8.om3e55n1.g4tp4svg > section > main > div > header > section > div._aa_m > h2");

            if (element != null) {
                return element.text();
            }
        }

        return "";
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.instagram.com/p/") || data.getURL().startsWith("https://www.instagram.com/stories/")) {
            return data.getURL();
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_yt > div > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div > div > div.alzwoclg.cqf1kptm.p1t2w4gn.fawcizw8.om3e55n1.g4tp4svg > section > main > div > header > section > div._aa_m > h2");

            if (element != null) {
                return data.getURL();
            }
        }

        return null;
    }

    @Override
    public String getMainButtonText() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.instagram.com/p/")) {
            return "View Post";
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_yt > div > div > div > div.bdao358l.om3e55n1.g4tp4svg > div > div > div > div.alzwoclg.cqf1kptm.p1t2w4gn.fawcizw8.om3e55n1.g4tp4svg > section > main > div > header > section > div._aa_m > h2");

            if (element != null) {
                return "View Profile";
            }
        }

        return null;
    }

    @Override
    public String getLargeImageKey() {
        return "logo";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
