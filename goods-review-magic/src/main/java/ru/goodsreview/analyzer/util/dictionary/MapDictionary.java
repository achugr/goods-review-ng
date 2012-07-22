package ru.goodsreview.analyzer.util.dictionary;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//import ru.goodsreview.core.util.StringUtil;

/**
 * Date: 14.05.12
 * Time: 00:10
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class MapDictionary implements DictionaryInterface {
    private final static Logger log = Logger.getLogger(MapDictionary.class);

    private final HashMap<String, Double> dictionary;

    public MapDictionary(final String dictionaryFileName, final String encoding) {
        this.dictionary = new HashMap<String, Double>();
        readDictionaryFromFile(dictionaryFileName, encoding);
    }

    private void readDictionaryFromFile(final String dictionaryFileName, final String encoding) {
        InputStream inputStream = null;
        try {
            inputStream = MapDictionary.class.getResourceAsStream(dictionaryFileName);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
            String s = bufferedReader.readLine();
            while (s != null) {
                s = s.trim();
                if (s.length() != 0) {

                    if (s.contains(" ")) {
                        int n = s.indexOf(" ");
                        String word = s.substring(0, n);
                        Double positivity = Double.parseDouble(s.substring(n + 1));
                        dictionary.put(word, positivity);
                    }

                }
                s = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public Map<String, Double> getDictionary() {
        return this.dictionary;
    }

    public void print() {
        for (String word : dictionary.keySet()) {
            System.out.println(word + " " + dictionary.get(word));
        }
    }

    /**
     * Checking if word is in dictionary
     *
     * @param key
     * @return true if word is here. false — otherwise
     */
    public boolean contains(Object key) {
        if (key instanceof String) {
            return dictionary.containsKey(key);
        } else {
            throw new IllegalArgumentException("key must be String");
        }
    }
}