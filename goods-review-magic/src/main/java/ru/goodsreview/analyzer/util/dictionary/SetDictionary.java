package ru.goodsreview.analyzer.util.dictionary;
/*
 *  Date: 08.02.12
 *   Time: 18:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class SetDictionary implements Dictionary {
    private final static Logger log = Logger.getLogger(SetDictionary.class);

    private final HashSet<String> dictionary;

    public SetDictionary(final String dictionaryFileName, final String encoding) {
        this.dictionary = new HashSet<String>();
        readDictionaryFromFile(dictionaryFileName, encoding);
    }

    private void readDictionaryFromFile(final String dictionaryFileName, final String encoding) {
        InputStream inputStream = null;
        try {
            inputStream = SetDictionary.class.getResourceAsStream(dictionaryFileName);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));

            String s = bufferedReader.readLine();
            while (s != null) {
                s = s.trim();
                if (s.length() != 0) {
                    /*if(s.charAt(0)=='﻿') {
                        s = s.substring(1);
                    }*/
                    if (s.contains(" ")) {
                        dictionary.add(s.substring(0, s.indexOf(" ")));
                    } else {
                        dictionary.add(s);
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


    public Set<String> getDictionary() {
        return this.dictionary;
    }

    public void print() {
        for (String word : dictionary) {
            System.out.println(word);
        }
    }

    /**
     * Checking if key is in dictionary
     *
     * @param key
     * @return true if key is here. false — otherwise
     */
    @Override
    public boolean contains(Object key) {
        if (key instanceof String) {
            return dictionary.contains(key);
        } else {
            throw new IllegalArgumentException("key must be String");
        }
    }

}
