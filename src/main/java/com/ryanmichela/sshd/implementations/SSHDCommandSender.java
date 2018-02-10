package com.ryanmichela.sshd.implementations;

import com.ryanmichela.sshd.ConsoleShellFactory;
import com.ryanmichela.sshd.SshdPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.craftbukkit.v1_12_R1.command.ServerCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.conversations.ConversationTracker;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

public class SSHDCommandSender extends ServerCommandSender implements ConsoleCommandSender {

    private final ConversationTracker conversationTracker = new ConversationTracker();

    public void sendMessage(String message) {
        this.sendRawMessage(message);
    }

    public void sendRawMessage(String message) {
        try {
            ConsoleShellFactory.ConsoleShell.consoleReader.println(ChatColor.stripColor(message) + "\r");
        } catch (IOException e) {
            SshdPlugin.instance.getLogger().log(Level.SEVERE, "Error sending message to SSHDCommandSender", e);
        }
    }

    public void sendMessage(String[] messages) {
        Arrays.asList(messages).forEach(this::sendMessage);
    }

    public String getName() {
        return "SSHD CONSOLE";
    }

    public boolean isOp() {
        return true;
    }

    public void setOp(boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of server console");
    }

    public boolean beginConversation(Conversation conversation) {
        return this.conversationTracker.beginConversation(conversation);
    }

    public void abandonConversation(Conversation conversation) {
        this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
    }

    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        this.conversationTracker.abandonConversation(conversation, details);
    }

    public void acceptConversationInput(String input) {
        this.conversationTracker.acceptConversationInput(input);
    }

    public boolean isConversing() {
        return this.conversationTracker.isConversing();
    }

}
