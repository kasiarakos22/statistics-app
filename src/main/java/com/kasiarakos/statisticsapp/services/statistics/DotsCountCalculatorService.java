package com.kasiarakos.statisticsapp.services.statistics;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kasiarakos.statisticsapp.services.StatisticCalculatorService;

/**
 * This class is an implementation of the  {@link StatisticCalculatorService}
 * which generates statistics about the number of dots in  a text.
 */

@Service
public class DotsCountCalculatorService implements StatisticCalculatorService {

    private static final  String STATISTIC_NAME = "Dots Count";

    @Override
    public Map<String, Object> calculate(String content) {
        Map<String, Object> result = new HashMap<>();

        long dotsCount =   content.length() - content.replaceAll("[.]", "").length();
        result.put(STATISTIC_NAME ,  dotsCount);

        return result;
    }
}
