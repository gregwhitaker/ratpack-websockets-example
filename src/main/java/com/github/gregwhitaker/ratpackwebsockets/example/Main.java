package com.github.gregwhitaker.ratpackwebsockets.example;

import com.github.gregwhitaker.ratpackwebsockets.example.core.UserConnectHandler;
import com.github.gregwhitaker.ratpackwebsockets.example.core.UserEventHandler;
import com.github.gregwhitaker.ratpackwebsockets.example.handler.UserEventStreams;
import ratpack.rx.RxRatpack;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

/**
 * Starts the Ratpack Websockets Demo.
 */
public class Main {

    /**
     * Main entry-point of the application.
     *
     * @param args command line arguments
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        RxRatpack.initialize();

        RatpackServer.start(
                s -> s.serverConfig(
                        c -> c.baseDir(BaseDir.find()))
                                    .registryOf(registry -> {
                                        registry.add(new UserEventStreams());
                                    })
                                    .handlers(chain ->
                                            chain
                                                .post("demo/connect", UserConnectHandler.class)
                                                .get("demo/events", UserEventHandler.class)
                                                .files(f -> f.dir("public").indexFiles("index.html"))));
    }
}
