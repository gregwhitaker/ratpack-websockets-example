package com.github.gregwhitaker.ratpackwebsockets.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.websocket.WebSocket;
import ratpack.websocket.WebSocketClose;
import ratpack.websocket.WebSocketHandler;
import ratpack.websocket.WebSocketMessage;
import ratpack.websocket.WebSockets;
import rx.Subscription;
import rx.subjects.PublishSubject;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatHandler implements Handler {
    private static final Logger LOG = LoggerFactory.getLogger(ChatHandler.class);

    private final ObjectMapper mapper = new ObjectMapper();

    // List of all currently connected clients
    private final Set<String> clients = new HashSet<>();

    // Subject that all clients subscribe to for events
    private final PublishSubject<String> events = PublishSubject.create();

    // Mapping of client to subscription to the events subject
    private final Map<String, Subscription> subscriptions = new HashMap<>();

    @Override
    public void handle(Context ctx) throws Exception {
        WebSockets.websocket(ctx, new WebSocketHandler<String>() {

            @Override
            public String onOpen(WebSocket webSocket) throws Exception {
                String client = ctx.getRequest().getQueryParams().get("client");

                LOG.info("Websocket opened for client: {}", client);

                if (client == null || client.isEmpty()) {
                    webSocket.close(500, "Client id is required");
                } else if (clients.contains(client)) {
                    webSocket.close(500, "Client is already connected");
                } else {
                    Map<String, Object> initEvent = new HashMap<>();
                    initEvent.put("type", "init");
                    initEvent.put("client", client);
                    initEvent.put("success", true);
                    initEvent.put("connectedClients", Collections.unmodifiableSet(clients));

                    webSocket.send(mapper.writer().writeValueAsString(initEvent));

                    clients.add(client);

                    Map<String, Object> clientConnectEvent = new HashMap<>();
                    clientConnectEvent.put("type", "clientconnect");
                    clientConnectEvent.put("client", client);

                    events.onNext(mapper.writer().writeValueAsString(clientConnectEvent));

                    subscriptions.put(client, events.subscribe(webSocket::send));

                    LOG.info("Client {} subscribed to event stream", client);
                }

                return null;
            }

            @Override
            public void onClose(WebSocketClose<String> close) throws Exception {
                String client = ctx.getRequest().getQueryParams().get("client");

                clients.remove(client);

                Map<String, Object> event = new HashMap<>();
                event.put("type", "clientdisconnect");
                event.put("client", client);

                events.onNext(mapper.writer().writeValueAsString(event));

                subscriptions.remove(client);

                LOG.info("Websocket closed for client: {}", client);
            }

            @Override
            public void onMessage(WebSocketMessage<String> frame) throws Exception {
                events.onNext(frame.getText());
            }
        });
    }
}
