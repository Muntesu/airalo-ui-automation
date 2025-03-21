package com.airalo.logger;

import org.slf4j.LoggerFactory;

public class LoggerManager {

    private LoggerManager() {
    }

    public static void logInfo(String text) {
        LoggerFactory.getLogger(LoggerManager.class).info(text);
    }

    public static void logInfo(String text, Object ...obj) {
        LoggerFactory.getLogger(LoggerManager.class).info(text, obj);
    }

    public static void logError(String text) {
        LoggerFactory.getLogger(LoggerManager.class).error(text);
    }

    public static void logError(String text, Object ...obj) {
        LoggerFactory.getLogger(LoggerManager.class).error(text, obj);
    }
}
