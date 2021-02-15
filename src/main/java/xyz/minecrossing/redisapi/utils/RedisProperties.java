package xyz.minecrossing.redisapi.utils;

import xyz.minecrossing.coreutilities.io.PropertyLoader;
import xyz.minecrossing.coreutilities.remote.ConnectionDetails;

import java.io.*;
import java.util.Properties;

public class RedisProperties implements PropertyLoader {

    private final String FILE_PATH = "src/main/resources/";
    private final String FILE_NAME = "redis.properties";
    private final String FILE = FILE_PATH + FILE_NAME;

    /**
     * Create the redis connection properties if they dont exist
     */
    @Override
    public void createProperties() {
        if (!new File(FILE).exists()) {
            try (OutputStream output = new FileOutputStream(FILE)) {
                Properties properties = new Properties();
                properties.setProperty("ip", "127.0.0.1");
                properties.setProperty("port", "6379");
                properties.setProperty("password", "");

                properties.store(output, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load the connection details from the storage medium
     *
     * @return The redis connection details
     */
    @Override
    public ConnectionDetails loadProperties() {
        try (InputStream input = new FileInputStream(FILE)) {
            Properties properties = new Properties();
            properties.load(input);

            return new ConnectionDetails(
                    properties.getProperty("ip"),
                    Integer.parseInt(properties.getProperty("port")),
                    "",
                    properties.getProperty("password")

            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
