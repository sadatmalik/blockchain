package com.sadatmalik.blockchain;

import com.sadatmalik.blockchain.model.Blockchain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Rest application.
 *
 * @author sadatmalik
 */
@SpringBootApplication
public class BlockchainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainApplication.class, args);
	}

	@Bean
	public Blockchain blockchain() {
		return new Blockchain();
	}

}
