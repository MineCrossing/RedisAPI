package xyz.minecrossing.redisapi.redis;

import redis.clients.jedis.JedisPubSub;

public class RedisPubSub extends JedisPubSub {

    private final RedisChannelListener listener;

    /**
     * Constructor to create a redis pub sub listener
     *
     * @param listener The listener channel to use
     */
    public RedisPubSub(RedisChannelListener listener) {
        this.listener = listener;
    }

    /**
     * Method for when a message is received
     *
     * @param channel The channel to send the message to
     * @param message The message to send
     */
    @Override
    public void onMessage(String channel, String message) {
        listener.messageReceived(message);
    }

    /**
     * Unsubscribe from a Jedis instance
     */
    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }
}
