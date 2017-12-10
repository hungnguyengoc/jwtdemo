package example.springjwt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAHashing {
    public static final String toSha256(String input) throws NoSuchAlgorithmException {
        if (input == null) {
            return null;
        }

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
        byte byteData[] = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return sb.toString();
    }
}
