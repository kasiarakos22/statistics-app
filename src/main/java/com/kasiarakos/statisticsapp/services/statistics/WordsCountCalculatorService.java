package com.kasiarakos.statisticsapp.services.statistics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kasiarakos.statisticsapp.services.StatisticCalculatorService;

/**
 * This class is an implementation of the  {@link StatisticCalculatorService}
 * which generates statistics about the number of words in  a text.
 */

@Service
public class WordsCountCalculatorService implements StatisticCalculatorService {

    private static final  String STATISTIC_NAME = "Words Count";

    @Override
    public Map<String, Object> calculate(String content) {
        Map<String, Object> result = new HashMap<>();
        long wordsCount = 0;

        if(content != null && content.length() > 0) {
            wordsCount = Arrays.stream(content.split(" ")).count();
        }
        result.put(STATISTIC_NAME, wordsCount);
        return result;
    }
}
