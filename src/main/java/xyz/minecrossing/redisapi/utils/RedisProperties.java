package xyz.minecrossing.redisapi.utils;

import java.io.*;
import java.util.Properties;

public class RedisProperties {

    private final String FILE_PATH = "src/main/resources/";
    private final String FILE_NAME = "redis.properties";
    private final String FILE = FILE_PATH + FILE_NAME;

    /**
     * Create the database properties if they dont exist
     *
     * @return <code>true</code> if the file was created; <code>false</code> otherwise
     */
    public boolean createProperties() {
        if (!new File(FILE).exists()) {
            try (OutputStream output = new FileOutputStream(FILE)) {
                Properties properties = new Properties();
                properties.setProperty("ip", "127.0.0.1");
                properties.setProperty("port", "6379");
                properties.setProperty("password", "");

                properties.store(output, null);

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Load the connection details from the storage medium
     *
     * @return The database connection details
     */
    public ConnectionDetails loadProperties() {
        try (InputStream input = new FileInputStream(FILE)) {
            Properties properties = new Properties();
            properties.load(input);

            return new ConnectionDetails(
                    properties.getProperty("ip"),
                    Integer.parseInt(properties.getProperty("port")),
                    properties.getProperty("password")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
