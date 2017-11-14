package com.ryanmichela.sshd;

import jline.TerminalSupport;

/**
 * Copyright 2013 Ryan Michela
 */
public class SshTerminal extends TerminalSupport {

    protected SshTerminal() {
        super(true);
    }

    @Override
    public void init() throws Exception {
        setAnsiSupported(true);
        setEchoEnabled(true);
    }
}
