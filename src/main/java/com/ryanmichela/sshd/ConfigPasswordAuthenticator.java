package com.ryanmichela.sshd;

import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2013 Ryan Michela
 */
public class ConfigPasswordAuthenticator implements PasswordAuthenticator {
    private Map<String, Integer> failCounts = new HashMap<String, Integer>();

    @Override
    public boolean authenticate(String username, String password, ServerSession serverSession) {
        if (SshdPlugin.instance.getConfig().getString("credentials." + username).equals(password)) {
            failCounts.put(username, 0);
            return true;
        }
        SshdPlugin.instance.getLogger().info("Failed login for " + username + " using password authentication.");

        try {
            Thread.sleep(3000);
            if (failCounts.containsKey(username)) {
                failCounts.put(username, failCounts.get(username) + 1);
            } else {
                failCounts.put(username, 1);
            }
            if (failCounts.get(username) >= 3) {
                failCounts.put(username, 0);
                serverSession.close(true);
            }
        } catch (InterruptedException e) {
            // do nothing
        }
        return false;
    }
}
