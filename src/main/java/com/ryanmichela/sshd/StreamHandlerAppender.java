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

        if(logEvent.getLevel().equals(org.apache.logging.log4j.Level.DEBUG)) {
            level = java.util.logging.Level.FINE;
        } else if(logEvent.getLevel().equals(org.apache.logging.log4j.Level.INFO)) {
            level = java.util.logging.Level.INFO;
        } else if(logEvent.getLevel().equals(org.apache.logging.log4j.Level.WARN)) {
            level = java.util.logging.Level.WARNING;
        } else if(logEvent.getLevel().equals(org.apache.logging.log4j.Level.ERROR)) {
            level = java.util.logging.Level.SEVERE;
        } else {
            level = java.util.logging.Level.INFO;
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
    public State getState() {
        return State.INITIALIZED;
    }

    @Override
    public void initialize() {

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

    @Override
    public boolean isStopped() {
        return false;
    }
}
