package com.sadatmalik.blockchain;

/**
 * @author sm@creativefusion.net
 */
public class Block {
    Long index;
    String timeStamp;
    String proof;
    String previousHash;


    Block(long index, String timeStamp, String proof,
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
