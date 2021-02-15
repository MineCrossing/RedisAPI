package xyz.minecrossing.redisapi.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import xyz.minecrossing.coreutilities.CoreUtilities;
import xyz.minecrossing.redisapi.RedisAPI;

import java.util.ArrayList;

public class RedisConnector {

    private final Redis redis;
    private final ArrayList<JedisPubSub> pubSubs;

    /**
     * Constructor to create a redis connector class
     *
     * @param redis The Redis information to store
     */
    public RedisConnector(Redis redis) {
        this.redis = redis;
        this.pubSubs = new ArrayList<>();
    }

    /**
     * Get the Jedis connection resource
     *
     * @return The Jedis connection resource
     */
    public Jedis getConnection() {
        return redis.getResource();
    }

    /**
     * Get the singleton instance of the Redis connector
     *
     * @return A singleton instance of the Redis connector
     */
    public static RedisConnector getInstance() {
        return RedisAPI.getRedisAPI().getRedisConnector();
    }

    /**
     * Get an array of Jedis pubsub listeners
     *
     * @return An array of Jedis pubsub listeners
     */
    public ArrayList<JedisPubSub> getPubSubs() {
        return pubSubs;
    }

    /**
     * Listen to a specific channel for published messages
     *
     * @param channel  The channel to listen to
     * @param listener The redis listener class
     */
    public void listenForChannel(String channel, RedisChannelListener listener) {
        CoreUtilities.getTaskManager().runAsync(() -> {
            try (Jedis j = redis.getResource()) {
                RedisPubSub redisPubSub = new RedisPubSub(listener);
                pubSubs.add(redisPubSub);
                j.subscribe(redisPubSub, channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
