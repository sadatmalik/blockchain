package com.sadatmalik.blockchain.model.crypto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Defines a crypto transaction.
 *
 * @author sm@creativefusion.net
 */
@Getter
@Setter
@ToString
public class Transaction {

    @NotBlank
    String sender;

    @NotBlank
    String receiver;

    @NotNull
    Double amount;

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
