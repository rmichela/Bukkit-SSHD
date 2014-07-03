package com.ryanmichela.sshd;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * Copyright 2014 Ryan Michela
 */
public class StreamHandlerAppender implements Appender {
    private StreamHandler streamHandler;
    private UUID uuid;

    public StreamHandlerAppender(StreamHandler streamHandler) {
        this.streamHandler = streamHandler;
        uuid = UUID.randomUUID();
    }

    @Override
    public void append(LogEvent logEvent) {
        java.util.logging.Level level;
        switch (logEvent.getLevel())
        {
            case DEBUG: level = java.util.logging.Level.FINE; break;
            case INFO: level = java.util.logging.Level.INFO; break;
            case WARN: level = java.util.logging.Level.WARNING; break;
            case ERROR: level = java.util.logging.Level.SEVERE; break;
            default: level = java.util.logging.Level.INFO; break;
        }

        String message = logEvent.getMessage().getFormattedMessage();


        LogRecord logRecord = new LogRecord(level, message);
        streamHandler.publish(logRecord);
    }

    @Override
    public String getName() {
        return "StreamHandlerAppender:" + uuid.toString();
    }

    @Override
    public Layout<? extends Serializable> getLayout() {
        return null;
    }

    @Override
    public boolean ignoreExceptions() {
        return false;
    }

    @Override
    public ErrorHandler getHandler() {
        return null;
    }

    @Override
    public void setHandler(ErrorHandler errorHandler) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return true;
    }
}
