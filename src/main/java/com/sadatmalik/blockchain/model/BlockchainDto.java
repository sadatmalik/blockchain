package com.sadatmalik.blockchain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author sm@creativefusion.net
 */
@Getter
@Setter
public class BlockchainDto {
    List<Block> chain;
    int size;

    public BlockchainDto(List<Block> chain, int size) {
        this.chain = chain;
        this.size = size;
    }
}
