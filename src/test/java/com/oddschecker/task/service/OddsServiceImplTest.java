package com.oddschecker.task.service;

import com.oddschecker.task.exception.OddsAlreadyExistException;
import com.oddschecker.task.exception.ResourceNotFoundException;
import com.oddschecker.task.model.BetDao;
import com.oddschecker.task.model.dto.Odds;
import com.oddschecker.task.model.OddsDao;
import com.oddschecker.task.repository.OddsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OddsServiceImplTest {

    @Mock
    private OddsRepository oddsRepository;

    @Mock
    private BetService betService;

    private OddsServiceImpl oddsService;

    @BeforeEach
    public void setup() {
        oddsService = new OddsServiceImpl(oddsRepository, betService);
    }

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String ODDS = "2/3";

    @Test
    public void shouldFindByBetId() {
        BetDao betDao = new BetDao();
        betDao.setId(1);

        when(oddsRepository.findByBetId(1)).thenReturn(List.of(new OddsDao(betDao, USER_ID, ODDS)));

        List<Odds> odds = oddsService.findByBetId(1);

        assertEquals(1, odds.size());
        assertEquals(1, odds.get(0).getBetId());
        assertEquals("2/3", odds.get(0).getOdds());
    }

    @Test
    public void findByBetId_EmptyList() {
        BetDao betDao = new BetDao();
        betDao.setId(1);

        when(oddsRepository.findByBetId(1)).thenReturn(List.of());

        List<Odds> odds = oddsService.findByBetId(1);

        assertEquals(0, odds.size());
    }

    @Test
    public void offerOdds_betDoesntExist() {
        when(betService.findById(1)).thenThrow(new ResourceNotFoundException("Bet 1 doesn't exist"));

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            oddsService.offerOdds(1, USER_ID, ODDS);
        });
    }

    @Test
    public void offerOdds_OddsAlreadyExist() {
        BetDao betDao = new BetDao();
        betDao.setId(1);

        when(betService.findById(1)).thenReturn(betDao);
        when(oddsRepository.existsByBetAndAndUserIdAndOdds(any(), any(), any())).thenReturn(true);

        Assertions.assertThrows(OddsAlreadyExistException.class, () -> {
            oddsService.offerOdds(1, USER_ID, ODDS);
        });
    }

}
