package com.oddschecker.task.service;


import com.oddschecker.task.exception.OddsAlreadyExistException;
import com.oddschecker.task.model.BetDao;
import com.oddschecker.task.model.dto.Odds;
import com.oddschecker.task.model.OddsDao;
import com.oddschecker.task.repository.OddsRepository;
import com.oddschecker.task.util.ConverterUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.oddschecker.task.util.ConverterUtil.convertsOddsDaoToOdds;

@Service
@AllArgsConstructor
@Slf4j
public class OddsServiceImpl implements OddsService {

    private final OddsRepository oddsRepository;

    private final BetService betService;

    @Override
    public List<Odds> findByBetId(int betId) {
        return oddsRepository.findByBetId(betId).stream()
                .map(ConverterUtil::convertsOddsDaoToOdds).collect(Collectors.toList());
    }

    @Override
    public Odds offerOdds(int betId, String userId, String odds) {
        BetDao bet = betService.findById(betId);
        log.info("Found bet {}", bet.getId());

        if (oddsRepository.existsByBetAndAndUserIdAndOdds(bet, userId, odds)) {
            log.info("Rejecting these odds - they are a duplicate");
            throw new OddsAlreadyExistException();
        }

        OddsDao oddsDao = new OddsDao(bet, userId, odds);
        log.info("Saving odds");
        return convertsOddsDaoToOdds(oddsRepository.save(oddsDao));
    }


}
