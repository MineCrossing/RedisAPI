package xyz.minecrossing.redisapi.listeners;

import xyz.minecrossing.redisapi.redis.RedisChannelListener;

public class DebugListener implements RedisChannelListener {

    @Override
    public void messageReceived(String message) {
        System.out.println("[Redis Debug] " + message);
    }
}
