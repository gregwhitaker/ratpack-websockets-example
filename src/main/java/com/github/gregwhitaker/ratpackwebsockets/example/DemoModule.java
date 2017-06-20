package com.github.gregwhitaker.ratpackwebsockets.example;

import com.google.inject.AbstractModule;

public class DemoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DemoHandler.class);
        bind(DemoModule.class);
        bind(DemoService.class);
    }
}
