package com.kasiarakos.statisticsapp.facade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.kasiarakos.statisticsapp.services.DocumentReaderService;
import com.kasiarakos.statisticsapp.services.StatisticCalculatorService;
import com.kasiarakos.statisticsapp.services.StatisticsPrinterService;

/**
 *This is the most complex class and contains the whole logic fot the application it makes the orchestration of the services.
 *
 */

//todo make a refactoring and extract an interface in order to be able to replace it later
public class NewFilesProcessorFacade {


    private final Map<String, DocumentReaderService> documentReaderMap;
    private final List<StatisticCalculatorService> statisticCalculatorServices;
    private final StatisticsPrinterService printerService;

    public NewFilesProcessorFacade(
        Map<String, DocumentReaderService> documentReaderMap, List<StatisticCalculatorService> statisticCalculatorServices,
        StatisticsPrinterService printerService) {
        this.documentReaderMap = documentReaderMap;
        this.statisticCalculatorServices = statisticCalculatorServices;
        this.printerService = printerService;

    }

    public void readAndCalculateStatistics(String path) {

        try (Stream<Path> paths = Files.list(Paths.get(path))) {
            paths
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .map(this::readDocumentPerType)
                .filter(pair -> this.weCanHandleThisFileType(pair.getLeft()))
                .map(this::calculateStatistics)
                .peek(printerService::aggregateAndPrint)
                .forEach(fileWithStatistics -> handleFileAfterProcess(fileWithStatistics.getLeft(), Boolean.TRUE));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private org.apache.commons.lang3.tuple.Pair<String, String> readDocumentPerType(String path) {
        String fileExtension = FilenameUtils.getExtension(path);
        String fileContent = "";
        if(documentReaderMap.containsKey(fileExtension)) {
            try {
                fileContent = documentReaderMap.get(fileExtension).readContent(path);
            } catch (IOException e) {
                System.err.println("something did not well while processing: "+path);
            }
        } else {
            System.err.println("we cannot handle this type of files: "+path);
            handleFileAfterProcess(path, Boolean.FALSE);
        }
        return Pair.of(path, fileContent);
    }

    //todo refactor this and previous method to avoid duplicate code
    private boolean weCanHandleThisFileType(String path){
        String fileExtension = FilenameUtils.getExtension(path);
        return documentReaderMap.containsKey(fileExtension);
    }

    private Pair<String, Map<String, Object>> calculateStatistics(Pair<String, String> fileWithContent ) {
        String content = fileWithContent.getRight();
        Map<String, Object> statistics = new HashMap<>();
        for (StatisticCalculatorService statisticCalculatorService : statisticCalculatorServices) {
            statistics.putAll(statisticCalculatorService.calculate(content));
        }

        return Pair.of(fileWithContent.getLeft(), statistics);
    }


    //todo check for name collisions
    private void handleFileAfterProcess(String path, boolean success) {
        Path fileToBeMoved = Paths.get(path);
        String subPathStr = success ? "processed" : "failed";

        Path fileName = fileToBeMoved.getFileName();
        Path fileFolder = fileToBeMoved.getParent();

        if(!Files.exists(fileFolder.resolve(subPathStr))){
            try {
                Files.createDirectories(fileFolder.resolve(subPathStr ));
            } catch (IOException e) {
                System.err.println("There was a problem creating the subfolder "+fileFolder.resolve(subPathStr ));
            }
        }

        Path targetPath = fileFolder.resolve(subPathStr).resolve(fileName);
        try {
            Files.move(fileToBeMoved, targetPath);
        } catch (IOException e) {
            System.err.println("There was a problem moving the file "+fileFolder.resolve(subPathStr )+" please make manual actions to avoid reprocessing of the file");
        }
    }

}
