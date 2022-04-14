package com.sadatmalik.blockchain.model;

import com.sadatmalik.blockchain.model.crypto.Transaction;
import com.sadatmalik.blockchain.model.nodes.Node;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Creates a blockchain.
 *
 * The inclusion of transactions sets up the blockchain for cryptocurrency. And a set
 * of nodes holds all the nodes in the blockchain distributed network.
 *
 * The restTemplate handles the p2p communications with the nodes in the network.
 *
 * @author sm@creativefusion.net
 */
@Getter
public class Blockchain {

    List<Block> chain;
    List<Transaction> transactions;
    Set<Node> nodes;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Initializes the chain and creates the genesis block.
     */
    public Blockchain() {
        chain = new ArrayList<>();
        transactions = new ArrayList<>();
        nodes = new HashSet<>();
        createBlock(1L, "0"); //genesis block
    }

    /**
     * Creates a new block with index equal to current size of chain plus one, the current
     * date and time, the "proof" of work from mining the previous block, and the hash of the
     * previous block.
     *
     * Adds the created block to the chain. And then clears the transactions list, ready for the
     * next mined block.
     *
     * @param proof the proof of work.
     * @param previousHash hash of the previous block.
     * @return the new mined block.
     */
    public Block createBlock(long proof, String previousHash) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timeStamp = dateTime.format(formatter);

        Block block = new Block(
                chain.size()+1,
                timeStamp, proof, previousHash,
                transactions);

        transactions = new ArrayList<>();

        chain.add(block);
        return block;
    }

    /**
     * Returns the last inserted block - the block currently at the end of the
     * chain. Could be named getLastBlock().
     *
     * @return last inserted block.
     */
    public Block getPreviousBlock() {
        return chain.get(chain.size()-1);
    }


    /**
     * The proof of work algorithm for miners. Not too complicated but could easily
     * be made more difficult - the easiest way would be to simply add more leading
     * zeroes to the final check. For now, simply hashes the difference between the
     * squares of the proof and the previous proof with SHA256. Looking for a hash with
     * 4 leading zeroes.
     *
     * @param prevProof the proof from the last block
     * @return the proof for the new block
     */
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

    /**
     * Returns the hash of the given block - hashes the entire block, including the proof,
     * previous hash, index and timestamp. And the body - i.e. crypto transactions.
     *
     * @param block to calculate the hash for
     * @return SHA256 hash
     */
    public static String hash(Block block) {
        return DigestUtils.sha256Hex(block.toString());
    }

    /**
     * Starting from beginning of the chain, checks the calculated hash of each previous
     * block with the previous hash value stored in its next block.
     *
     * Likewise, rechecks the proof of work has not been tampered with - reapplying the
     * difference of squares of proofs hash check.
     *
     * Returns false if either of the above conditions fail, otherwise true.
     *
     * @return chain validity true or false
     */
    public static boolean isChainValid(List<Block> chain) {
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

    /**
     * Adds the transaction to the current transaction list, and returns the index of
     * the block into which the transactions will be inserted - i.e. the next mined
     * block.
     *
     * @param transaction the transaction to insert.
     * @return the index of the next block - into which the transaction will be inserted.
     */
    public long addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return getPreviousBlock().index + 1;
    }

    /**
     * Creates a new node for the given network URL and adds it to the set of known nodes.
     *
     * @param url the node url.
     */
    public void addNode(String url) {
        nodes.add(new Node(url));
    }

    /**
     * Iterates over every node in the p2p network. For each node, retrieves its chain via
     * a Rest call. Checks the size of every chain in the network, if a longer chain is found,
     * replaces own chain with the longest one found.
     *
     * @return true if our chain was replaced by a longer one, otherwise returns false.
     */
    public boolean replaceChain() {
        List<Block> longestChain = null;
        int maxLength = this.chain.size();
        for (Node node : nodes) {
            ResponseEntity<BlockchainDto> response =
                    restTemplate.getForEntity(node.getUrl() + "/get-chain",
                            BlockchainDto.class);
            if (response.getBody() != null &&
                    response.getStatusCode().is2xxSuccessful()) {
                int length = response.getBody().getSize();
                List<Block> chain = response.getBody().getChain();
                if (length > maxLength && isChainValid(chain)) {
                    maxLength = length;
                    longestChain = chain;
                }
            }
        }
        if (longestChain != null) {
            this.chain = longestChain;
            return true;
        }
        return false;
    }
}
