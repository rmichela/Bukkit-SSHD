package com.ryanmichela.sshd;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright 2013 Ryan Michela
 */
public class ConsoleCommandFactory implements CommandFactory {

    @Override
    public Command createCommand(String command) {
        return new ConsoleCommand(command);
    }

    public class ConsoleCommand implements Command {

        private String command;

        private InputStream in;
        private OutputStream out;
        private OutputStream err;
        private ExitCallback callback;

        public ConsoleCommand(String command) {
            this.command = command;
        }

        public void setInputStream(InputStream in) {
            this.in = in;
        }

        public void setOutputStream(OutputStream out) {
            this.out = out;
        }

        public void setErrorStream(OutputStream err) {
            this.err = err;
        }

        public void setExitCallback(ExitCallback callback) {
            this.callback = callback;
        }

        @Override
        public void start(Environment environment) throws IOException {
            try {
                SshdPlugin.instance.getLogger()
                        .info("[U: " + environment.getEnv().get(Environment.ENV_USER) + "] " + command);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } catch (Exception e) {
                SshdPlugin.instance.getLogger().severe("Error processing command from SSH -" + e.getMessage());
            } finally {
                callback.onExit(0);
            }
        }

        @Override
        public void destroy() {

        }
    }
}
