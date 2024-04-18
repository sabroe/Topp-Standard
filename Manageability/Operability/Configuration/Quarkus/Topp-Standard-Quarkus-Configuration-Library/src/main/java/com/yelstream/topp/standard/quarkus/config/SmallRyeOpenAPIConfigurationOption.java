package com.yelstream.topp.standard.quarkus.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.List;

@Slf4j
@AllArgsConstructor
public enum SmallRyeOpenAPIConfigurationOption {

    //https://quarkus.io/guides/all-config#quarkus-smallrye-openapi_quarkus-smallrye-openapi-smallrye-openapi

    QuarkusHttpTestSslPort("quarkus.smallrye-openapi.servers",List.class);  //List of String?

    @Getter
    private final String propertyName;

    @Getter
    private final Class<?> propertyType;

    public OptionReader reader() {
        Config config= ConfigProvider.getConfig();
        return new OptionReader(this,config,propertyName,propertyType);
    }

    public static void log() {
        for (var option: values()) {
            OptionReader reader=option.reader();
            reader.log(log);
        }
    }
}
