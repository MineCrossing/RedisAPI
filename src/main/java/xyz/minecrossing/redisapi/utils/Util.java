package xyz.minecrossing.redisapi.utils;

public class Util {

    public static void runAsync(AsyncFunction asyncFunction) {
        new Thread(asyncFunction::run).start();
    }

}
