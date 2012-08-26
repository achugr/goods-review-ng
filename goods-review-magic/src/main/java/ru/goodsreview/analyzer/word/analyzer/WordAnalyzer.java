package ru.goodsreview.analyzer.word.analyzer;


import ru.goodsreview.analyzer.util.sentence.Token;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public interface WordAnalyzer {

    ArrayList<Token> getTokenList(String word) throws UnsupportedEncodingException;

//    TODO add methods, gets other useful features by word ru.goodsreview.analyzer programs
}
