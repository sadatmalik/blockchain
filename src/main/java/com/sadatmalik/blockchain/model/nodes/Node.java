package com.sadatmalik.blockchain.model.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @author sm@creativefusion.net
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Node {

    @NotBlank
    String url;

    @JsonIgnore
    String address;

    public Node(String url) {
        this.url = url;
        this.address = UUID.randomUUID().toString()
                .replace("-", "");
    }
}
