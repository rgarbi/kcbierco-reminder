package com.kcbeirco.fixture;

/**
 * Created by Richard on 1/30/16.
 */
public class ResourceFileFinder {

    public static String findFileInResources(String name){
        return ResourceFileFinder.class.getResource("/com/kcbeirco/" + name).getFile();
    }

}
