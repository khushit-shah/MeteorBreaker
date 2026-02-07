package com.meteor.breaker;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class Log {
    private static volatile boolean configured = false;

    private Log() {}

    public static Logger getLogger(Class<?> type) {
        configure();
        return Logger.getLogger(type.getName());
    }

    private static synchronized void configure() {
        if (configured) {
            return;
        }

        Logger root = Logger.getLogger("");
        root.setLevel(Level.INFO);
        for (java.util.logging.Handler handler : root.getHandlers()) {
            root.removeHandler(handler);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("[%1$tF %1$tT] [%2$s] %3$s%n", record.getMillis(), record.getLoggerName(), record.getMessage());
            }
        });
        root.addHandler(consoleHandler);

        configured = true;
    }
}
