package com.oddschecker.task.repository;

import com.oddschecker.task.model.BetDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<BetDao, Integer> {

}
