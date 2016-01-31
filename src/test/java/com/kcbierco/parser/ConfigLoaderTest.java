package com.kcbierco.parser;

import com.kcbierco.models.ExcelParsingConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ConfigLoaderTest {

    @Test
    public void testLoadTheConfig() throws IOException, ExcelFormatNotSupportedException {
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig();
        Assert.assertNotNull(excelParsingConfig);
    }

}
