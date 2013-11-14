package com.ryanmichela.sshd;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Copyright 2013 Ryan Michela
 */
public class FlushyOutputStream extends OutputStream {
    private OutputStream base;

    public FlushyOutputStream(OutputStream base) {
        this.base = base;
    }

    @Override
    public void write(int b) throws IOException {
        base.write(b);
        base.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        base.write(b);
        base.flush();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        base.write(b, off, len);
        base.flush();
    }
}
