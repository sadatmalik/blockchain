package com.sadatmalik.blockchain.controllers;

import com.sadatmalik.blockchain.model.Block;
import com.sadatmalik.blockchain.model.Blockchain;
import com.sadatmalik.blockchain.model.BlockchainDto;
import com.sadatmalik.blockchain.model.crypto.Transaction;
import com.sadatmalik.blockchain.model.nodes.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Blockchain Rest controller.
 *
 * @author sm@creativefusion.net
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class BlockchainController {

    private final Blockchain blockchain;
    private final Node node;

    /**
     * Mines the previous block to insert the new block with the required proof.
     *
     * Adds a single transaction representing the mined coins returned to the miner.
     *
     * @return newly inserted block
     */
    @GetMapping("/mine-block")
    public ResponseEntity<Block> mineBlock() {
        long proof = blockchain.proofOfWork(
                blockchain.getPreviousBlock().getProof()
        );
        String previousHash = Blockchain.hash(
                blockchain.getPreviousBlock()
        );
        blockchain.addTransaction(
                new Transaction(node.getAddress(), node.getMiner(), 1)
        );
        Block block = blockchain.createBlock(proof, previousHash);

        log.info("Mined block: " + block.toString());

        return ResponseEntity.ok(block);
    }

    /**
     * Get the blockchain. Constructs a Dto with the list of blocks and the chain size.
     *
     * @return the blockchain data transfer object.
     */
    @GetMapping("/get-chain")
    public ResponseEntity<BlockchainDto> getChain() {
        BlockchainDto dto = new BlockchainDto(blockchain.getChain(),
                blockchain.getChain().size());
        return ResponseEntity.ok(dto);
    }

    /**
     * Validate the blockchain.
     *
     * @return true or false
     */
    @GetMapping("/is-valid")
    public ResponseEntity<Boolean> isValid() {
        return ResponseEntity.ok(
                Blockchain.isChainValid(blockchain.getChain())
        );
    }

    /**
     * Adds the transaction body to the blockchain. Returning the index of the block
     * the transaction is being added to.
     *
     * @param transaction http request body.
     * @return http response with status created, including the block index.
     */
    @PostMapping("/add-transaction")
    public ResponseEntity<String> addTransaction(
            @RequestBody @Valid Transaction transaction) {
        long blockIndex = blockchain.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Transaction will be added to block " + blockIndex);
    }

    /**
     * Receives a Json list of Nodes in the network and them all to the blockchain.
     *
     * @param nodes list of nodes in the network.
     * @return status message.
     */
    @PostMapping("/connect-nodes")
    public ResponseEntity<String> connectNodes(
            @RequestBody @Valid List<Node> nodes) {

        if (nodes == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No node");
        }

        log.info("Adding nodes: " + nodes);

        for (Node node : nodes) {
            blockchain.addNode(node.getUrl());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("All the nodes are now connected. " +
                        "The blockchain now contains the following nodes: " +
                        blockchain.getNodes().toString());
    }

    @GetMapping("/replace-chain")
    public ResponseEntity<String> replaceChain() {
        boolean isChainReplaced = blockchain.replaceChain();
        String message;
        if (isChainReplaced) {
            message = "The nodes had different chains so the chain was replaced " +
                    "by the longest one";
        } else {
            message = "All good. The chain is the largest one.";
        }
        return ResponseEntity.ok(message);
    }

}
