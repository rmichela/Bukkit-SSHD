package com.ryanmichela.sshd;

import org.apache.commons.codec.binary.Base64;

import java.io.Reader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Copyright 2013 Ryan Michela
 */
public class PemDecoder extends java.io.BufferedReader {
    private static final String BEGIN = "^-+\\s*BEGIN.+";
    private static final String END = "^-+\\s*END.+";
    private static final String COMMENT = "Comment:";

    public PemDecoder(Reader in) {
        super(in);
    }

    public PublicKey getPemBytes() throws Exception {
        StringBuffer b64 = new StringBuffer();

        String line = readLine();
        if (!line.matches(BEGIN)) {
            return null;
        }

        for(line = readLine(); line != null; line = readLine()) {
            if (!line.matches(END) && !line.startsWith(COMMENT)) {
                b64.append(line.trim());
            }
        }

        return decodePublicKey(b64.toString());
    }

    private byte[] bytes;
    private int pos;

    private PublicKey decodePublicKey(String keyLine) throws Exception {
        bytes = null;
        pos = 0;

        // look for the Base64 encoded part of the line to decode
        // both ssh-rsa and ssh-dss begin with "AAAA" due to the length bytes
        for (String part : keyLine.split(" ")) {
            if (part.startsWith("AAAA")) {
                bytes = Base64.decodeBase64(part.getBytes());
                break;
            }
        }
        if (bytes == null) {
            throw new IllegalArgumentException("no Base64 part to decode");
        }

        String type = decodeType();
        if (type.equals("ssh-rsa")) {
            BigInteger e = decodeBigInt();
            BigInteger m = decodeBigInt();
            RSAPublicKeySpec spec = new RSAPublicKeySpec(m, e);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } else if (type.equals("ssh-dss")) {
            BigInteger p = decodeBigInt();
            BigInteger q = decodeBigInt();
            BigInteger g = decodeBigInt();
            BigInteger y = decodeBigInt();
            DSAPublicKeySpec spec = new DSAPublicKeySpec(y, p, q, g);
            return KeyFactory.getInstance("DSA").generatePublic(spec);
        } else {
            throw new IllegalArgumentException("unknown type " + type);
        }
    }

    private String decodeType() {
        int len = decodeInt();
        String type = new String(bytes, pos, len);
        pos += len;
        return type;
    }

    private int decodeInt() {
        return ((bytes[pos++] & 0xFF) << 24) | ((bytes[pos++] & 0xFF) << 16)
                | ((bytes[pos++] & 0xFF) << 8) | (bytes[pos++] & 0xFF);
    }

    private BigInteger decodeBigInt() {
        int len = decodeInt();
        byte[] bigIntBytes = new byte[len];
        System.arraycopy(bytes, pos, bigIntBytes, 0, len);
        pos += len;
        return new BigInteger(bigIntBytes);
    }
}
