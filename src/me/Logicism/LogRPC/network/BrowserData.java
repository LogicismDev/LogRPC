package me.Logicism.LogRPC.network;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BrowserData {

    private String url;
    private Map<String, List<String>> headers;
    private int resCode;
    private int resLength;
    private InputStream response;

    public BrowserData(String url, Map<String, List<String>> headers, int resCode, int resLength, InputStream response) {
        this.url = url;
        this.headers = headers;
        this.resCode = resCode;
        this.resLength = resLength;
        this.response = response;
    }

    public String getURL() {
        return url;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public int getResponseCode() {
        return resCode;
    }

    public int getResponseLength() {
        return resLength;
    }

    public InputStream getResponse() {
        return response;
    }
}
