package com.kbtg.bootcamp.posttest.lotteries;

import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", length = 10)
    private String userId;

    @OneToOne
    private Lottery ticket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Lottery getTicket() {
        return ticket;
    }

    public void setTicket(Lottery ticket) {
        this.ticket = ticket;
    }
}
