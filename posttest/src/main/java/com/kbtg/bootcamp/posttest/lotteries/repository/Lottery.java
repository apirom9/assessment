package com.kbtg.bootcamp.posttest.lotteries.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lottery")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lottery {

    @Id
    @Column(name = "ticket_id", length = 6)
    private String ticketId;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;
}
