package com.kasiarakos.statisticsapp.services.readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.kasiarakos.statisticsapp.services.DocumentReaderService;

@Service
public class TtxDocumentReaderService implements DocumentReaderService {

    private static final String FILE_EXTENSION = "txt";

    @Override
    public String readContent(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
