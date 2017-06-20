package com.github.gregwhitaker.ratpackwebsockets.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.websocket.WebSocket;
import ratpack.websocket.WebSocketClose;
import ratpack.websocket.WebSocketHandler;
import ratpack.websocket.WebSocketMessage;
import ratpack.websocket.WebSockets;
import rx.subjects.PublishSubject;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DemoHandler implements Handler {
    private static final Logger LOG = LoggerFactory.getLogger(DemoHandler.class);

    private final Map<String, PublishSubject<String>> listeners = new HashMap<>();

    @Override
    public void handle(Context ctx) throws Exception {
        WebSockets.websocket(ctx, new WebSocketHandler<String>() {
            @Override
            public String onOpen(WebSocket webSocket) throws Exception {
                WebSockets.websocketBroadcast();
                return null;
            }

            @Override
            public void onClose(WebSocketClose<String> close) throws Exception {
                // Noop
            }

            @Override
            public void onMessage(WebSocketMessage<String> frame) throws Exception {

            }
        });
    }
}
