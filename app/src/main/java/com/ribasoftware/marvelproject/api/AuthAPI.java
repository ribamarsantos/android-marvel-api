package com.ribasoftware.marvelproject.api;

import com.ribasoftware.marvelproject.util.FBRemoteConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ribamar on 16/09/16.
 */
public class AuthAPI {

    private static final String KEY_PRIVATE = "KEY_PRIVATE";
    private static final String KEY_PUBLIC = "KEY_PUBLIC";

    private String publicKey;
    private String privateKey;
    private String timeStamp;
    private String md5Key;
    private FBRemoteConfig config;


    public AuthAPI(){
        timeStamp  = String.valueOf(System.currentTimeMillis());
        config     = new FBRemoteConfig();
        privateKey = "fb1528db57fa6be41901543020a07f322135fbda";//config.getKeyValue(KEY_PRIVATE);
        publicKey  = "811ea1386983b5d67115228399660d1e";//config.getKeyValue(KEY_PUBLIC);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public String getMd5Key() {

        String hash =null;
        String input = getTimeStamp() + getPrivateKey() + getPublicKey() ;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte [] md5Bytes = md.digest(input.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (int i = 0; i < md5Bytes.length; ++i) {
                md5.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1, 3));
            }
            md5Key = md5.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Key;
    }

}
