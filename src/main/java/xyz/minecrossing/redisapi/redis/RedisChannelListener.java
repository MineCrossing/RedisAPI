package xyz.minecrossing.redisapi.redis;

public interface RedisChannelListener {

    void messageReceived(String message);
}
