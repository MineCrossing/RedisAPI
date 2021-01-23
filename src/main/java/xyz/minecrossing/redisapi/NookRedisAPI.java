package xyz.minecrossing.redisapi;

public class NookRedisAPI implements RedisAPI {

    private static NookRedisAPI redisAPI;

    @Override
    public void initialize() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public RedisAPI getRedisAPI() {
        if (redisAPI == null) {
            redisAPI = new NookRedisAPI();
        }
        return redisAPI;
    }
}
