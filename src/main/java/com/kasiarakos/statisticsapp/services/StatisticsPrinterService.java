package com.kasiarakos.statisticsapp.services;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

/**
 * This interface contains only the aggregate and print method which is responsible
 * to take  fileName and statistics in a map and produce an visual representation to the end user
 */

public interface StatisticsPrinterService {

    void aggregateAndPrint(Pair<String, Map<String, Object>> fileWithTheStatistics);

}
