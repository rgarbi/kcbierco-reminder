package com.kcbierco.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 1/31/16.
 */
public class FileManager {
    private static final String XLS_EXT = "xls";
    private static final String XLSX_EXT = "xlsx";

    public List<String> getAllExcelFilesInADirectory(String directoryPath) throws IOException {
        List<String> excelFilePaths = new ArrayList<>();

        Files.newDirectoryStream(Paths.get(directoryPath)).forEach(path -> {
            String extension = getExtensionFromPath(path).toLowerCase();
            if(extension.equals(XLS_EXT) || extension.equals(XLSX_EXT)){
                excelFilePaths.add(path.toAbsolutePath().toString());
            }
        });

        return excelFilePaths;
    }


    protected String getExtensionFromPath(Path path){
        return path.toString().substring(path.toString().lastIndexOf(".") + 1, path.toString().length());
    }
}
