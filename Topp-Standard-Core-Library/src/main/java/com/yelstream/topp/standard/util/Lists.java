package com.yelstream.topp.standard.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@UtilityClass
public class Lists {

    public static <T> List<T> createList(Iterable<T> iterable) {
        List<T> result=null;
        if (iterable!=null) {
            result=new ArrayList<>();
            iterable.forEach(result::add);
        }
        return result;
    }

    public static <T> List<T> createList(Iterator<T> iterator) {
        List<T> result=null;
        if (iterator!=null) {
            result=new ArrayList<>();
            iterator.forEachRemaining(result::add);
        }
        return result;
    }

    public static <T> List<T> createList(Spliterator<T> spliterator) {
        List<T> result=null;
        if (spliterator!=null) {
            result=StreamSupport.stream(spliterator,false).toList();
        }
        return result;
    }
}
