package com.kcbierco.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcbierco.models.ExcelParsingConfig;

import java.io.File;
import java.io.IOException;

/**
 * Created by Richard on 1/31/16.
 */
public class ConfigLoader {

    public static ExcelParsingConfig loadTheConfig(String path) throws IOException {
        return new ObjectMapper().readValue(new File(path), ExcelParsingConfig.class);
    }
}
