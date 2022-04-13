package com.sadatmalik.blockchain.model.nodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author sm@creativefusion.net
 */
@Getter
@Setter
@ToString
public class Node {

    String url;
    String address;

    public Node(String url) {
        this.url = url;
        this.address = UUID.randomUUID().toString()
                .replace("-", "");
    }
}
