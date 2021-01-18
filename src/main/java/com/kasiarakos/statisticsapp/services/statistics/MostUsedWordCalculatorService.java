package com.kasiarakos.statisticsapp.services.statistics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kasiarakos.statisticsapp.services.StatisticCalculatorService;
/**
 * This class is an implementation of the  {@link StatisticCalculatorService}
 * which generates statistics about the most used word in  a text.
 */

@Service
public class MostUsedWordCalculatorService implements StatisticCalculatorService {

    private static final  String STATISTIC_NAME = "Most Used Word";

    @Override
    public Map<String, Object> calculate(String content) {
        Map<String, Object> result = new HashMap<>();
        String mostUsedWord = "";

        if(content != null && content.length() > 0) {
           Map<String, Long> wordsPerOccurrence = Arrays.stream(content.split(" ")).map(String::toLowerCase).collect(Collectors.groupingBy(Function.identity(), Collectors.counting() ));
           mostUsedWord = wordsPerOccurrence.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        }

        result.put(STATISTIC_NAME, mostUsedWord);
        return result;
    }
}
