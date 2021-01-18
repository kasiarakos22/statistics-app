package com.kasiarakos.statisticsapp.services;

import java.util.Map;

/**
 * This interface contains only the calculate method which takes as argument
 * a text content and returns a string identifier for the type of the statistics that computed
 * and the actual  calculation
 */

public interface StatisticCalculatorService {

    Map<String, Object> calculate(String content);

}
