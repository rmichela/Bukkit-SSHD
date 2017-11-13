package com.ryanmichela.sshd;

import org.apache.sshd.common.SshException;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;


import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.*;

/**
 * Copyright 2013 Ryan Michela
 */
public class FlushyStreamHandler extends StreamHandler {
    private ConsoleReader reader;

    public FlushyStreamHandler(OutputStream out, Formatter formatter, ConsoleReader reader) {
        super(out, formatter);
        this.reader = reader;
        setLevel(Level.INFO);
    }

    @Override
    public synchronized void publish(LogRecord record) {
        record.setMessage(record.getMessage().replace("\n", "\n\r"));
        super.publish(record);
        flush();
    }

    @Override
    public synchronized void flush() {
        try {
            reader.print(ConsoleReader.RESET_LINE + "");
            reader.flush();
            super.flush();
            try {
                reader.drawLine();
            } catch (Throwable ex) {
                reader.getCursorBuffer().clear();
            }
            reader.flush();
            super.flush();
        } catch (SshException ex) {
            // do nothing
        } catch (IOException ex) {
            Logger.getLogger(FlushyStreamHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
