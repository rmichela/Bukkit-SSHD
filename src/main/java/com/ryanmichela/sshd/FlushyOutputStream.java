package com.ryanmichela.sshd;

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
        if(isClosed) return;
        base.write(b);
        base.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        if(isClosed) return;
        base.write(b);
        base.flush();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if(isClosed) return;
        base.write(b, off, len);
        base.flush();
    }

    @Override
    public void close() {
        isClosed = true;
    }
}
