package xyz.minecrossing.redisapi.utils;

public class ConnectionDetails {

    private final String ip, password;
    private final int port;

    public ConnectionDetails(String ip, int port, String password) {
        this.ip = ip;
        this.port = port;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }
}
