package com.ryanmichela.sshd;

import org.apache.sshd.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Copyright 2013 Ryan Michela
 */
public class SshdPlugin extends JavaPlugin {
    private SshServer sshd;
    public static SshdPlugin instance;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        File authorizedKeys = new File(getDataFolder(), "authorized_keys");
        if (!authorizedKeys.exists()) {
            authorizedKeys.mkdirs();
        }

        // Don't go any lower than INFO or SSHD will cause a stack overflow exception.
        // SSHD will log that it wrote bites to the output stream, which writes
        // bytes to the output stream - ad nauseaum.
        getLogger().setLevel(Level.INFO);
    }

    @Override
    public void onEnable() {
        instance = this;

        sshd = SshServer.setUpDefaultServer();
        sshd.setPort(getConfig().getInt("port", 22));

        File hostKey = new File(getDataFolder(), "hostkey");
        File authorizedKeys = new File(getDataFolder(), "authorized_keys");

        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(hostKey.getPath()));
        sshd.setShellFactory(new ConsoleShellFactory());
        sshd.setPasswordAuthenticator(new ConfigPasswordAuthenticator());
        sshd.setPublickeyAuthenticator(new PublicKeyAuthenticator(authorizedKeys));
        sshd.setCommandFactory(new ConsoleCommandFactory());
        try {
            sshd.start();
        } catch (IOException e) {
            getLogger().severe("Failed to start SSH server! " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        try {
            sshd.stop();
        } catch (Exception e) {
            // do nothing
        }
    }
}
