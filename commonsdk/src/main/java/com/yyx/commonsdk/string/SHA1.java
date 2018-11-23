package com.yyx.commonsdk.string;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.Formatter;

/**
 * Created by java on 2017/10/24.
 */

public class SHA1 {

    public static String hexdigest(String password)
    {
        String sha1 = "";
        try
        {
            if (TextUtils.isEmpty(password))
                return "";
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}