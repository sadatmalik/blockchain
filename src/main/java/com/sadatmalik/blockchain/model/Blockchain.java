package com.sadatmalik.blockchain.model;

import org.apache.commons.codec.digest.DigestUtils;

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

    public Blockchain() {
        chain = new ArrayList<>();
        Block genesis = createBlock(1L, "0");
    }

    public Block createBlock(long proof, String previousHash) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timeStamp = dateTime.format(formatter);

        Block block = new Block(
                chain.size()+1,
                timeStamp, proof, previousHash);

        chain.add(block);
        return block;
    }

    public Block getPreviousBlock() {
        return chain.get(chain.size()-2);
    }

    public long proofOfWork(long prevProof) {
        long newProof = 1;
        boolean checkProof = false;

        while (!checkProof) {
            String hashOperation =
                    DigestUtils.sha256Hex(String.valueOf(
                            Math.pow(newProof,2) - Math.pow(prevProof,2)));
            if (hashOperation.startsWith("0000")) {
                checkProof = true;
            } else {
                newProof++;
            }
        }
        return newProof;
    }

    private String hash(Block block) {
        return DigestUtils.sha256Hex(block.toString());
    }

    private boolean isChainValid() {
        Block previousBlock = chain.get(0);
        long blockIndex = 1;

        while (blockIndex < chain.size()) {
            Block block = chain.get((int)blockIndex);
            if (!block.previousHash.equals(
                    hash(previousBlock))) {
                return false;
            }
            long prevProof = previousBlock.proof;
            long proof = block.proof;
            String hashOperation =
                    DigestUtils.sha256Hex(String.valueOf(
                            Math.pow(proof,2) - Math.pow(prevProof,2)));
            if (!hashOperation.startsWith("0000")) {
                return false;
            }
            previousBlock = block;
            blockIndex++;
        }
        return true;
    }
}