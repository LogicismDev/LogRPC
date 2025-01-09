package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import org.jsoup.nodes.Element;

public class InstagramPresence extends Presence {

    public InstagramPresence(PresenceData data) {
        super(547436289960574977L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public String getDetails() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().equals("https://www.instagram.com/")) {
            return "Browsing Homepage";
        } else if (data.getURL().equals("https://www.instagram.com/direct/inbox/") || data.getURL().startsWith("https://www.instagram.com/direct/t/")) {
            return "Reading DMs";
        } else if (data.getURL().equals("https://www.instagram.com/explore/")) {
            return "Browsing Explore";
        } else if (data.getURL().startsWith("https://www.instagram.com/reels/")) {
            return "Watching Reels";
        } else if (data.getURL().startsWith("https://www.instagram.com/p/")) {
            return "Viewing a Post";
        } else if (data.getURL().startsWith("https://www.instagram.com/accounts/")) {
            return "Browsing Account Settings";
        } else if (data.getURL().startsWith("https://www.instagram.com/stories/")) {
            return "Viewing a Story";
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_kf > div > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div:nth-child(2) > div > div.x1gryazu.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > header > section.x1xdureb.x1agbcgv.x1lhsz42.xieb3on.xr1yuqi.x6ikm8r.x10wlt62.xs5motx > div > div > div.x9f619.xjbqb8w.x78zum5.x168nmei.x13lgxp2.x5pf9jr.xo71vjh.x1h5jrl4.x1uhb9sk.x6ikm8r.x10wlt62.x1c4vz4f.xs83m0k.xdt5ytf.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div > a > h2 > span");

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
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_kf > div > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.x9f619.xvbhtw8.x78zum5.x168nmei.x13lgxp2.x5pf9jr.xo71vjh.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.x1gryazu.xh8yej3.x10o80wk.x14k21rp.x1v4esvl.x8vgawa > section > main > section > div > div > div > div.xjp7ctv > div > div.x9f619.x1n2onr6.x1ja2u2z.x78zum5.xdt5ytf.x193iq5w.xeuugli.x1r8uery.x1iyjqo2.xs83m0k > div > div > div.x1ja2u2z.x9f619.x78zum5.xdt5ytf.x193iq5w.x1l7klhg.x1iyjqo2.xs83m0k.x2lwn1j.xcrg951.x6prxxf.x6ikm8r.x10wlt62.x1n2onr6.xh8yej3 > div > div > div > div.x1vjfegm > div > div.x9f619.x1ja2u2z.x78zum5.x1n2onr6.x1r8uery.x1iyjqo2.xs83m0k.xeuugli.x1qughib.x6s0dn4.xozqiw3.x1q0g3np.xexx8yu.xykv574.xbmpl8g.x4cne27.xifccgj > div.x9f619.x1n2onr6.x1ja2u2z.x78zum5.xdt5ytf.x193iq5w.xeuugli.x1r8uery.x1iyjqo2.xs83m0k.xsyo7zv.x16hj40l.x10b6aqq.x1yrsyyn > a > div > div > h2 > span");

            return element.text();
        } else if (data.getURL().startsWith("https://www.instagram.com/p/")) {
            Element element = data.getHTMLDocument().selectFirst("body > div.x14dbnvc.x67yw2k.x1f1tace.x1xb1xrg.xz3gdfk.xbi9o00.x1dbek64.x4666fc.x1n2onr6.xzkaem6 > div.x9f619.x1n2onr6.x1ja2u2z > div > div.x1uvtmcs.x4k7w5x.x1h91t0o.x1beo9mf.xaigb6o.x12ejxvf.x3igimt.xarpa2k.xedcshv.x1lytzrv.x1t2pt76.x7ja8zs.x1n2onr6.x1qrby5j.x1jfb8zj > div > div > div > div > div.xb88tzc.xw2csxc.x1odjw0f.x5fp0pe.x1qjc9v5.xjbqb8w.x1lcm9me.x1yr5g0i.xrt01vj.x10y3i5r.xr1yuqi.xkrivgy.x4ii5y1.x1gryazu.x15h9jz8.x47corl.xh8yej3.xir0mxb.x1juhsu6 > div > article > div > div.x1qjc9v5.x972fbf.xcfux6l.x1qhh985.xm0m39n.x9f619.x78zum5.xdt5ytf.x1iyjqo2.x5wqa0o.xln7xf2.xk390pu.xdj266r.x11i5rnm.xat24cr.x1mh8g0r.x65f84u.x1vq45kp.xexx8yu.x4uap5.x18d9i69.xkhd6sd.x1n2onr6.x11njtxf > div > div > div._aasi > div > header > div._aaqy._aaqz > div._aar0._aar1 > div > div > div > span > span > div > a");

            if (element == null) {
                element = data.getHTMLDocument().selectFirst("#mount_0_0_kf > div > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.x9f619.xvbhtw8.x78zum5.x168nmei.x13lgxp2.x5pf9jr.xo71vjh.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.x1gryazu.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > div.x6s0dn4.x78zum5.xdt5ytf.xdj266r.xkrivgy.xat24cr.x1gryazu.x1n2onr6.xh8yej3 > div > div.x4h1yfo > div > div.xyinxu5.x1pi30zi.x1g2khh7.x1swvt13 > div > div.x9f619.xjbqb8w.x78zum5.x168nmei.x13lgxp2.x5pf9jr.xo71vjh.x1uhb9sk.x1plvlek.xryxfnj.x1iyjqo2.x2lwn1j.xeuugli.x1q0g3np.xqjyukv.x6s0dn4.x1oa3qoh.x1nhvcw1 > div > div:nth-child(1) > div:nth-child(1) > div > span > span > div > a > div > div > span");
            }

            return element.text();
        } else if (data.getURL().startsWith("https://www.instagram.com/stories/")) {
            Element element = data.getHTMLDocument().selectFirst("div > div > div.xoqlrxr.xds687c.x17qophe.x6ikm8r.x10wlt62.x1cnzs8.x1pi30zi.x1gan7if.x1swvt13.x10l6tqk.x13vifvy.x1vjfegm.x1hc1fzr.x1d8287x.x19991ni.xwji4o3 > div > div.x6s0dn4.x78zum5.x1xmf6yo > div.x6s0dn4.x78zum5.x1iyjqo2.xs83m0k.x1emribx.x6ikm8r.x10wlt62 > div > div.x78zum5.xdt5ytf.x1gnnpzl.x1i64zmx.xeuugli.x1qughib > div.x6s0dn4.x78zum5 > div:nth-child(1) > a > span > span > span");

            return element.text();
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_kf > div > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div:nth-child(2) > div > div.x1gryazu.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > header > section.x1xdureb.x1agbcgv.x1lhsz42.xieb3on.xr1yuqi.x6ikm8r.x10wlt62.xs5motx > div > div > div.x9f619.xjbqb8w.x78zum5.x168nmei.x13lgxp2.x5pf9jr.xo71vjh.x1h5jrl4.x1uhb9sk.x6ikm8r.x10wlt62.x1c4vz4f.xs83m0k.xdt5ytf.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div > a > h2 > span");

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
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_kf > div > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div:nth-child(2) > div > div.x1gryazu.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > header > section.x1xdureb.x1agbcgv.x1lhsz42.xieb3on.xr1yuqi.x6ikm8r.x10wlt62.xs5motx > div > div > div.x9f619.xjbqb8w.x78zum5.x168nmei.x13lgxp2.x5pf9jr.xo71vjh.x1h5jrl4.x1uhb9sk.x6ikm8r.x10wlt62.x1c4vz4f.xs83m0k.xdt5ytf.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div > a > h2 > span");

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
        } else if (data.getURL().startsWith("https://www.instagram.com/stories/")) {
            return "View Story";
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_kf > div > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div:nth-child(2) > div > div.x1gryazu.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > header > section.x1xdureb.x1agbcgv.x1lhsz42.xieb3on.xr1yuqi.x6ikm8r.x10wlt62.xs5motx > div > div > div.x9f619.xjbqb8w.x78zum5.x168nmei.x13lgxp2.x5pf9jr.xo71vjh.x1h5jrl4.x1uhb9sk.x6ikm8r.x10wlt62.x1c4vz4f.xs83m0k.xdt5ytf.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div > a > h2 > span");

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
