package database.security;

import database.Utils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AccessData {

    private static final Logger log = Logger.getLogger(AccessData.class);

    private static final String mSessionKey = "9b2ea99482c753a3db97ae89a32062ae";
    private static final String properties = "config.properties";
    private static final Object lock = new Object();
    private static String filename;
    private static String environment = null;

    private static AccessData mInstance = null;


    private AccessData() {
        environment = getResource("environment");
        filename = "config." + environment + ".properties";
        log.info("environment "+environment+" filename "+filename);
    }

    public static AccessData getAccessInstance() {
        if (mInstance == null) {
            mInstance = new AccessData();
        }
        return mInstance;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getResource(String attribute) {

        synchronized (lock) {

            Properties prop = new Properties();
            InputStream input = null;
            String value = null;

            try {

                input = Utils.class.getClassLoader().getResourceAsStream(environment == null ? properties : filename);

                if (input == null) {

                    log.error("unable to find ressource file ");
                    return null;
                }

                prop.load(input);

                value = prop.getProperty(attribute);

            } catch (IOException ex) {

                log.error(ex,ex);
                ex.printStackTrace();

            } finally {

                if (input != null) {

                    try {

                        input.close();

                    } catch (IOException e) {

                        log.error(e,e);
                        e.printStackTrace();

                    }
                }
            }

            try {

                return (environment == null ? value : Blowfish.getBlowfish(mSessionKey).decrypt(value));

            } catch (Exception e) {

                log.error(e,e);
                e.printStackTrace();
                return null;

            }

        }
    }
}
