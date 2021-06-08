package com.oddschecker.task.service;

import com.oddschecker.task.model.dto.Odds;

import java.util.List;

public interface OddsService {

    List<Odds> findByBetId(int betId);

    Odds offerOdds(int betId, String userId, String odds);

}
