package xyz.minecrossing.redisapi.redis;

public interface RedisChannelListener {

    /**
     * Method to call when a message is received from a Redis channel
     *
     * @param message The message received from Redis
     */
    void messageReceived(String message);
}
