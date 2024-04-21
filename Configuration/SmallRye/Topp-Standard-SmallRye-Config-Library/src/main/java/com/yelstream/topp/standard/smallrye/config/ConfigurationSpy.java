package com.yelstream.topp.standard.smallrye.config;

import io.smallrye.config.SmallRyeConfig;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;

public class ConfigurationSpy {


    public static void bruteForcePrint() {

        Config config = ConfigProvider.getConfig();
        SmallRyeConfig smallRyeConfig = config.unwrap(SmallRyeConfig.class);

        System.out.println("Profiles: "+smallRyeConfig.getProfiles());

        int index=0;
        for (ConfigSource configSource: smallRyeConfig.getConfigSources()) {
            System.out.format("[%s] ConfigSource: %n",index);
            System.out.println("    Name:           "+configSource.getName());
            System.out.println("    Class:          "+configSource.getClass());
            System.out.println("    Ordinal:        "+configSource.getOrdinal());
            System.out.println("    Property names: "+configSource.getPropertyNames());
            System.out.println("    Property:       "+configSource.getProperties());
            index++;
        }
    }

}
