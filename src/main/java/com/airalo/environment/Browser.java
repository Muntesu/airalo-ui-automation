package com.airalo.environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;

public enum Browser {
    CHROME("chrome"),
    FIREFOX("firefox"),
    SAFARI("safari"),
    HEADLESS("headless");

    private static final Map<String, Browser> BY_LABEL = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(e -> BY_LABEL.put(e.driverName.toLowerCase(), e));
    }

    private final String driverName;


    Browser(String driverName) {
        this.driverName = driverName;
    }

    public static Browser valueOfLabel(String label) {
        return BY_LABEL.get(label.toLowerCase());
    }

}
