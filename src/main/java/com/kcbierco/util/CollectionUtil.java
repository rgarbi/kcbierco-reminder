package com.kcbierco.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 2/2/16.
 */
public class CollectionUtil {

    public static <T> List<T> iterableToCollection(Iterable<T> iterable){
        List<T> collection = new ArrayList<>();

        for(T item : iterable){
            collection.add(item);
        }

        return collection;

    }
}
