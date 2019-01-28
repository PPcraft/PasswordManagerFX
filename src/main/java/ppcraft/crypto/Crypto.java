package ppcraft.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

import static ppcraft.main.Main.pass;

public class Crypto {
    private static StringBuilder builder = new StringBuilder();

    public static String md5Crypto(){
        String md5Hex = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(pass.getBytes(),0,pass.length());
            md5Hex = (new BigInteger(1,m.digest()).toString(16));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException :" + e);
        }
        return md5Hex;
    }

    public static String encrypt (String log){
        String crypto;
        crypto = toAscii(log);
        char[] summKey = keySumm(pass);
        byte[] sourceArray = new byte[crypto.length()];
        try {
            byte[] textByte = crypto.getBytes("UTF-8");
            int k = 0;
            for (int i = 0; i < sourceArray.length; i++) {
                sourceArray[i] = (byte) (textByte[i] ^ summKey[k]);
                if (k<3){
                    k++;
                }else {
                    k = 0;
                }
            }
            crypto = new String(sourceArray, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException :" + e);
        }
        return crypto;
    }

    public static String decrypt (String log) {
        String crypto = "";
        char[] summKey = keySumm(pass);
        byte[] sourceArray = new byte[log.length()];
        try {
            byte[] textByte = log.getBytes("UTF-8");
            int k = 0;
            for (int i = 0; i < sourceArray.length; i++) {
                sourceArray[i] = (byte) (textByte[i] ^ summKey[k]);
                if (k<3){
                    k++;
                }else {
                    k = 0;
                }
            }
            crypto = fromAscii(new String(sourceArray, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return crypto;
    }

    private static String toAscii (String log){
        String crypto;
        builder.delete(0,builder.length());
        int ascii;
        char[] chars = log.toCharArray();
        for (int i = 0; i < log.length(); i++){
            ascii = (int)chars[i];
            if (ascii >= 1000){
                builder.append(ascii);
            }else if (ascii >= 100){
                builder.append("0" + ascii);
            }else if (ascii >= 10){
                builder.append("00" + ascii);
            }else{
                builder.append("000" + ascii);
            }
        }
        crypto = builder.toString();
        return crypto;
    }

    private static String fromAscii (String log) {
        String crypto;
        builder.delete(0,builder.length());
        int ascii;
        char[] chars = log.toCharArray();
        for (int i = 0; i < log.length(); i += 4) {
            ascii = Integer.parseInt(chars[i] + "" + chars[i + 1] + "" + chars[i + 2] + "" + chars[i + 3]);
            builder.append((char) ascii);
        }
        crypto = builder.toString();
        return crypto;
    }

    private static char[] keySumm(String key){
        String keyCrypto = toAscii(key);
        char[] keyChar = keyCrypto.toCharArray();
        int summ = 0;
        for (int i = 0; i < keyChar.length; i++){
            summ += Character.getNumericValue(keyChar[i]);
            if (summ >= 10){
                summ -=10;
            }
        }
        char[] chars = new char[4];
        chars[0] = (char) (summ + '0');
        if (summ < 5){
            chars[1] = (char) ((summ + 5) + '0');
            chars[2] = (char) ((summ + 2) + '0');
            chars[3] = (char) ((summ + 3) + '0');
        }else {
            chars[1] = (char) ((summ - 5) + '0');
            chars[2] = (char) ((summ - 1) + '0');
            chars[3] = (char) ((summ - 4) + '0');
        }
        return chars;
    }
}