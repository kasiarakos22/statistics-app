package com.kasiarakos.statisticsapp.services.impl;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.kasiarakos.statisticsapp.services.StatisticsPrinterService;

/**
 * This class is a basic implementation of the  {@link StatisticsPrinterService}
 * for displaying the statistics of a file to the standard output of the user
 */

@Service
public class StatisticsPrinterServiceImpl implements StatisticsPrinterService {

    @Override
    public void aggregateAndPrint(Pair<String, Map<String, Object>> fileWithTheStatistics) {

        StringBuilder builder = new StringBuilder();
        builder.append("The statistics for the file ").append(fileWithTheStatistics.getLeft()).append(" are:").append("\n\n");
        fileWithTheStatistics.getRight().forEach((k,v)-> builder.append(k).append(" -> ").append(v).append("\n"));
        builder.append("\n\n\n");
        System.out.println(builder.toString());

    }
}
