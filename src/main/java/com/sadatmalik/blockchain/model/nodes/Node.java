package com.sadatmalik.blockchain.model.nodes;

import lombok.Getter;

/**
 * @author sm@creativefusion.net
 */
@Getter
public class Node {

    String url;

    public Node(String url) {
        this.url = url;
    }
}
