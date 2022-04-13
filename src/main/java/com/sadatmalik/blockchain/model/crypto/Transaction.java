package com.sadatmalik.blockchain.model.crypto;

/**
 * Defines a crypto transaction.
 *
 * @author sm@creativefusion.net
 */
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
