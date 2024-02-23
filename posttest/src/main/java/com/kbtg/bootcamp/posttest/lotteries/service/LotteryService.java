package com.kbtg.bootcamp.posttest.lotteries.service;

import com.kbtg.bootcamp.posttest.exception.InvalidRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lotteries.dto.CreateLotteryDto;
import com.kbtg.bootcamp.posttest.lotteries.repository.Lottery;
import com.kbtg.bootcamp.posttest.lotteries.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lotteries.repository.UserTicket;
import com.kbtg.bootcamp.posttest.lotteries.repository.UserTicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    private final UserTicketRepository userTicketRepository;

    public LotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    public String addLottery(CreateLotteryDto createLotteryDto) {
        validate(createLotteryDto);

        Lottery lottery = new Lottery();
        lottery.setTicketId(createLotteryDto.ticket());
        lottery.setPrice(createLotteryDto.price());
        lottery.setAmount(createLotteryDto.amount());

        this.lotteryRepository.save(lottery);

        return lottery.getTicketId();
    }

    public List<String> getAllLotteryIds(){
        List<String> result = new ArrayList<>();
        for(Lottery lottery : this.lotteryRepository.findAll()){
            result.add(lottery.getTicketId());
        }
        return result;
    }

    private void validate(CreateLotteryDto createLotteryDto) {
        for (char c : createLotteryDto.ticket().toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidRequestException("Ticket id must be numeric only");
            }
        }
    }

    public String buyLottery(String userId, String ticketId) {

        Optional<Lottery> ticket = this.lotteryRepository.findById(ticketId);
        if(ticket.isEmpty()){
            throw new NotFoundException("Cannot find ticket id: " + ticketId);
        }

        if(userId.isEmpty() || userId.isBlank()){
            throw new InvalidRequestException("User id is empty!");
        }

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicket(ticket.get());
        try{
            this.userTicketRepository.save(userTicket);
        } catch (Exception e){
            throw new InvalidRequestException("Ticket with id '" + ticketId + "' is already sold!");
        }
        return userTicket.getId().toString();
    }
}
