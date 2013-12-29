package com.ryanmichela.sshd;

/**
 * Copyright 2013 Ryan Michela
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter {

    private SimpleDateFormat dateFormat;

    public ConsoleLogFormatter() {
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public String format(LogRecord logrecord) {
        StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append(" [");
        stringbuilder.append(this.dateFormat.format(Long.valueOf(logrecord.getMillis()))).append(" ");

        stringbuilder.append(logrecord.getLevel().getName()).append("]: ");
        stringbuilder.append(this.formatMessage(logrecord));
        stringbuilder.append('\n');
        Throwable throwable = logrecord.getThrown();

        if (throwable != null) {
            StringWriter stringwriter = new StringWriter();

            throwable.printStackTrace(new PrintWriter(stringwriter));
            stringbuilder.append(stringwriter.toString());
        }

        return stringbuilder.toString();
    }
}

