package ru.goodsreview.analyzer.word.analyzer;


import ru.goodsreview.analyzer.util.sentence.Token;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface WordAnalyzer {

    List<Token> getTokenList(String word) throws UnsupportedEncodingException;

    void close();

    String report(String word);

}
