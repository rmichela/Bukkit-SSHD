package com.ryanmichela.sshd;

/**
 * Copyright 2014 Ryan Michela
 */

import java.net.UnknownHostException;
import java.util.List;

public class NetworkAddressValidator {

    private CIDRUtils[] approvedAddressList = null;

    public NetworkAddressValidator(List<String> approvedAddressList) {
        this.approvedAddressList = new CIDRUtils[approvedAddressList.size()];
        for (int i = 0; i < approvedAddressList.size(); i++) {
            String whitelistEntry = approvedAddressList.get(i);
            try {
                if (approvedAddressList.get(i).indexOf("/") > 0) {
                    this.approvedAddressList[i] = new CIDRUtils(whitelistEntry);
                } else {
                    this.approvedAddressList[i] = new CIDRUtils(whitelistEntry + "/32");
                }
            } catch (UnknownHostException e) {
                SshdPlugin.instance.getLogger().severe(whitelistEntry + " is not a valid IPv4 or IPv6 address or CIDR formatted address.");
            }
        }
    }

    public boolean isApproved(String ipAddress) {
        try {
            for (CIDRUtils approvedAddress : approvedAddressList) {
                if (approvedAddress.isInRange(ipAddress)) {
                    return true;
                }
            }
            return false;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
