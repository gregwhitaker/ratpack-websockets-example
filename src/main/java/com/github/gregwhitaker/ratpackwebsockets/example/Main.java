package com.github.gregwhitaker.ratpackwebsockets.example;

import ratpack.guice.Guice;
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
                                    .registry(Guice.registry(b -> b.module(DemoModule.class)))
                                    .handlers(chain ->
                                            chain
                                                .get("demo/events", DemoHandler.class)
                                                .files(f -> f.dir("public").indexFiles("index.html"))));
    }
}
