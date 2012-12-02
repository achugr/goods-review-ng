package ru.goodsreview.analyzer.word.analyzer;


import ru.goodsreview.analyzer.util.sentence.Token;
import ru.goodsreview.analyzer.util.sentence.mystem.GrammarGender;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface WordAnalyzer {

    List<Token> getTokenList(String word);

    void close();

    String report(String word);

    public GrammarGender getGenger(String word);

}
