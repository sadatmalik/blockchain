package com.sadatmalik.blockchain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a blockchain
 *
 * @author sm@creativefusion.net
 */
public class Blockchain {

    List<Block> chain;

    Blockchain() {
        chain = new ArrayList<>();
        Block genesis = createBlock("1", "0");
    }

    public Block createBlock(String proof, String previousHash) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timeStamp = dateTime.format(formatter);

        Block block = new Block(
                chain.size()+1,
                timeStamp, proof, previousHash);

        chain.add(block);
        return block;
    }
}
