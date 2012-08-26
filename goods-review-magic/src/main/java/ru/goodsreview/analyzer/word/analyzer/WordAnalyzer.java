package ru.goodsreview.analyzer.word.analyzer;

import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;

public interface WordAnalyzer {

    PartOfSpeech partOfSpeech(String word);

//    TODO add methods, gets other useful features by word ru.goodsreview.analyzer programs
}
