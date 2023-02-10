package me.Logicism.LogRPC.core.gui;

public class OverwatchMap implements Comparable<OverwatchMap> {

    private String key;
    private String[] types;
    private String name;

    public OverwatchMap(String key, String[] types, String name) {
        this.key = key;
        this.types = types;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String[] getTypes() {
        return types;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(OverwatchMap o) {
        return o.name.compareTo(this.name);
    }
}
