package org.manlu.tools;

import java.util.Base64;

public class Cipher {
    static String key = "ebVwSIQP]eUfnqKc";
    static int n_key = 0;
    static int num = 21;

    private static void getKey() {
        for (int i = 0; i < key.length(); i++) {
            n_key += key.charAt(i);
        }
    }

    public static String encrypt(String text) {
        getKey();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int n = text.charAt(i);
            result.append((char) ((n + n_key) * num));
        }
        n_key = 0;
        String s = result.toString();
        s = Base64.getEncoder().encodeToString(s.getBytes());
        return s;
    }

    public static String decrypt(String text) {
        getKey();
        byte[] a = Base64.getMimeDecoder().decode(text);
        text = new String(a);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append((char) ((text.charAt(i) / num - n_key)));
//            result.append((char) ((text.charAt(i) - 0) / num - n_key));

        }
        n_key = 0;
        return result.toString();
    }

    public static void main(String[] args) {
        String s= Cipher.encrypt("email=1571120423@qq.com\nkey=0c81f6b2e3d0aa8be591d60c05653f30");
        System.out.println(s);
        String a= Cipher.decrypt("56+J6I2A6I+o6Ius6I6U6I+T57+457u8572Q572657u857u857yR57un57y757yR57ym6IC36JC86JC857q96IyW6JCS6I+o56+J6I6+6I2A6JOk57+46Iyr6Ius6I2A6Ius6I2V57ym576k57un57y76Iyr57yR57un6IyB57u857u857y757ym572657u85726576P6I2A57266IyW6I2A572l6Ius6Ius6I2V576k6IyW6Ius56+J6IyW6JCS6JCS6I6+6I6U6I2A57+456+J6Iyr6IyB6IuC6I2/6JCS6JGm6JG757+457u857yR572657q957un57q957un57q957u856+J6Iyr6IyB6IuC6JCn6JCS6JGR6JG757+457ym57ym57un572l56+J6Iyr6IyB6IuC6JKQ6I+96Ius6I+o6I2A57+46JGR6JCS6JCS6JG756+J6Iyr6IyB6IuC6JCn6JK66Iyr57+46JGR6JCS6JCS6JG756+J6Iyr6IyB6IuC6Iyr6IyB57+46JKQ6JGR6I+T6JGm56+J");

        System.out.println(a);
        System.out.println(Cipher.decrypt("56+J6IyW6JCS6JCS6I6+6I6U6I2A57+4"));
    }
}
