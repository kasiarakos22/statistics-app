package com.kasiarakos.statisticsapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.kasiarakos.statisticsapp.facade.NewFilesProcessorFacade;
import com.kasiarakos.statisticsapp.services.DocumentReaderService;
import com.kasiarakos.statisticsapp.services.StatisticCalculatorService;
import com.kasiarakos.statisticsapp.services.StatisticsPrinterService;
import com.kasiarakos.statisticsapp.tasks.CheckPathTask;


/**
 * This class is the entry point in the application it implements the spring interface CommandLineRunner
 * and initializes all the beans for use in the {@link NewFilesProcessorFacade} all the implementations of {@link DocumentReaderService}
 * and {@link StatisticCalculatorService} are used in the runtime to generate the appropriate output.
 *
 * when we add new implementations of these services and make them spring beans the application finds them and uses them for the
 * output of the app.
 *
 * The check in the specified path is done every minute.
 */
@SpringBootApplication
public class StatisticsAppApplication implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(StatisticsAppApplication.class, args);
    }

    public StatisticsAppApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {

        //todo remove this is there only for testing purposes
        String path =  "/Users/dimitriskasiaras/test";
        if(args.length > 0) {
            path = args[0];
        }

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        Map<String, DocumentReaderService> fileExtensionsToReaders = applicationContext.getBeansOfType(DocumentReaderService.class).values()
                                                                                       .stream()
                                                                                       .collect(Collectors.toMap(DocumentReaderService::getFileExtension, impl -> impl));

        List<StatisticCalculatorService> statisticCalculatorServices = new ArrayList<>(applicationContext.getBeansOfType(StatisticCalculatorService.class).values());
        StatisticsPrinterService statisticsPrinterService = applicationContext.getBean(StatisticsPrinterService.class);


        NewFilesProcessorFacade processorFacade = new NewFilesProcessorFacade(fileExtensionsToReaders, statisticCalculatorServices, statisticsPrinterService);

        Runnable task = new CheckPathTask(path,processorFacade);
        scheduledThreadPool.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }
}
