package xyz.minecrossing.redisapi.listeners;

import xyz.minecrossing.coreutilities.Logger;
import xyz.minecrossing.redisapi.redis.RedisChannelListener;

public class DebugListener implements RedisChannelListener {

    @Override
    public void messageReceived(String message) {
        Logger.debug(message);
    }
}
