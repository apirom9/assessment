package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lotteries.controller.UserController;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        String userId = "user1";
        String ticketId = "123456";
        String url = "/users/" + userId + "/lotteries/" + ticketId;
        when(lotteryService.buyLottery(userId, ticketId)).thenReturn("1");
        mockMvc.perform(post(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")));
    }
}
