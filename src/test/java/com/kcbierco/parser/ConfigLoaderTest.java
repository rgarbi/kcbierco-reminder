package com.kcbierco.parser;

import com.kcbierco.models.ExcelParsingConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ConfigLoaderTest {

    @Test
    public void testLoadTheConfig() throws IOException, ExcelFormatNotSupportedException {
        List<ExcelParsingConfig> excelParsingConfig = ConfigLoader.loadTheConfig();
        Assert.assertNotNull(excelParsingConfig);
        Assert.assertEquals(1, excelParsingConfig.size());
    }

}
