package com.sadatmalik.blockchain.controllers;

import com.sadatmalik.blockchain.model.Block;
import com.sadatmalik.blockchain.model.Blockchain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Blockchain Rest controller.
 *
 * @author sm@creativefusion.net
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class BlockchainController {

    private final Blockchain blockchain;

    @GetMapping("/mine-block")
    public ResponseEntity<Block> mineBlock() {
        long proof = blockchain.proofOfWork(
                blockchain.getPreviousBlock().getProof()
        );
        String previousHash = blockchain.hash(
                blockchain.getPreviousBlock()
        );
        Block block = blockchain.createBlock(proof, previousHash);

        log.info("Mined block: " + block.toString());

        return ResponseEntity.ok(block);
    }

    @GetMapping("/get-chain")
    public ResponseEntity<List<Block>> getChain() {
        return ResponseEntity.ok(blockchain.getChain());
    }

    @GetMapping("/is-valid")
    public ResponseEntity<Boolean> isValid() {
        return ResponseEntity.ok(
                blockchain.isChainValid()
        );
    }
}
