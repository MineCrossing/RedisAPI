# RedisAPI
RedisAPI for communication between the MineCrossing website and game server

## Maven Repository
To use RedisAPI add these into your pom.xml
```xml
<repositories>
    ...
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
</repositories>

<dependencies>
    ...
    <dependency>
        <groupId>com.github.MineCrossing</groupId>
        <artifactId>RedisAPI</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

#### Example Usage
Note: Redis calls should always be done asynchronously to avoid blocking the main thread.
See `CoreUtilities#getTaskManager`
```java
Jedis jedis = RedisConnector.getInstance().getConnection();
// Key, Value
jedis.get("test"); // Returns value, "hello"
jedis.set("key", "value"); // Sets a key, value storage

// Using Lists
jedis.lpush("queue#players", "Player1", "Player2"); // Add 2 players to list
jedis.lpush("queue#players", "Player3"); // Add 1 player to list

String player = jedis.rpop("queue#players"); // Returns "Player1"
```

#### Publishing and Subscribing
When listening (subscribing) to a specific channel you need to use a RedisChannelListener and register it.
```java
RedisConnector.getInstance().listenForChannel("channelName", new RedisChannelListener());
```

Example class for a RedisChannelListener
```java
public class ChannelListener implements RedisChannelListener {

    @Override
    public void messageReceived(String message) {
        System.out.println("Received message: " + message);
    }

}
```

When publishing it is a very simple process of sending out a message (or serialised data)
```java
RedisConnector.getInstance().getConnection().publish("debug", "test");
```

##### Debugging
RedisAPI has some light debugging available, it will always listen to the debug channel and you can publish messages there for testing like the example above.