package xyz.minecrossing.redisapi.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.minecrossing.coreutilities.Logger;

public class Redis {

    private final String ip, password;
    private final int port;

    private JedisPool pool = null;

    public Redis(String ip, int port, String password) {
        this.ip = ip;
        this.port = port;
        this.password = password;

        connect();
    }

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

    public Jedis getResource() {
        return pool.getResource();
    }

    public void shutdown() {
        if (!pool.isClosed()) pool.close();
    }
}
