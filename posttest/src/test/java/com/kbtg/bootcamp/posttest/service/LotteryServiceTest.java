package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.exception.InvalidRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lotteries.dto.CreateLotteryDto;
import com.kbtg.bootcamp.posttest.lotteries.repository.Lottery;
import com.kbtg.bootcamp.posttest.lotteries.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lotteries.repository.UserTicket;
import com.kbtg.bootcamp.posttest.lotteries.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.lotteries.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LotteryServiceTest {

    @Mock
    LotteryRepository lotteryRepository;

    @Mock
    UserTicketRepository userTicketRepository;


    LotteryService lotteryService;

    @BeforeEach
    void setUp(){
        lotteryService = new LotteryService(lotteryRepository, userTicketRepository);
    }

    @Test
    @DisplayName("Test add valid lottery")
    void testAddLottery() {
        CreateLotteryDto createLotteryDto = new CreateLotteryDto("123456", 80, 1);
        String actualTicketId = lotteryService.addLottery(createLotteryDto);
        assertEquals(createLotteryDto.ticket(), actualTicketId);
    }

    @Test
    @DisplayName("Test add lottery with non-numeric ticket id")
    void testAddLotteryWithInvalidTicket() {
        CreateLotteryDto createLotteryDto = new CreateLotteryDto("12345X", 80, 1);
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> lotteryService.addLottery(createLotteryDto));
        assertEquals("Ticket id must be numeric only", exception.getMessage());
    }

    @Test
    @DisplayName("Test get all lottery Ids")
    void testGetAllLotteryIds() {
        List<Lottery> lotteries = List.of(new Lottery("123456", 81, 1),
                                          new Lottery("223456", 81, 1),
                                          new Lottery("323456", 81, 1));
        when(lotteryRepository.findAll()).thenReturn(lotteries);
        List<String> actualTicketIds = lotteryService.getAllLotteryIds();
        assertEquals(List.of("123456", "223456", "323456"), actualTicketIds);
    }

    @Test
    @DisplayName("Test buy lottery successfully")
    void testBuyLottery() {
        String ticketId = "123456";
        String userId = "1234567890";
        Lottery lottery = new Lottery(ticketId, 81, 1);

        when(lotteryRepository.findById(ticketId)).thenReturn(Optional.of(lottery));
        when(userTicketRepository.save(any())).thenReturn(new UserTicket(1, userId, lottery));

        String actualId = lotteryService.buyLottery(userId, ticketId);
        assertEquals("1", actualId);
    }

    @Test
    @DisplayName("Test buy lottery with non-existing ticket id")
    void testBuyLotteryWithNonExistingTicketId() {
        String ticketId = "123456";
        String userId = "1234567890";
        Lottery lottery = new Lottery(ticketId, 81, 1);

        when(lotteryRepository.findById(ticketId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> lotteryService.buyLottery(userId, ticketId));
        assertEquals("Cannot find ticket id: " + ticketId, exception.getMessage());
    }

    @Test
    @DisplayName("Test buy lottery with invalid user id")
    void testBuyLotteryWithInvalidUserId() {
        String ticketId = "123456";
        Map<String, String> data = Map.of("", "User id must not be empty!",
                                        " ", "User id must not be empty!",
                                        "12345678901", "User id is too long!",
                                        "123456789A", "User id must be numeric only");

        for(String userId : data.keySet()) {
            InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> lotteryService.buyLottery(userId, ticketId));
            assertEquals(data.get(userId), exception.getMessage());
        }
    }

    @Test
    @DisplayName("Test buy already sold lottery")
    void testBuyAlreadySoldLottery() {
        String ticketId = "123456";
        String userId = "1234567890";
        Lottery lottery = new Lottery(ticketId, 81, 1);

        when(lotteryRepository.findById(ticketId)).thenReturn(Optional.of(lottery));
        when(userTicketRepository.save(any())).thenThrow(new RuntimeException());

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> lotteryService.buyLottery(userId, ticketId));
        assertEquals("Ticket with id '" + ticketId + "' is already sold!", exception.getMessage());
    }

    @Test
    @DisplayName("Test get all lotteries by user id")
    void testGetAllLotteriesByUserId() {
        String ticketId = "123456";
        String userId = "1234567890";
        Lottery lottery = new Lottery(ticketId, 81, 1);
        UserTicket userTicket = new UserTicket(1, userId, lottery);

        when(userTicketRepository.findAll(any(Example.class))).thenReturn(List.of(userTicket));

        List<UserTicket> actualUserTickets = lotteryService.getLotteries(userId);
        assertEquals(List.of(userTicket), actualUserTickets);
    }

    @Test
    @DisplayName("Test delete lottery")
    void testDeleteLottery() {
        String ticketId = "123456";
        String userId = "1234567890";
        Lottery lottery = new Lottery(ticketId, 81, 1);
        UserTicket userTicket = new UserTicket(1, userId, lottery);

        when(lotteryRepository.findById(ticketId)).thenReturn(Optional.of(new Lottery(ticketId, 81, 1)));
        when(userTicketRepository.findAll(any(Example.class))).thenReturn(List.of(userTicket));

        String actualTicketId = lotteryService.deleteBuyLottery(userId, ticketId);
        assertEquals(ticketId, actualTicketId);
    }

    @Test
    @DisplayName("Test delete non-existing lottery")
    void testDeleteNonExistingLottery() {
        String ticketId = "123456";
        String userId = "1234567890";
        Lottery lottery = new Lottery(ticketId, 81, 1);

        when(lotteryRepository.findById(ticketId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> lotteryService.deleteBuyLottery(userId, ticketId));
        assertEquals("Cannot find ticket id: " + ticketId, exception.getMessage());
    }
}
