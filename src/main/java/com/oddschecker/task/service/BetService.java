package com.oddschecker.task.service;

import com.oddschecker.task.model.BetDao;

public interface BetService {

    BetDao findById(int betId);

    BetDao create();

}
