package pl.ratemyrestaurant.utils;

import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class SecurityUtils {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return toBase64(bytes);
    }

    //TODO swith to JBCrypt
    public static String generatePasswordHash(String password, String salt) {
        String saltedPass = salt + password;
        return DigestUtils.sha256Hex(saltedPass.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean isPasswordMatch(String urerPassword, String userSalt, String hashToVerify) {
        if (urerPassword != null && hashToVerify != null && userSalt != null) {
            String regeneratedHash = generatePasswordHash(urerPassword, userSalt);
            return regeneratedHash.equals(hashToVerify);
        }
        throw new IllegalArgumentException("One of arguments is null");
    }

    private static byte[] fromBase64(String hex)
            throws IllegalArgumentException {
        return DatatypeConverter.parseBase64Binary(hex);
    }

    private static String toBase64(byte[] array) {
        return DatatypeConverter.printBase64Binary(array);
    }
}
