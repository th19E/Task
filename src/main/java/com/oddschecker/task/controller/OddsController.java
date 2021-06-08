package com.oddschecker.task.controller;

import com.oddschecker.task.model.dto.Odds;
import com.oddschecker.task.service.OddsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/odds")
@Slf4j
@AllArgsConstructor
@Validated
public class OddsController {

    private final OddsService oddsService;

    @GetMapping("/{betId}")
    public List<Odds> getOddsByBetId(@PathVariable(value =  "betId") int betId) {
        log.info("Finding odds for bet: {}", betId);
        return oddsService.findByBetId(betId);
    }

    @PostMapping
    public Odds offerOdds(@Valid @RequestBody Odds odds) {
        log.info("Offering odds: {}", odds);
        return oddsService.offerOdds(odds.getBetId(), odds.getUserId(), odds.getOdds());
    }

}

