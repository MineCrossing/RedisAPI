package xyz.minecrossing.redisapi.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.minecrossing.coreutilities.Logger;

public class Redis {

    private final String ip, password;
    private final int port;

    private JedisPool pool = null;

    /**
     * Constructor to create a Redis class containing connection information
     *
     * @param ip       The IP of the Redis server
     * @param port     The port of the Redis server
     * @param password The password for the Redis server
     */
    public Redis(String ip, int port, String password) {
        this.ip = ip;
        this.port = port;
        this.password = password;

        connect();
    }

    /**
     * Connect to the Redis connection pool of the remote server
     */
    private void connect() {
        // create connection
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.MAX_VALUE);
        // may have to set max idle connections in future // correction its max idle time
        config.setMaxIdle(0);
        if (password == null || password.contentEquals("")) {
            pool = new JedisPool(config, ip, port, 0);
        } else {
            pool = new JedisPool(config, ip, port, 0, password);
        }

        // test connection
        try (Jedis j = pool.getResource()) {
            Logger.info("Connected to Redis!");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Failed to connect to Redis...");
        }
    }

    /**
     * Get the Jedis resource
     *
     * @return The Jedis resource
     */
    public Jedis getResource() {
        return pool.getResource();
    }

    /**
     * Check if the redis pool is not closed then close the connection pool
     */
    public void shutdown() {
        if (!pool.isClosed()) pool.close();
    }
}
