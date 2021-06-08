package com.oddschecker.task.service;

import com.oddschecker.task.exception.ResourceNotFoundException;
import com.oddschecker.task.model.BetDao;
import com.oddschecker.task.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetServiceImpl implements BetService {

    @Autowired
    private BetRepository betRepository;

    @Override
    public BetDao findById(int betId) {
        return betRepository.findById(betId).orElseThrow(() -> new ResourceNotFoundException(String.format("Bet %d does not exist", betId)));
    }

    @Override
    public BetDao create() {
        return betRepository.save(new BetDao());
    }
}
