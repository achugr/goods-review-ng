package ru.goodsreview.analyzer.util.dictionary;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.log4j.Logger;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import ru.goodsreview.analyzer.word.analyzer.MystemAnalyzer;
import ru.goodsreview.core.util.StringUtil;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * Date: 14.05.12
 * Time: 00:10
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class MapDictionary {
    private final static Logger log = Logger.getLogger(MapDictionary.class);

    private HashMap<String, Double> words;

    public MapDictionary(String dictionaryFileName, String encoding) {
        this.words = new HashMap<String, Double>();
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
                        words.put(word, positivity);
                    }

                }
                s = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }


    public HashMap<String, Double> getDictionary() {
        return this.words;
    }

    public void print() {
        for (String word : words.keySet()) {
            System.out.println(word + " " + words.get(word));
        }
    }

    /**
     * Checking if word is in dictionary
     *
     * @param word
     * @return true if word is here. false — otherwise
     */
    public boolean contains(String word) {
        return words.containsKey(word);
    }
}