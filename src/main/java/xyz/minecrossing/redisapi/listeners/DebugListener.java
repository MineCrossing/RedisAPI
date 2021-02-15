package xyz.minecrossing.redisapi.listeners;

import xyz.minecrossing.coreutilities.Logger;
import xyz.minecrossing.redisapi.redis.RedisChannelListener;

public class DebugListener implements RedisChannelListener {

    /**
     * Debug listener for when a message is received on the "debug" channel
     *
     * @param message The message to print out
     */
    @Override
    public void messageReceived(String message) {
        Logger.debug(message);
    }
}
