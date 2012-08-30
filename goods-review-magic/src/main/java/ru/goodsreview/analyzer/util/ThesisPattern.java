package ru.goodsreview.analyzer.util;
/*
 *  Date: 12.02.12
 *   Time: 15:36
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class to represent thesis patterns
 */
public final class ThesisPattern<T> {
//    list of part of speech - this is pattern
   private final List<T> pattern = new ArrayList<T>();

    public ThesisPattern(final T... parts){
        Collections.addAll(pattern, parts);
    }

    public T get(final int i){
//       TODO i don't think it's needed
        if(i >= pattern.size()){
            throw new IndexOutOfBoundsException("pattern has only " + pattern.size() + " elements");
        }
        return pattern.get(i);
    }

}
