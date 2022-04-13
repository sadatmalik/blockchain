package com.sadatmalik.blockchain.model;

import com.sadatmalik.blockchain.model.crypto.Transaction;
import lombok.Getter;

import java.util.List;

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
    List<Transaction> transactions;

    /**
     * All args constructor.
     *
     * @param index block chain index
     * @param timeStamp timestamp string
     * @param proof the proof of work
     * @param previousHash previous block's hash
     */
    Block(long index, String timeStamp, long proof,
          String previousHash, List<Transaction> transactions) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.proof = proof;
        this.previousHash = previousHash;
        this.transactions = transactions;
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
