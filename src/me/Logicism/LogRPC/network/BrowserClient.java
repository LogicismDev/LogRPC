package me.Logicism.LogRPC.network;

import me.Logicism.LogRPC.LogRPC;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class BrowserClient {

    public static BrowserData executeGETRequest(URL url, Map<String, String> headers) throws IOException {
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestProperty("User-Agent", "Java 17 / LogRPC " + LogRPC.VERSION);
        c.setConnectTimeout(30000);
        c.setReadTimeout(30000);
        c.setInstanceFollowRedirects(false);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                c.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        int resCode = c.getResponseCode();
        int resLength = c.getContentLength();

        return new BrowserData(c.getURL().toString(), c.getHeaderFields(), resCode, resLength, c.getInputStream() != null ? c.getInputStream() : c.getErrorStream());
    }

    public static BrowserData executePOSTRequest(URL url, String data, Map<String, String> headers) throws IOException {
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestProperty("User-Agent", "Java 17 / LogRPC " + LogRPC.VERSION);
        c.setConnectTimeout(30000);
        c.setReadTimeout(30000);
        c.setInstanceFollowRedirects(false);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                c.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        c.setDoInput(true);
        c.setDoOutput(true);

        DataOutputStream dos = new DataOutputStream(c.getOutputStream());
        dos.writeBytes(data);
        dos.flush();
        dos.close();

        int resCode = c.getResponseCode();
        int resLength = c.getContentLength();

        return new BrowserData(c.getURL().toString(), c.getHeaderFields(), resCode, resLength, c.getInputStream() != null ? c.getInputStream() : c.getErrorStream());
    }

    public static String requestToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }

        is.close();

        return sb.toString();
    }

}
