package com.kbtg.bootcamp.posttest.lotteries.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateLotteryDto(
        @NotNull(message = "ticket is missing")
        @NotBlank(message = "ticket must not be blank")
        @Size(min = 1, max = 6, message = "ticket length should 1-6 characters")
        String ticket,

        @NotNull(message = "price is missing")
        @Positive(message = "price must be greater than 0")
        Integer price,

        @NotNull(message = "amount is missing")
        @Positive(message = "amount must be greater than 0")
        Integer amount) {
}
