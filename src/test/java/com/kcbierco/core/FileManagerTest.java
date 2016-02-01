package com.kcbierco.core;

import com.kcbierco.fixture.ResourceFileFinder;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class FileManagerTest {

    @Test
    public void testGetExtensionFromPath(){
        Path path = Paths.get(ResourceFileFinder.findFileInResources("sometext.txt"));
        Assert.assertEquals("txt", new FileManager().getExtensionFromPath(path));
    }

    @Test
    public void testGetExtensionFromPathExcel(){
        Path path = Paths.get(ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx"));
        Assert.assertEquals("xlsx", new FileManager().getExtensionFromPath(path));
    }

    @Test
    public void testGetAllExcelFilesInADirectory() throws IOException {
        Path path = Paths.get(ResourceFileFinder.findFileInResources("sometext.txt"));
        List<String> allExcelFiles = new FileManager()
                .getAllExcelFilesInADirectory(path.getParent().toAbsolutePath().toString());
        Assert.assertTrue(allExcelFiles.size() > 0);
    }
}
