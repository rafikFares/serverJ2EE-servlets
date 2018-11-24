package servlets.authentification.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import database.adaptator.ObjectAdaptator;
import database.res.Student;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import servlets.authentification.prevented.BlackList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static database.Utils.STUDENT;

public final class TokenManager {

    private static final Logger log = Logger.getLogger(TokenManager.class);

    private static final String ISSUER = "auth0";
    private static final String SECRET = "20956794e3bb59fb8c412c9ca107d571";
    private static final String mSessionKey = "9b2ea99482c753a3db97ae89a32062ae";
    private static final long TEN_HOURS = 36000000;
    private static final Object lock = new Object();


    public static String makeJwt(String cryptedStudent) {

        try {

            String decryptedStudent = Aes.getAes(mSessionKey)
                    .decrypt(cryptedStudent);

            Student student = (Student) ObjectAdaptator
                    .fromJson(decryptedStudent, STUDENT);

            if (BlackList.getInstance().isBlacklisted(student.getIne())){ // student is banned
                log.info("student banned trying to connect"+student.toString());
                return null;
            }else {                                             // student is NOT banned
                return generateToken(student.getIne(),
                        ObjectAdaptator.toMap(student));
            }

        } catch (Exception e) {
            log.error(e,e);
            e.printStackTrace();
            return null;
        }

    }

    public static Boolean verifyToken(String jwt) {
        try {

            return getTokenClaims(jwt)
                    .get("iss").asString()
                    .equalsIgnoreCase(ISSUER);

        } catch (Exception ex) {
            log.error(ex,ex);
            ex.printStackTrace();
            return false;
        }
    }

    public static String getUserIne(String token) {
        synchronized (lock) {
            try {

                return verifyToken(token) ? getTokenClaims(token)
                        .get("ine").asString() : null;

            } catch (Exception e) {
                log.error(e,e);
                e.printStackTrace();
                return null;
            }
        }
    }

    private static String generateToken(String userId, Map<String, String> claims)
            throws JWTCreationException {

        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        Builder builder = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(userId)
                .withExpiresAt(new Date(new Date().getTime() + TEN_HOURS));

        claims.forEach((String key, String value) -> {
            builder.withClaim(key, value);
        });

        return builder.sign(algorithm);
    }

    private static Map<String, Claim> getTokenClaims(String token)
            throws JWTVerificationException {

        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaims();

    }

    private static Map<String, String> decodeToken(String token)
            throws JWTVerificationException {

        Map<String, String> result = new HashMap<>();

        String[] split_string = token.split("\\.");
        String b64Header = split_string[0];
        String b64Body = split_string[1];
        String b64Signature = split_string[2];

        Base64 base64 = new Base64(true);

        result.put("header", new String(base64.decode(b64Header)));

        result.put("payload", new String(base64.decode(b64Body)));

        return result;

    }

}