package com.github.gregwhitaker.ratpackwebsockets.example;

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
                                        registry.add(new ChatHandler());
                                    })
                                    .handlers(chain ->
                                            chain
                                                .get("chat", ChatHandler.class)
                                                .files(f -> f.dir("public").indexFiles("index.html"))));
    }
}
