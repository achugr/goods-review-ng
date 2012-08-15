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
public class MapDictionary implements Dictionary {
    private final static Logger log = Logger.getLogger(MapDictionary.class);

    //TODO оно не HashMap а просто Map
    private final HashMap<String, Double> dictionary;

    public MapDictionary(final String dictionaryFileName, final String encoding) {
        this.dictionary = new HashMap<String, Double>();
        readDictionaryFromFile(dictionaryFileName, encoding);
    }

    //TODO копипаста одна везде и всюду
    private void readDictionaryFromFile(final String dictionaryFileName, final String encoding) {
        InputStream inputStream = null;
        try {
            inputStream = MapDictionary.class.getResourceAsStream(dictionaryFileName);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
            String s = bufferedReader.readLine();
            //TODO y BufferedReader есть метод ready()
            while (s != null) {
                s = s.trim();
                if (s.length() != 0) {

                    //TODO трим все и так делает
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

    //TODO нахера этот метод? говнодизайн
    public Map<String, Double> getDictionary() {
        return this.dictionary;
    }

    //TODO wtf?
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
        //TODO реализация не лучше сигнатуры метода
        if (key instanceof String) {
            return dictionary.containsKey(key);
        } else {
            throw new IllegalArgumentException("key must be String");
        }
    }
}