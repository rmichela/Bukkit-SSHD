package org.slf4j.impl;

import com.ryanmichela.sshd.SshdPlugin;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.logging.Level;

/**
 * Copyright 2013 Ryan Michela
 */
public class PluginSlf4jFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return new PluginSlf4jAdapter(name);
    }

    public class PluginSlf4jAdapter implements Logger {
        private String name;

        private boolean isEnabled(Level level) {
            if (SshdPlugin.instance != null) {
                return SshdPlugin.instance.getLogger().isLoggable(level);
            }
            return false;
        }

        private void log(Level level, String s, Object[] objects) {
           if (SshdPlugin.instance != null && isEnabled(level)) {
               FormattingTuple ft = MessageFormatter.arrayFormat(s, objects);
               SshdPlugin.instance.getLogger().log(level, ft.getMessage(), ft.getThrowable());
               SshdPlugin.instance.getLogger().log(level, s, Thread.currentThread().getStackTrace());
           }
        }

        private void log(Level level, String s, Throwable throwable) {
            if (SshdPlugin.instance != null && isEnabled(level)) {
                SshdPlugin.instance.getLogger().log(level, s, throwable);
                SshdPlugin.instance.getLogger().log(level, s, Thread.currentThread().getStackTrace());
            }
        }

        public PluginSlf4jAdapter(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isTraceEnabled() {
            return isEnabled(Level.FINEST);
        }

        @Override
        public void trace(String s) {
            trace(s, new Object[]{});
        }

        @Override
        public void trace(String s, Object o) {
            trace(s, new Object[]{o});
        }

        @Override
        public void trace(String s, Object o, Object o1) {
            trace(s, new Object[]{o, o1});
        }

        @Override
        public void trace(String s, Object[] objects) {
            log(Level.FINEST, s, objects);
        }

        @Override
        public void trace(String s, Throwable throwable) {
            log(Level.FINEST, s, throwable);
        }

        @Override
        public boolean isTraceEnabled(Marker marker) {
            return isTraceEnabled();
        }

        @Override
        public void trace(Marker marker, String s) {
            trace(s);
        }

        @Override
        public void trace(Marker marker, String s, Object o) {
            trace(s, o);
        }

        @Override
        public void trace(Marker marker, String s, Object o, Object o1) {
            trace(s, o, o1);
        }

        @Override
        public void trace(Marker marker, String s, Object[] objects) {
            trace(s, objects);
        }

        @Override
        public void trace(Marker marker, String s, Throwable throwable) {
            trace(s, throwable);
        }

        @Override
        public boolean isDebugEnabled() {
            return isEnabled(Level.FINE);
        }

        @Override
        public void debug(String s) {
            debug(s, new Object[]{});
        }

        @Override
        public void debug(String s, Object o) {
            debug(s, new Object[]{o});
        }

        @Override
        public void debug(String s, Object o, Object o1) {
            debug(s, new Object[]{o, o1});
        }

        @Override
        public void debug(String s, Object[] objects) {
            log(Level.FINE, s, objects);
        }

        @Override
        public void debug(String s, Throwable throwable) {
            log(Level.FINE, s, throwable);
        }

        @Override
        public boolean isDebugEnabled(Marker marker) {
            return isDebugEnabled();
        }

        @Override
        public void debug(Marker marker, String s) {
            debug(s);
        }

        @Override
        public void debug(Marker marker, String s, Object o) {
            debug(s, o);
        }

        @Override
        public void debug(Marker marker, String s, Object o, Object o1) {
            debug(s, o, o1);
        }

        @Override
        public void debug(Marker marker, String s, Object[] objects) {
            debug(s, objects);
        }

        @Override
        public void debug(Marker marker, String s, Throwable throwable) {
            debug(s, throwable);
        }

        @Override
        public boolean isInfoEnabled() {
            return isEnabled(Level.INFO);
        }

        @Override
        public void info(String s) {
            info(s, new Object[]{});
        }

        @Override
        public void info(String s, Object o) {
            info(s, new Object[]{o});
        }

        @Override
        public void info(String s, Object o, Object o1) {
            info(s, new Object[]{o, o1});
        }

        @Override
        public void info(String s, Object[] objects) {
            log(Level.INFO, s, objects);
        }

        @Override
        public void info(String s, Throwable throwable) {
            log(Level.INFO, s, throwable);
        }

        @Override
        public boolean isInfoEnabled(Marker marker) {
            return isInfoEnabled();
        }

        @Override
        public void info(Marker marker, String s) {
            info(s);
        }

        @Override
        public void info(Marker marker, String s, Object o) {
            info(s, o);
        }

        @Override
        public void info(Marker marker, String s, Object o, Object o1) {
            info(s, o, o1);
        }

        @Override
        public void info(Marker marker, String s, Object[] objects) {
            info(s, objects);
        }

        @Override
        public void info(Marker marker, String s, Throwable throwable) {
            info(s, throwable);
        }

        @Override
        public boolean isWarnEnabled() {
            return isEnabled(Level.WARNING);
        }

        @Override
        public void warn(String s) {
            warn(s, new Object[]{});
        }

        @Override
        public void warn(String s, Object o) {
            warn(s, new Object[]{o});
        }

        @Override
        public void warn(String s, Object o, Object o1) {
            warn(s, new Object[]{o, o1});
        }

        @Override
        public void warn(String s, Object[] objects) {
            log(Level.WARNING, s, objects);
        }

        @Override
        public void warn(String s, Throwable throwable) {
            log(Level.WARNING, s, throwable);
        }

        @Override
        public boolean isWarnEnabled(Marker marker) {
            return isWarnEnabled();
        }

        @Override
        public void warn(Marker marker, String s) {
            warn(s);
        }

        @Override
        public void warn(Marker marker, String s, Object o) {
            warn(s, o);
        }

        @Override
        public void warn(Marker marker, String s, Object o, Object o1) {
            warn(s, o, o1);
        }

        @Override
        public void warn(Marker marker, String s, Object[] objects) {
            warn(s, objects);
        }

        @Override
        public void warn(Marker marker, String s, Throwable throwable) {
            warn(s, throwable);
        }

        @Override
        public boolean isErrorEnabled() {
            return isEnabled(Level.SEVERE);
        }

        @Override
        public void error(String s) {
            error(s, new Object[]{});
        }

        @Override
        public void error(String s, Object o) {
            error(s, new Object[]{o});
        }

        @Override
        public void error(String s, Object o, Object o1) {
            error(s, new Object[]{o, o1});
        }

        @Override
        public void error(String s, Object[] objects) {
            log(Level.SEVERE, s, objects);
        }

        @Override
        public void error(String s, Throwable throwable) {
            log(Level.SEVERE, s, throwable);
        }

        @Override
        public boolean isErrorEnabled(Marker marker) {
            return isErrorEnabled();
        }

        @Override
        public void error(Marker marker, String s) {
            error(s);
        }

        @Override
        public void error(Marker marker, String s, Object o) {
            error(s, o);
        }

        @Override
        public void error(Marker marker, String s, Object o, Object o1) {
            error(s, o, o1);
        }

        @Override
        public void error(Marker marker, String s, Object[] objects) {
            error(s, objects);
        }

        @Override
        public void error(Marker marker, String s, Throwable throwable) {
            error(s, throwable);
        }
    }
}
