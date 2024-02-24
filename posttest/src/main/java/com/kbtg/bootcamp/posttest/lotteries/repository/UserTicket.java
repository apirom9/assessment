package com.kbtg.bootcamp.posttest.lotteries.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_ticket", indexes = @Index(columnList = "user_id, ticket_ticket_id"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", length = 10)
    private String userId;

    @OneToOne
    private Lottery ticket;
}
