package xyz.minecrossing.redisapi.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import xyz.minecrossing.coreutilities.CoreUtilities;
import xyz.minecrossing.redisapi.RedisAPI;

import java.util.ArrayList;

public class RedisConnector {

    private final Redis redis;
    private final ArrayList<JedisPubSub> pubSubs;

    public RedisConnector(Redis redis) {
        this.redis = redis;
        this.pubSubs = new ArrayList<>();
    }

    public Jedis getConnection() {
        return redis.getResource();
    }

    public static RedisConnector getInstance() {
        return RedisAPI.getRedisAPI().getRedisConnector();
    }

    public ArrayList<JedisPubSub> getPubSubs() {
        return pubSubs;
    }

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
