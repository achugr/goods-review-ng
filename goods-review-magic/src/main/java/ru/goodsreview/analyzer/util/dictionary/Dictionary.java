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

public class Dictionary {
    private final static Logger log = Logger.getLogger(Dictionary.class);

    private HashSet<String> words;

    public Dictionary(String dictionaryFileName, String encoding) {
        this.words = new HashSet<String>();

        InputStream inputStream = null;
        try {
            inputStream = Dictionary.class.getResourceAsStream(dictionaryFileName);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));

            String s = bufferedReader.readLine();
            while (s != null) {
                s = s.trim();
                if (s.length() != 0) {
                    /*if(s.charAt(0)=='﻿') {
                        s = s.substring(1);
                    }*/
                    if (s.contains(" ")) {
                        words.add(s.substring(0, s.indexOf(" ")));
                    } else {
                        words.add(s);
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


    public HashSet<String> getDictionary() {
        return this.words;
    }

    public void print() {
        for (String word : words) {
            System.out.println(word);
        }
    }

    /**
     * Checking if word is in dictionary
     *
     * @param word
     * @return true if word is here. false — otherwise
     */
    public boolean contains(String word) {
        return words.contains(word);
    }

}
