package servlets.authentification.security;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

final class Aes {

    private static final Logger log = Logger.getLogger(Aes.class);

    private static final String mAlgorithm = "AES";
    private static final String mEncryptionKey = "e51ef23d5e9c18789794d114f56e59c7";
    private static final String mInitializationVector = "AODVNUASDNVVAOVF";
    private static final String mTransformation = "AES/CBC/pkcs5padding";
    private static final String mProvider = "SunJCE";
    private static final String mAuthentificationKey = "9b2ea99482c753a3db97ae89a32062ae";
    private static Aes mInstance = null;

    private Aes() {
    }

    static Aes getAes(String sessionKey) throws Exception {
        if (sessionKey.equalsIgnoreCase(mAuthentificationKey)) {
            if (mInstance == null) {
                mInstance = new Aes();
            }
            return mInstance;
        } else {
            log.fatal("TRYING TO CONNECT WITH BAD AUTHENTIFICATION KEY "+sessionKey);
            throw new Exception("TRYING TO CONNECT WITH BAD AUTHENTIFICATION KEY");
        }
    }

    String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(mTransformation, mProvider);
        SecretKeySpec key = new SecretKeySpec(mEncryptionKey.getBytes(StandardCharsets.UTF_8),
                mAlgorithm);

        cipher.init(Cipher.ENCRYPT_MODE,
                key,
                new IvParameterSpec(mInitializationVector.getBytes(StandardCharsets.UTF_8)));

        byte[] encryptedMsg = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().
                encodeToString(encryptedMsg);
    }

    String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(mTransformation, mProvider);
        SecretKeySpec key = new SecretKeySpec(mEncryptionKey.getBytes(StandardCharsets.UTF_8),
                mAlgorithm);

        cipher.init(Cipher.DECRYPT_MODE,
                key,
                new IvParameterSpec(mInitializationVector.getBytes(StandardCharsets.UTF_8)));

        byte[] base64Decrypted = Base64.getDecoder()
                .decode(encryptedData);

        return new String(cipher.doFinal(base64Decrypted),
                StandardCharsets.UTF_8);
    }


}
