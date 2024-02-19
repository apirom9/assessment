package com.kbtg.bootcamp.posttest.lotteries.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateLotteryDto(
        @NotNull
        @NotBlank
        @Size(min = 1, max = 6, message = "ticket length should 1-6 characters")
        String ticket,

        @NotNull
        @Positive
        Integer price,

        @NotNull
        @Positive
        Integer amount) {
}
