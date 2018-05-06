package com.ryanmichela.sshd;

import org.apache.sshd.common.SshException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Copyright 2013 Ryan Michela
 */
public class FlushyOutputStream extends OutputStream {

    private OutputStream base;
    private boolean isClosed = false;

    public FlushyOutputStream(OutputStream base) {
        this.base = base;
    }

    @Override
    public void write(int b) throws IOException {
        if (isClosed) return;
        base.write(b);
        base.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (isClosed) return;
        base.write(b);
        base.flush();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (isClosed) return;
        try {
            base.write(b, off, len);
            base.flush();
        } catch (SshException e) {
            if (!e.getMessage().contains("channel already closed")) throw e;
        }
    }

    @Override
    public void close() {
        isClosed = true;
    }
}
