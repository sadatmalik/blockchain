package com.sadatmalik.blockchain.model.nodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sm@creativefusion.net
 */
@Getter
@Setter
@ToString
public class Node {

    String url;

    public Node(String url) {
        this.url = url;
    }
}
