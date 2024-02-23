package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lotteries.controller.UserController;
import com.kbtg.bootcamp.posttest.lotteries.repository.Lottery;
import com.kbtg.bootcamp.posttest.lotteries.repository.UserTicket;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    MockMvc mockMvc;

    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp(){
        UserController userController = new UserController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("Test buy lottery successfully")
    void testBuyLotterySuccess() throws Exception {
        String userId = "1234567890";
        String ticketId = "123456";
        String url = "/users/" + userId + "/lotteries/" + ticketId;
        when(lotteryService.buyLottery(userId, ticketId)).thenReturn("1");
        mockMvc.perform(post(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")));
    }

    @Test
    @DisplayName("Test get lotteries by user")
    void testGetLotteriesByUser() throws Exception {

        String userId = "1234567890";
        List<UserTicket> userTickets = new ArrayList<>();
        userTickets.add(new UserTicket(1, userId, new Lottery("123456", 100, 1)));
        userTickets.add(new UserTicket(2, userId, new Lottery("123457", 200, 2)));

        when(lotteryService.getLotteries(userId)).thenReturn(userTickets);
        mockMvc.perform(get("/users/" + userId + "/lotteries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(3)))
                .andExpect(jsonPath("$.cost", is(500)))
                .andExpect(jsonPath("$.tickets", is(List.of("123456","123457"))));
    }

    @Test
    @DisplayName("Test delete buy lottery successfully")
    void testDeleteBuyLotterySuccess() throws Exception {
        String userId = "1234567890";
        String ticketId = "123456";
        String url = "/users/" + userId + "/lotteries/" + ticketId;
        when(lotteryService.deleteBuyLottery(userId, ticketId)).thenReturn("2");
        mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is("2")));
    }
}
