package com.sadatmalik.blockchain.controllers;

import com.sadatmalik.blockchain.model.Blockchain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public Blockchain blockchain() {
        return new Blockchain();
    }
}
