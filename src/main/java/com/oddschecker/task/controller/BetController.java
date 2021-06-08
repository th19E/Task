package com.oddschecker.task.controller;

import com.oddschecker.task.model.dto.Bet;
import com.oddschecker.task.model.BetDao;
import com.oddschecker.task.service.BetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bets")
@Slf4j
@AllArgsConstructor
public class BetController {

    private final BetService betService;

    @PostMapping
    public Bet createBet() {
        log.info("Creating bet");
        BetDao betDao = betService.create();
        log.info("Created bet with id: {}", betDao.getId());
        return new Bet(betDao.getId());
    }
}

