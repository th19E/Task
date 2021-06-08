package com.oddschecker.task.controller;

import com.oddschecker.task.model.dto.Bet;
import com.oddschecker.task.model.BetDao;
import com.oddschecker.task.service.BetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BetControllerTest {

    @Mock
    private BetService betService;

    private BetController controller;

    @BeforeEach
    void setUp() {
        controller = new BetController(betService);
    }

    @Test
    public void shouldReturnValueFromService() {
        BetDao bet = new BetDao();
        bet.setId(1);
        when(betService.create()).thenReturn(bet);

        Bet expectedResponse = new Bet(1);
        Bet response = controller.createBet();
        assertEquals(expectedResponse.getId(), response.getId());
    }
}
