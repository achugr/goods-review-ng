package ru.goodsreview.analyzer.util;
/*
 *  Date: 12.02.12
 *   Time: 15:36
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class to represent thesis patterns
 */
//TODO fix this class. are mystemAnalyzer used here? why?
public class ThesisPattern {
//    list of part of speech - this is pattern
   private ArrayList<PartOfSpeech> pattern;

    public List<PartOfSpeech> getPattern(){
        return pattern;
    }

    /**
     * create new pattern
     * @param p1 first token of pattern
     * @param p2 second token of pattern
     */
    public ThesisPattern(PartOfSpeech p1, PartOfSpeech p2) throws IOException {
        pattern = new ArrayList<PartOfSpeech>();
        pattern.add(p1);
        pattern.add(p2);
    }

    public PartOfSpeech get(int i){
        return pattern.get(i);
    }

}
