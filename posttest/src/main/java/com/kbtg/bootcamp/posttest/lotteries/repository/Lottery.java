package com.kbtg.bootcamp.posttest.lotteries.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lottery")
@AllArgsConstructor
@NoArgsConstructor
public class Lottery {

    @Id
    @Column(name = "ticket_id", length = 6)
    private String ticketId;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
