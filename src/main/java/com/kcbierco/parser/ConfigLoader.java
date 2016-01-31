package com.kcbierco.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcbierco.models.ExcelParsingConfig;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Richard on 1/31/16.
 */
public class ConfigLoader {
    private static final String configName = "config.json";

    public static ExcelParsingConfig loadTheConfig() throws IOException {
        URL filePath = ConfigLoader.class.getClassLoader().getResource(configName);

        return new ObjectMapper().readValue(new File(filePath.getFile()), ExcelParsingConfig.class);
    }
}
