package com.znz.compass.znzlibray.utils;

import android.annotation.SuppressLint;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密工具包
 *
 * @author Jun
 */
@SuppressLint("NewApi")
public class AESUtil {
    /**
     * 加密
     *
     * @param text
     * @param key
     * @return
     */
    public static String encrypt(String text, String key) {

        String result = "";

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("utf-8");
            int len = b.length;
            if (len > keyBytes.length)
                len = keyBytes.length;
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] results = cipher.doFinal(text.getBytes("utf-8"));
            result = Base64Util.encode(results);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密
     *
     * @param text
     * @param key
     * @return
     */
    public static String decrypt(String text, String key) {
        String result = "";
        if (!StringUtil.isBlank(text)) {
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                byte[] keyBytes = new byte[16];
                byte[] b = key.getBytes("utf-8");
                int len = b.length;
                if (len > keyBytes.length)
                    len = keyBytes.length;
                System.arraycopy(b, 0, keyBytes, 0, len);
                SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);

                byte[] results = cipher.doFinal(Base64Util.decode(text));
                result = new String(results, "utf-8");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}