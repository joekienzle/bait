package org.bait.config;

import org.bait.rest.BaitResource;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(BaitResource.class);
    }
}
