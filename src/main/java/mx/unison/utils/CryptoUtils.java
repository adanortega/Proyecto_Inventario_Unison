package mx.unison.utils;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class CryptoUtils {
    public static String md5(String input) {
        if (input == null) throw new RuntimeException("Input nulo");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error en cifrado", e);
        }
    }
}