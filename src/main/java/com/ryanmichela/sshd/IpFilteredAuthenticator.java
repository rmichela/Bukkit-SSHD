package com.ryanmichela.sshd;

import org.apache.sshd.server.session.ServerSession;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Copyright 2014 Ryan Michela
 */
public class IpFilteredAuthenticator {
    private NetworkAddressValidator addressValidator;

    public IpFilteredAuthenticator() {
        List<String> whitelist = SshdPlugin.instance.getConfig().getStringList("whitelist");
        if (whitelist.size() > 0) {
            addressValidator = new NetworkAddressValidator(whitelist);
        }
    }

    public boolean ipAddressIsApproved(ServerSession serverSession) {
        if (addressValidator != null) {
            String ip = ((InetSocketAddress)serverSession.getIoSession().getRemoteAddress()).getAddress().getHostAddress();
            return addressValidator.isApproved(ip);
        }
        return true;
    }
}
