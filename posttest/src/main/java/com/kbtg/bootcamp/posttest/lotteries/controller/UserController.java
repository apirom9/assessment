package com.kbtg.bootcamp.posttest.lotteries.controller;

import com.kbtg.bootcamp.posttest.lotteries.repository.Lottery;
import com.kbtg.bootcamp.posttest.lotteries.repository.UserTicket;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LotteryService lotteryService;

    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> buyLottery(@PathVariable String userId, @PathVariable String ticketId) {
        String id = lotteryService.buyLottery(userId, ticketId);
        return new ResponseEntity<>(Map.of("id", id), HttpStatus.OK);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<Map<String, Object>> getLotteries(@PathVariable String userId) {

        List<String> ticketIds = new ArrayList<>();
        int totalCost = 0;
        int totalCount = 0;
        for(UserTicket userTicket : lotteryService.getLotteries(userId)){
            Lottery ticket = userTicket.getTicket();
            ticketIds.add(ticket.getTicketId());
            totalCost += ticket.getPrice() * ticket.getAmount();
            totalCount += ticket.getAmount();
        }
        return new ResponseEntity<>(Map.of("tickets", ticketIds,
                                            "count", totalCount,
                                           "cost", totalCost), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> deleteBuyLottery(@PathVariable String userId, @PathVariable String ticketId) {
        String ticket = lotteryService.deleteBuyLottery(userId, ticketId);
        return new ResponseEntity<>(Map.of("ticket", ticket), HttpStatus.OK);
    }
}
