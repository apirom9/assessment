package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.lotteries.controller.LotteryController;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {

    MockMvc mockMvc;

    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp(){
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("Test get all lotteries successfully")
    void testGetAllLottery() throws Exception {
        when(lotteryService.getAllLotteryIds()).thenReturn(List.of("000001","000002","123456"));
        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", is(List.of("000001","000002","123456"))));
    }
}
