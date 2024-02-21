package com.kbtg.bootcamp.posttest.lotteries.service;

import com.kbtg.bootcamp.posttest.exception.InvalidRequestException;
import com.kbtg.bootcamp.posttest.lotteries.dto.CreateLotteryDto;
import com.kbtg.bootcamp.posttest.lotteries.repository.Lottery;
import com.kbtg.bootcamp.posttest.lotteries.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lotteries.repository.UserTicketRepository;
import org.springframework.stereotype.Service;

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

    private void validate(CreateLotteryDto createLotteryDto) {
        for (char c : createLotteryDto.ticket().toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidRequestException("Ticket id must be numeric only");
            }
        }
    }
}
