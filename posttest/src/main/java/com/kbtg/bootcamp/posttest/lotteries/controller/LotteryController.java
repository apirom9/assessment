package com.kbtg.bootcamp.posttest.lotteries.controller;

import com.kbtg.bootcamp.posttest.lotteries.dto.CreateLotteryDto;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<String>>> getLotteryIds(@Valid @RequestBody CreateLotteryDto createLotteryDto) {
        List<String> ticketIds = lotteryService.getAllLotteryIds();
        return new ResponseEntity<>(Map.of("tickets", ticketIds), HttpStatus.OK);
    }
}
