package xyz.minecrossing.redisapi;

import redis.clients.jedis.JedisPubSub;
import xyz.minecrossing.redisapi.listeners.DebugListener;
import xyz.minecrossing.redisapi.redis.Redis;
import xyz.minecrossing.redisapi.redis.RedisConnector;
import xyz.minecrossing.redisapi.utils.ConnectionDetails;
import xyz.minecrossing.redisapi.utils.ConnectionManager;
import xyz.minecrossing.redisapi.utils.RedisProperties;

public class RedisAPI implements ConnectionManager {

    private static RedisAPI redisAPI;

    private Redis redis;
    private RedisConnector redisConnector;

    @Override
    public void initialize() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.createProperties();

        ConnectionDetails details = redisProperties.loadProperties();
        if (details == null) {
            System.out.println("Failed to initialize RedisAPI - details failed to load!");
            return;
        }

        this.redis = new Redis(details.getIp(), details.getPort(), details.getPassword());

        redisConnector = new RedisConnector(redis);

        redisConnector.listenForChannel("debug", new DebugListener());
    }

    @Override
    public void shutdown() {
        for (JedisPubSub pubSub : RedisConnector.getInstance().getPubSubs()) {
            pubSub.unsubscribe();
        }
        redis.shutdown();
    }

    public RedisConnector getRedisConnector() {
        return redisConnector;
    }

    public static RedisAPI getRedisAPI() {
        if (redisAPI == null) redisAPI = new RedisAPI();
        return redisAPI;
    }
}
