package com.oddschecker.task.integration;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddschecker.task.model.BetDao;
import com.oddschecker.task.model.dto.Odds;
import com.oddschecker.task.model.OddsDao;
import com.oddschecker.task.repository.BetRepository;
import com.oddschecker.task.repository.OddsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    OddsRepository oddsRepository;

    @MockBean
    BetRepository betRepository;

    public final static String USER_ID = UUID.randomUUID().toString();

    @Test
    public void postOdds_invalidBetId() throws Exception {
        Odds odds = new Odds(0, USER_ID, "2/3");
        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("invalid betId")))
                .andDo(print());
    }

    @Test
    public void postOdds_invalidUserId() throws Exception {
        Odds odds = new Odds(1, "", "2/3");

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("userId must not be blank")))
                .andDo(print());
    }

    @Test
    public void postOdds_invalidOdds_notFraction() throws Exception {
        Odds odds = new Odds(1, USER_ID, "0.5");

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("invalid odds")))
                .andDo(print());
    }

    @Test
    public void postOdds_invalidOdds_negative() throws Exception {
        Odds odds = new Odds(1, USER_ID, "-2/3");

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("invalid odds")))
                .andDo(print());
    }

    @Test
    public void postOdds_invalidOdds_1() throws Exception {
        Odds odds = new Odds(1, USER_ID, "0/1");

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("invalid odds")))
                .andDo(print());
    }

    @Test
    public void postOdds_invalidOdds_2() throws Exception {
        Odds odds = new Odds(1, USER_ID, "SP/1");

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("invalid odds")))
                .andDo(print());
    }

    @Test
    public void postOdds_invalidOdds_3() throws Exception {
        Odds odds = new Odds(1, USER_ID, "2/3/4");

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("invalid odds")))
                .andDo(print());
    }

    @Test
    public void postOdds_invalidOdds_4() throws Exception {
        Odds odds = new Odds(1, USER_ID, "23*4");

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("invalid odds")))
                .andDo(print());
    }

    @Test
    public void postOdds_validOdds_sp() throws Exception {
        Odds odds = new Odds(1, USER_ID, "SP");

        BetDao betDao = new BetDao();
        betDao.setId(1);

        OddsDao oddsDao = new OddsDao(betDao, USER_ID, "SP");

        when(betRepository.findById(1)).thenReturn(Optional.of(betDao));
        when(oddsRepository.save(any())).thenReturn(oddsDao);

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void postOdds_OddsAlreadyExist() throws Exception {
        Odds odds = new Odds(1, USER_ID, "3/4");

        BetDao betDao = new BetDao();
        betDao.setId(1);

        when(betRepository.findById(1)).thenReturn(Optional.of(betDao));
        when(oddsRepository.existsByBetAndAndUserIdAndOdds(any(), any(), any())).thenReturn(true);

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("These odds already exist")))
                .andDo(print());
    }

    @Test
    public void postOdds_betNotFound() throws Exception {
        Odds odds = new Odds(1, USER_ID, "3/4");

        when(betRepository.findById(1)).thenReturn(Optional.empty());

        this.mockMvc.perform(post("/odds").
                content(mapper.writeValueAsString(odds)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Bet 1 does not exist")))
                .andDo(print());
    }


}