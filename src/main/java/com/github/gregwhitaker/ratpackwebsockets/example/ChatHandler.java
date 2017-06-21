package com.github.gregwhitaker.ratpackwebsockets.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.websocket.WebSocket;
import ratpack.websocket.WebSocketClose;
import ratpack.websocket.WebSocketHandler;
import ratpack.websocket.WebSocketMessage;
import ratpack.websocket.WebSockets;
import rx.RxReactiveStreams;
import rx.subjects.PublishSubject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatHandler implements Handler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Set<String> users = new HashSet<>();
    private final PublishSubject<String> events = PublishSubject.create();

    @Override
    public void handle(Context ctx) throws Exception {
        WebSockets.websocket(ctx, new WebSocketHandler<String>() {

            @Override
            public String onOpen(WebSocket webSocket) throws Exception {
                String client = ctx.getRequest().getQueryParams().get("client");
                Map<String, String> response = new HashMap<>();

                if (client == null) {
                    // Client id is required
                    response.put("type", "error");
                    response.put("message", "Client id is required!");
                } else if (users.contains(client)) {
                    // Client can only connect once
                    response.put("type", "error");
                    response.put("message", "User is already connected!");
                } else {
                    // Client successfully connected
                    response.put("type", "clientconnect");
                    response.put("client", client);
                    response.put("success", "true");
                }

                return mapper.writer().writeValueAsString(response);
            }

            @Override
            public void onClose(WebSocketClose<String> close) throws Exception {
                // Noop
            }

            @Override
            public void onMessage(WebSocketMessage<String> frame) throws Exception {
                events.onNext(frame.getText());
            }
        });

        WebSockets.websocketBroadcast(ctx, RxReactiveStreams.toPublisher(events));
    }
}
