package me.Logicism.LogRPC.presence.website;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
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
    public DisplayType getDisplayType() {
        return DisplayType.STATE;
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
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_jO > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.x16ye13r.xvbhtw8.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.xvc5jky.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > div > header > div > section:nth-child(2) > div.x7a106z.x972fbf.x10w94by.x1qhh985.x14e42zd.x9f619.x78zum5.xdt5ytf.x1yztbdb.xw7yly9.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x1n2onr6.x1r0jzty.x11njtxf.x1fkh5qu.x1ddbhtg.x1dlrdel > div.html-div.xdj266r.x14z9mp.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.xat24cr.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div.x78zum5.x193iq5w.x6ikm8r.x10wlt62 > a > h2 > span");

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
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_jO > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.x16ye13r.xvbhtw8.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.xvc5jky.xh8yej3.x10o80wk.x14k21rp.x1v4esvl.x8vgawa > section > main > div > section > div > div > div > div.x9f619.x2lah0s.x1nhvcw1.x1qjc9v5.xozqiw3.x1q0g3np.x78zum5.x1iyjqo2.x5yr21d.x1t2pt76.x1n2onr6.x1ja2u2z > div.x9f619.x1n2onr6.x1ja2u2z.x78zum5.xdt5ytf.x193iq5w.xeuugli.x1r8uery.x1iyjqo2.xs83m0k > div > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1iyjqo2.x2lwn1j.xeuugli.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1.xcrg951.x6prxxf.x6ikm8r.x10wlt62.x1n2onr6.xh8yej3 > div > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.x9f619.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.xyamay9.xv54qhq.x1l90r2v.xf7dkkf.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.xdt5ytf.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1.x1bs97v6.x1q0q8m5.xso031l.xh8yej3 > div > div > a > div > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xkj4a21.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.xs83m0k.xdt5ytf.xqjyukv.x1qjc9v5.x1oa3qoh.xl56j7k > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xkj4a21.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.xs83m0k.x1q0g3np.xqjyukv.x6s0dn4.x1oa3qoh.x1nhvcw1 > h2 > span > span");

            return element.text();
        } else if (data.getURL().startsWith("https://www.instagram.com/p/")) {
            Element element = data.getHTMLDocument().selectFirst("body > div.x1n2onr6.xzkaem6 > div.x9f619.x1n2onr6.x1ja2u2z > div > div.x1uvtmcs.x4k7w5x.x1h91t0o.x1beo9mf.xaigb6o.x12ejxvf.x3igimt.xarpa2k.xedcshv.x1lytzrv.x1t2pt76.x7ja8zs.x1n2onr6.x1qrby5j.x1jfb8zj > div > div > div > div > div.xb88tzc.xw2csxc.x1odjw0f.x5fp0pe.x1qjc9v5.xjbqb8w.xjwep3j.x1t39747.x1wcsgtt.x1pczhz8.xr1yuqi.x11t971q.x4ii5y1.xvc5jky.x15h9jz8.x47corl.xh8yej3.xir0mxb.x1juhsu6 > div > article > div > div.x1qjc9v5.x972fbf.x10w94by.x1qhh985.x14e42zd.x9f619.x78zum5.xdt5ytf.x1iyjqo2.x5wqa0o.xln7xf2.xk390pu.xdj266r.x14z9mp.xat24cr.x1lziwak.x65f84u.x1vq45kp.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x1n2onr6.x11njtxf > div > div > div._aasi._at8n > div > header > div._aaqy._aaqz > div._aar0._ad95._aar1 > div.x78zum5.x6ikm8r.x10wlt62 > div > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1n2onr6.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x6s0dn4.x1oa3qoh.x1nhvcw1 > span > span > span > div > div > a > div > span");

            if (element == null) {
                element = data.getHTMLDocument().selectFirst("body > div.x1n2onr6.xzkaem6 > div.x9f619.x1n2onr6.x1ja2u2z > div > div.x1uvtmcs.x4k7w5x.x1h91t0o.x1beo9mf.xaigb6o.x12ejxvf.x3igimt.xarpa2k.xedcshv.x1lytzrv.x1t2pt76.x7ja8zs.x1n2onr6.x1qrby5j.x1jfb8zj > div > div > div > div > div.xb88tzc.xw2csxc.x1odjw0f.x5fp0pe.x1qjc9v5.xjbqb8w.xjwep3j.x1t39747.x1wcsgtt.x1pczhz8.xr1yuqi.x11t971q.x4ii5y1.xvc5jky.x15h9jz8.x47corl.xh8yej3.xir0mxb.x1juhsu6 > div > article > div > div.x1qjc9v5.x972fbf.x10w94by.x1qhh985.x14e42zd.x9f619.x78zum5.xdt5ytf.x1iyjqo2.x5wqa0o.xln7xf2.xk390pu.xdj266r.x14z9mp.xat24cr.x1lziwak.x65f84u.x1vq45kp.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x1n2onr6.x11njtxf > div > div > div._aasi._at8n > div > header > div._aaqy._aaqz > div._aar0._aar1 > div > div > span > div");
            }

            return element.text();
        } else if (data.getURL().startsWith("https://www.instagram.com/stories/")) {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_Cs > div > div > div.x1n2onr6.x1vjfegm > div > div > div.x78zum5.xdt5ytf.xg6iff7.x1n2onr6.x1ja2u2z.x443n21 > div > section > div.x78zum5.xl56j7k > div > div > div.xyzq4qe.x5a5i1n.x1obq294.x5yr21d.x6ikm8r.x10wlt62.x1n2onr6.x87ps6o.xh8yej3.x1ja2u2z > div > div.xoqlrxr.xtijo5x.x1o0tod.x6ikm8r.x10wlt62.x1cnzs8.xv54qhq.x1gan7if.xf7dkkf.x10l6tqk.x13vifvy.x1vjfegm.x1hc1fzr.x1d8287x.x19991ni.xwji4o3 > div > div.x6s0dn4.x78zum5.x1xmf6yo > div.x6s0dn4.x78zum5.x1iyjqo2.xs83m0k.x1xegmmw.x6ikm8r.x10wlt62 > div > div.x78zum5.xdt5ytf.x1gnnpzl.x13fj5qh.xeuugli.x1qughib > div.x6s0dn4.x78zum5 > div:nth-child(1) > a > span > span.x1lliihq.x1plvlek.xryxfnj.x1n2onr6.xyejjpt.x15dsfln.x193iq5w.xeuugli.x1fj9vlw.x13faqbe.x1vvkbs.x1s928wv.xhkezso.x1gmr53x.x1cpjm7i.x1fgarty.x1943h6x.x1i0vuye.xvs91rp.xo1l8bm.x1g9anri.x1tu3fi.x3x7a5m.x10wh9bi.xpm28yp.x8viiok.x1o7cslx > span");

            return element.text();
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_jO > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.x16ye13r.xvbhtw8.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.xvc5jky.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > div > header > div > section:nth-child(2) > div.x7a106z.x972fbf.x10w94by.x1qhh985.x14e42zd.x9f619.x78zum5.xdt5ytf.x1yztbdb.xw7yly9.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x1n2onr6.x1r0jzty.x11njtxf.x1fkh5qu.x1ddbhtg.x1dlrdel > div.html-div.xdj266r.x14z9mp.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.xat24cr.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div.x78zum5.x193iq5w.x6ikm8r.x10wlt62 > a > h2 > span");

            if (element != null) {
                return element.text();
            }
        }

        return "";
    }

    @Override
    public String getStateURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.instagram.com/p/") || data.getURL().startsWith("https://www.instagram.com/stories/")) {
            return data.getURL();
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_jO > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.x16ye13r.xvbhtw8.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.xvc5jky.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > div > header > div > section:nth-child(2) > div.x7a106z.x972fbf.x10w94by.x1qhh985.x14e42zd.x9f619.x78zum5.xdt5ytf.x1yztbdb.xw7yly9.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x1n2onr6.x1r0jzty.x11njtxf.x1fkh5qu.x1ddbhtg.x1dlrdel > div.html-div.xdj266r.x14z9mp.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.xat24cr.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div.x78zum5.x193iq5w.x6ikm8r.x10wlt62 > a > h2 > span");

            if (element != null) {
                return data.getURL();
            }
        }

        return null;
    }

    @Override
    public String getMainButtonURL() {
        BrowserHTMLData data = (BrowserHTMLData) this.data;

        if (data.getURL().startsWith("https://www.instagram.com/p/") || data.getURL().startsWith("https://www.instagram.com/stories/")) {
            return data.getURL();
        } else {
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_jO > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.x16ye13r.xvbhtw8.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.xvc5jky.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > div > header > div > section:nth-child(2) > div.x7a106z.x972fbf.x10w94by.x1qhh985.x14e42zd.x9f619.x78zum5.xdt5ytf.x1yztbdb.xw7yly9.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x1n2onr6.x1r0jzty.x11njtxf.x1fkh5qu.x1ddbhtg.x1dlrdel > div.html-div.xdj266r.x14z9mp.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.xat24cr.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div.x78zum5.x193iq5w.x6ikm8r.x10wlt62 > a > h2 > span");

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
            Element element = data.getHTMLDocument().selectFirst("#mount_0_0_jO > div > div > div.x9f619.x1n2onr6.x1ja2u2z > div > div > div.x78zum5.xdt5ytf.x1t2pt76.x1n2onr6.x1ja2u2z.x10cihs4 > div.html-div.xdj266r.x14z9mp.xat24cr.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.x16ye13r.xvbhtw8.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1qughib > div.xvc5jky.xh8yej3.x10o80wk.x14k21rp.x17snn68.x6osk4m.x1porb0y.x8vgawa > section > main > div > div > header > div > section:nth-child(2) > div.x7a106z.x972fbf.x10w94by.x1qhh985.x14e42zd.x9f619.x78zum5.xdt5ytf.x1yztbdb.xw7yly9.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x1n2onr6.x1r0jzty.x11njtxf.x1fkh5qu.x1ddbhtg.x1dlrdel > div.html-div.xdj266r.x14z9mp.x1lziwak.xexx8yu.xyri2b.x18d9i69.x1c1uobl.x9f619.xjbqb8w.x78zum5.x15mokao.x1ga7v0g.x16uus16.xbiv7yw.xat24cr.x1uhb9sk.x1plvlek.xryxfnj.x1c4vz4f.x2lah0s.x1q0g3np.xqjyukv.x1qjc9v5.x1oa3qoh.x1nhvcw1 > div.x78zum5.x193iq5w.x6ikm8r.x10wlt62 > a > h2 > span");

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
