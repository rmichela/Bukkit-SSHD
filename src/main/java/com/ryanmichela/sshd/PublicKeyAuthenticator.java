package com.ryanmichela.sshd;

import org.apache.commons.lang.ArrayUtils;
import org.apache.sshd.server.auth.pubkey.PublickeyAuthenticator;
import org.apache.sshd.server.session.ServerSession;

import java.io.File;
import java.io.FileReader;
import java.security.PublicKey;

/**
 * Copyright 2013 Ryan Michela
 */
public class PublicKeyAuthenticator implements PublickeyAuthenticator {
    private File authorizedKeysDir;

    public PublicKeyAuthenticator(File authorizedKeysDir) {
        this.authorizedKeysDir = authorizedKeysDir;
    }

    @Override
    public boolean authenticate(String username, PublicKey key, ServerSession session) {
        byte[] keyBytes = key.getEncoded();
        File keyFile = new File(authorizedKeysDir, username);

        if (keyFile.exists()) {
            try {

                FileReader fr = new FileReader(keyFile);
                PemDecoder pd = new PemDecoder(fr);
                PublicKey k = pd.getPemBytes();
                pd.close();

                if (k != null) {
                    if (ArrayUtils.isEquals(key.getEncoded(), k.getEncoded())) {
                        return true;
                    }
                } else {
                    SshdPlugin.instance.getLogger().severe("Failed to parse PEM file. " + keyFile.getAbsolutePath());
                }
            } catch (Exception e) {
                SshdPlugin.instance.getLogger().severe("Failed to process public key " + keyFile.getAbsolutePath() + ". " + e.getMessage());
            }
        } else {
            SshdPlugin.instance.getLogger().warning("Could not locate public key for " + username + ". Make sure the user's key is named the same as their user name without a file extension.");
        }

        return false;
    }
}
