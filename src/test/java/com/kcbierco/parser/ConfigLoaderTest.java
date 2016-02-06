package com.kcbierco.parser;

import com.kcbierco.fixture.ResourceFileFinder;
import com.kcbierco.models.ExcelParsingConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ConfigLoaderTest {

    @Test
    public void testLoadTheConfig() throws IOException, ExcelFormatNotSupportedException {
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(ResourceFileFinder.findFileInResources("config.json"));
        Assert.assertNotNull(excelParsingConfig);
        Assert.assertEquals(2, excelParsingConfig.getImportantDatesToWatch().size());
    }

}
