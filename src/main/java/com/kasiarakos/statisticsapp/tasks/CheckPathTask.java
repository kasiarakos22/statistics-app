package com.kasiarakos.statisticsapp.tasks;

import com.kasiarakos.statisticsapp.facade.NewFilesProcessorFacade;

/**
 * This class is the task that is fed in a Service Executor which
 * schedules a check in the path and handles all new files with the {@link NewFilesProcessorFacade}
 */

public class CheckPathTask implements Runnable{

    private final String path;
    private final NewFilesProcessorFacade processor;

    public CheckPathTask(String path, NewFilesProcessorFacade processor) {
        this.path = path;
        this.processor = processor;
    }

    @Override
    public void run() {
        processor.readAndCalculateStatistics(path);
    }
}
