package com.kbtg.bootcamp.posttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lotteries.controller.AdminController;
import com.kbtg.bootcamp.posttest.lotteries.dto.CreateLotteryDto;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    MockMvc mockMvc;

    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp(){
        AdminController adminController = new AdminController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("Test create lottery successfully")
    void testCreateLotterySuccess() throws Exception {
        CreateLotteryDto createLotteryDto = new CreateLotteryDto("123456", 80, 1);
        when(lotteryService.addLottery(createLotteryDto)).thenReturn(createLotteryDto.ticket());
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(createLotteryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket", is(createLotteryDto.ticket())));
    }

    @Test
    @DisplayName("Test unable to create lottery with ticket id that is too long")
    void testCreateInvalidLotteryWithTooLongTicketId() throws Exception {
        CreateLotteryDto createLotteryDto = new CreateLotteryDto("1234567", 80, 1);
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(createLotteryDto)))
                .andExpect(status().isBadRequest());
    }
}
