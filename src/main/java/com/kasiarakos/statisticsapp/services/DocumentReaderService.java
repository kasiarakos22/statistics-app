package com.kasiarakos.statisticsapp.services;

import java.io.IOException;

/**
 * This interface contains the  methods for reading the content of a file,
 * and the method for the file types that the implementation can handle
 */

public interface DocumentReaderService {

    String readContent( String fileName) throws IOException;
    String getFileExtension();

}
