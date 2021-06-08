package com.oddschecker.task.repository;

import com.oddschecker.task.model.BetDao;
import com.oddschecker.task.model.OddsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OddsRepository extends JpaRepository<OddsDao, Integer> {

	List<OddsDao> findByBetId(Integer betId);

	boolean existsByBetAndAndUserIdAndOdds(BetDao bet, String userId, String odds);

}
