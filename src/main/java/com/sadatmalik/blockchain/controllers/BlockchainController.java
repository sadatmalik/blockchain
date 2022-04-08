package com.sadatmalik.blockchain.controllers;

import com.sadatmalik.blockchain.model.Block;
import com.sadatmalik.blockchain.model.Blockchain;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Blockchain Rest controller.
 *
 * @author sm@creativefusion.net
 */
@RestController
@RequiredArgsConstructor
public class BlockchainController {

    private final Blockchain blockchain;

    @GetMapping("/mine-block")
    public String mineBlock() {
        long proof = blockchain.proofOfWork(
                blockchain.getPreviousBlock().getProof()
        );
        String previousHash = blockchain.hash(
                blockchain.getPreviousBlock()
        );
        Block block = blockchain.createBlock(proof, previousHash);

        String message = "Congratulations: You just mined a block! \n" +
                block.toString();
        return message;
    }
}
