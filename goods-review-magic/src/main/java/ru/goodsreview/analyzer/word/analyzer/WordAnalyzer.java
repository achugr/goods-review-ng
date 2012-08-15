package ru.goodsreview.analyzer.word.analyzer;

import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;

public interface WordAnalyzer {

    //TODO в интерфейсах и так все методы паблик, спасибо кэп
    public PartOfSpeech partOfSpeech(String word);

//    TODO add methods, gets other useful features by word ru.goodsreview.analyzer programs
}
