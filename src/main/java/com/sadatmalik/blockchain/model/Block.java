package com.sadatmalik.blockchain.model;

import lombok.Getter;

/**
 * Models a block in the blockchain. Encapsulate index, timeStamp, proof, and
 * the previous block's SHA 256 ash.
 *
 * @author sm@creativefusion.net
 */
@Getter
public class Block {
    Long index;
    String timeStamp;
    long proof;
    String previousHash;

    /**
     * All args constructor.
     *
     * @param index block chain index
     * @param timeStamp timestamp string
     * @param proof the proof of work
     * @param previousHash previous block's hash
     */
    Block(long index, String timeStamp, long proof,
          String previousHash) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", timeStamp='" + timeStamp + '\'' +
                ", proof='" + proof + '\'' +
                ", previousHash='" + previousHash + '\'' +
                '}';
    }
}
