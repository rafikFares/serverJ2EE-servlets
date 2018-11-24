package database.security;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

final class Blowfish {

    private static final Logger log = Logger.getLogger(Blowfish.class);

    private static final String mAlgorithm = "Blowfish";
    private static final String mEncryptionKey = "e51ef23d5e9c18789794d114f56e59c7";
    private static final String mAuthentificationKey = "9b2ea99482c753a3db97ae89a32062ae";
    private static Blowfish mInstance = null;

    private Blowfish() {
    }

    static Blowfish getBlowfish(String sessionKey) throws Exception {
        if (sessionKey.equalsIgnoreCase(mAuthentificationKey)){
            if (mInstance == null){
                mInstance = new Blowfish();
            }
            return mInstance;
        }else {
            log.fatal("TRYING TO CONNECT WITH BAD AUTHENTIFICATION KEY "+sessionKey);
            throw new Exception("TRYING TO CONNECT WITH BAD AUTHENTIFICATION KEY");
        }
    }

    String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(mEncryptionKey.getBytes(), //"utf-8"
                mAlgorithm);
        Cipher cipher = Cipher.getInstance(mAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE,
                secretKey);
        byte[] hasil = cipher.doFinal(data.getBytes());  //"utf-8"

        return Base64.getEncoder().encodeToString(hasil);
    }

    String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(mEncryptionKey.getBytes(),  //"utf-8"
                mAlgorithm);
        Cipher cipher = Cipher.getInstance(mAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE,
                secretKey);
        byte[] hasil = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return new String(hasil);
    }

}
