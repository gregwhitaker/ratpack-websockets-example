package com.github.gregwhitaker.ratpackwebsockets.example;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class DemoConnectHandler implements Handler {

    private final DemoService service;

    public DemoConnectHandler(DemoService service) {
        this.service = service;
    }

    @Override
    public void handle(Context ctx) throws Exception {

    }
}
