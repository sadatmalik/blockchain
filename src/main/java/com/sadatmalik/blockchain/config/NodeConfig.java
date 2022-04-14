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

    @Value("${node.miner}")
    private String miner;

    /**
     * Creates a new node with the url values from configuration properties.
     * Sets the miner for this node - i.e. the recipient of mined coins.
     *
     * @return
     */
    @Bean
    public Node node() {
        Node node = new Node(url + ":" + port);
        node.setMiner(miner);
        return node;
    }

}
