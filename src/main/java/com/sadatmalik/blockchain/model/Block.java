package com.sadatmalik.blockchain.model;

/**
 * Models a block in the blockchain.
 *
 * @author sm@creativefusion.net
 */
public class Block {
    Long index;
    String timeStamp;
    long proof;
    String previousHash;

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
