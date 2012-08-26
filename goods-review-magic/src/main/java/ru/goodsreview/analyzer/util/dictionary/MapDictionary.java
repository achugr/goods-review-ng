package ru.goodsreview.analyzer.util.dictionary;

import org.apache.log4j.Logger;
import ru.goodsreview.core.util.FileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 14.05.12
 * Time: 00:10
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class MapDictionary implements KeyValueDictionary<String, Double> {
    private final static Logger log = Logger.getLogger(MapDictionary.class);
    private static final int WORD_INDEX = 0;
    private static final int WEIGHT_INDEX = 1;
    private final Map<String, Double> dictionary = new HashMap<String, Double>();

    public static MapDictionary getInstance(final String filePath) {
        MapDictionary mapDictionary = new MapDictionary();
        mapDictionary.initDictionary(filePath);
        return mapDictionary;
    }

    private void initDictionary(final String dictionaryFileName) {
        final List<String> fileLines = FileReader.readAsListOfLines(dictionaryFileName, MapDictionary.class);
        for(String line : fileLines){
            final String [] record = line.split("\\s");
            dictionary.put(record[WORD_INDEX], Double.parseDouble(record[WEIGHT_INDEX]));
        }
    }

    @Override
    public boolean contains(final String key) {
        return dictionary.containsKey(key);
    }

    @Override
    public Double getValue(final String key) {
        return dictionary.get(key);
    }

    public void print() {
        for (String word : dictionary.keySet()) {
            System.out.println(word + " " + dictionary.get(word));
        }
    }
}