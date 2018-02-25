package com.ryanmichela.sshd.implementations;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;

import java.util.LinkedList;
import java.util.logging.Level;

public class SSHDConversationTracker {
    private LinkedList<Conversation> conversationQueue = new LinkedList<>();

    synchronized boolean beginConversation(Conversation conversation) {
        if (!this.conversationQueue.contains(conversation)) {
            this.conversationQueue.addLast(conversation);
            if (this.conversationQueue.getFirst() == conversation) {
                conversation.begin();
                conversation.outputNextPrompt();
                return true;
            }
        }

        return true;
    }

    synchronized void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        if (!this.conversationQueue.isEmpty()) {
            if (this.conversationQueue.getFirst() == conversation) {
                conversation.abandon(details);
            }

            if (this.conversationQueue.contains(conversation)) {
                this.conversationQueue.remove(conversation);
            }

            if (!this.conversationQueue.isEmpty()) {
                this.conversationQueue.getFirst().outputNextPrompt();
            }
        }

    }

    public synchronized void abandonAllConversations() {
        LinkedList<Conversation> oldQueue = this.conversationQueue;
        this.conversationQueue = new LinkedList<>();

        for (Conversation conversation : oldQueue) {
            try {
                conversation.abandon(new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
            } catch (Throwable var5) {
                Bukkit.getLogger().log(Level.SEVERE, "Unexpected exception while abandoning a conversation", var5);
            }
        }

    }

    synchronized void acceptConversationInput(String input) {
        if (this.isConversing()) {
            Conversation conversation = this.conversationQueue.getFirst();

            try {
                conversation.acceptInput(input);
            } catch (Throwable var4) {
                conversation.getContext().getPlugin().getLogger().log(Level.WARNING, String.format("Plugin %s generated an exception whilst handling conversation input", conversation.getContext().getPlugin().getDescription().getFullName()), var4);
            }
        }

    }

    synchronized boolean isConversing() {
        return !this.conversationQueue.isEmpty();
    }

    public synchronized boolean isConversingModaly() {
        return this.isConversing() && this.conversationQueue.getFirst().isModal();
    }
}
