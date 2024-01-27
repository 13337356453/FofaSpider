package org.manlu.tools;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class B64 {
    public static String b64encode(String data){
        Base64.Encoder encoder=Base64.getEncoder();
        return encoder.encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }
    public static String b64decode(String data){
        Base64.Decoder decoder=Base64.getDecoder();
        return new String(decoder.decode(data),StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String data="123456";
        String s1=b64encode(data);
        System.out.println(s1);
        String s2=b64decode(s1);
        System.out.println(s2);
    }
}
