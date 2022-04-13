package com.sadatmalik.blockchain.model.crypto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Defines a crypto transaction.
 *
 * @author sm@creativefusion.net
 */
@Getter
@Setter
@ToString
public class Transaction {
    String sender;
    String receiver;
    double amount;

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
