package xyz.minecrossing.redisapi;

import redis.clients.jedis.JedisPubSub;
import xyz.minecrossing.coreutilities.Logger;
import xyz.minecrossing.coreutilities.remote.ConnectionDetails;
import xyz.minecrossing.coreutilities.remote.ConnectionManager;
import xyz.minecrossing.redisapi.listeners.DebugListener;
import xyz.minecrossing.redisapi.redis.Redis;
import xyz.minecrossing.redisapi.redis.RedisConnector;
import xyz.minecrossing.redisapi.utils.RedisProperties;

public class RedisAPI implements ConnectionManager {

    private static RedisAPI redisAPI;

    private Redis redis;
    private RedisConnector redisConnector;

    /**
     * Initialize the Redis connection and listen for the debug channel
     */
    @Override
    public void initialize() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.createProperties();

        ConnectionDetails details = redisProperties.loadProperties();
        if (details == null) {
            Logger.error("Failed to initialize RedisAPI - details failed to load!");
            return;
        }

        this.redis = new Redis(details.getHostname(), details.getPort(), details.getPassword());

        redisConnector = new RedisConnector(redis);

        redisConnector.listenForChannel("debug", new DebugListener());
    }

    /**
     * Shutdown the Redis connection to stop any leaks
     */
    @Override
    public void shutdown() {
        for (JedisPubSub pubSub : RedisConnector.getInstance().getPubSubs()) {
            pubSub.unsubscribe();
        }
        redis.shutdown();
    }

    /**
     * Get an instance of the Redis connector
     *
     * @return An instance of the Redis connector
     */
    public RedisConnector getRedisConnector() {
        return redisConnector;
    }

    /**
     * Get the Redis API class
     *
     * @return The Redis API class
     */
    public static RedisAPI getRedisAPI() {
        if (redisAPI == null) redisAPI = new RedisAPI();
        return redisAPI;
    }
}
