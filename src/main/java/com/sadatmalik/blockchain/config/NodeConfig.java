package com.sadatmalik.blockchain.config;

import com.sadatmalik.blockchain.model.nodes.Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Reads the configuration properties and creates a node with the host address and
 * port as a URL.
 *
 * @author sm@creativefusion.net
 */
@Configuration
public class NodeConfig {

    @Value("${node.url}")
    private String url;

    @Value("${server.port}")
    private String port;

    @Bean
    public Node node() {
        return new Node(url + ":" + port);
    }

}
