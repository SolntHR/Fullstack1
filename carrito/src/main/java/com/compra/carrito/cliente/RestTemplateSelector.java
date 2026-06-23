package com.compra.carrito.cliente;

import java.net.URI;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateSelector {

    private final RestTemplate defaultRestTemplate;
    private final RestTemplate loadBalancedRestTemplate;

    public RestTemplateSelector(
            @Qualifier("defaultRestTemplate") RestTemplate defaultRestTemplate,
            @Qualifier("loadBalancedRestTemplate") RestTemplate loadBalancedRestTemplate) {
        this.defaultRestTemplate = defaultRestTemplate;
        this.loadBalancedRestTemplate = loadBalancedRestTemplate;
    }

    public RestTemplate select(String baseUrl) {
        URI uri = URI.create(baseUrl);
        String host = uri.getHost();

        if (host == null || requiresDiscovery(host)) {
            return loadBalancedRestTemplate;
        }

        return defaultRestTemplate;
    }

    private boolean requiresDiscovery(String host) {
        return !(host.equalsIgnoreCase("localhost")
                || host.contains(".")
                || host.matches("\\d+\\.\\d+\\.\\d+\\.\\d+"));
    }
}
