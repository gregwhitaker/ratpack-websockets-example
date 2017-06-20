package com.github.gregwhitaker.ratpackwebsockets.example.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class UserEventHandler implements Handler {
    private static final Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);

    @Override
    public void handle(Context ctx) throws Exception {


//        if (ctx.getRequest().getQueryParams().get("clientId") != null) {
//            WebSockets.websocket(ctx, new WebSocketHandler<String>() {
//
//                @Override
//                public String onOpen(WebSocket webSocket) throws Exception {
//                    LOG.debug("Websocket connection created from: " + ctx.getRequest().getRemoteAddress().toString());
//                    return null;
//                }
//
//                @Override
//                public void onClose(WebSocketClose<String> close) throws Exception {
//                    LOG.debug("Websocket connection closed by: " + ctx.getRequest().getRemoteAddress().toString());
//                    // Noop
//                }
//
//                @Override
//                public void onMessage(WebSocketMessage<String> frame) throws Exception {
//
//                }
//            });
//        }
    }
}
