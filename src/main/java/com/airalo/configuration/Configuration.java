package com.airalo.configuration;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:system.properties")
public interface Configuration extends Config {

    @Key("browser")
    String browser();

    @Key("env")
    String env();

    @Key("url.base")
    String baseUrl();

    @Key("url.login")
    String loginUrl();

}
