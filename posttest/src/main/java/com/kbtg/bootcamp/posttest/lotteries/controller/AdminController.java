package com.kbtg.bootcamp.posttest.lotteries.controller;

import com.kbtg.bootcamp.posttest.lotteries.dto.CreateLotteryDto;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/lotteries")
    public Map<String, String> addLottery(@Valid @RequestBody CreateLotteryDto createLotteryDto) {
        String ticketId = lotteryService.addLottery(createLotteryDto);
        return Map.of("ticket", ticketId);
    }
}
