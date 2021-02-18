package com.unlimint.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

    @JsonProperty("orderId")
    private long orderId;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("comment")
    private String comment;
    private String status;

    @JsonCreator
    public Order(@JsonProperty(value = "orderId", required = true) long orderId,
                 @JsonProperty(value = "amount", required = true) double amount,
                 @JsonProperty(value = "currency", required = true) String currency,
                 @JsonProperty(value = "comment", required = true) String comment) {

        this.orderId = orderId;

        if (currency.equals(""))
            throw new IllegalArgumentException("Empty field.");
        if (comment.equals(""))
            throw new IllegalArgumentException("Empty field.");
        if (amount == 0)
            throw new IllegalArgumentException("Empty field.");

        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
        this.status = "OK";
    }
}
