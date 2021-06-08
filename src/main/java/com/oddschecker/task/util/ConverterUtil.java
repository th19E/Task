package com.oddschecker.task.util;

import com.oddschecker.task.model.dto.Odds;
import com.oddschecker.task.model.OddsDao;

public final class ConverterUtil {

    private ConverterUtil() {
    }

    public static Odds convertsOddsDaoToOdds(OddsDao oddsDao) {
        return new Odds(oddsDao.getBet().getId(), oddsDao.getUserId(), oddsDao.getOdds());
    }
}
