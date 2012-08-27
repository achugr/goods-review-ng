package ru.goodsreview.analyzer.util.dictionary;
/*
 *  Date: 08.02.12
 *   Time: 18:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;

import ru.goodsreview.core.util.FileReader;



import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetDictionary implements Dictionary<String> {
    private final static Logger log = Logger.getLogger(SetDictionary.class);

    private final Set<String> dictionary = new HashSet<String>();

    public static SetDictionary getInstance(final String filePath) {
        SetDictionary setDictionary = new SetDictionary();
        setDictionary.initDictionary(filePath);
        return setDictionary;
    }

    private void initDictionary(final String filePath) {
        List<String> fileLines = FileReader.readAsListOfLines(filePath, SetDictionary.class);
        for (String line : fileLines) {
            dictionary.add(line);
        }
    }

    @Override
    public boolean contains(String key) {
        return dictionary.contains(key);
    }

    public void print() {
        for (String word : dictionary) {
            System.out.println(word);
        }
    }


}