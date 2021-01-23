package xyz.minecrossing.redisapi.redis;

import redis.clients.jedis.JedisPubSub;

public class RedisPubSub extends JedisPubSub {

    private final RedisChannelListener listener;

    public RedisPubSub(RedisChannelListener listener) {
        this.listener = listener;
    }

    @Override
    public void onMessage(String channel, String message) {
        listener.messageReceived(message);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }
}
