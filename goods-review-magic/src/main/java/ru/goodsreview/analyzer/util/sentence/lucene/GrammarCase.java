package ru.goodsreview.analyzer.util.sentence.lucene;

import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;

/**
 * author : Ilya Makeev
 * date: 30.08.12
 */
public enum GrammarCase {
    IM("им"),
    ROD("рд"),
    DAT("дт"),
    VIN("вн"),
    TVOR("тв"),
    PRED("пр"),
    UNKNOWN (MystemReportAnalyzer.UNKNOUN);

    private final String value;

    private GrammarCase(String value){
        this.value = value;
    }

    public String toString(){
        return this.value;
    }
}

