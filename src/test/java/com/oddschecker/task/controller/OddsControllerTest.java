package com.oddschecker.task.controller;

import com.oddschecker.task.model.dto.Odds;
import com.oddschecker.task.service.OddsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OddsControllerTest {

    @Mock
    private OddsService oddsService;

    private OddsController controller;

    private final static String USER_ID = UUID.randomUUID().toString();
    private final static String ODDS = "2/3";

    @BeforeEach
    void setUp() {
        controller = new OddsController(oddsService);
    }

    @Test
    public void shouldReturnOddsByBetId() {
        when(oddsService.findByBetId(1)).thenReturn(List.of(new Odds(1, USER_ID, ODDS)));
        List<Odds> response = controller.getOddsByBetId(1);

        assertEquals(1, response.size());
        assertEquals(1, response.get(0).getBetId());
        assertEquals("2/3", response.get(0).getOdds());
    }

    @Test
    public void findOddsByBetId_shouldReturnEmptyListIfNoResults() {
        when(oddsService.findByBetId(1)).thenReturn(List.of());
        List<Odds> response = controller.getOddsByBetId(1);

        assertEquals(0, response.size());
    }

    @Test
    public void offerOdds_HappyPath() {
        when(oddsService.offerOdds(1, USER_ID, ODDS)).thenReturn(new Odds(1, USER_ID, ODDS));
        Odds response = controller.offerOdds(new Odds(1, USER_ID, ODDS));

        assertEquals(1, response.getBetId());
        assertEquals(USER_ID, response.getUserId());
        assertEquals(ODDS, response.getOdds());
    }
}
