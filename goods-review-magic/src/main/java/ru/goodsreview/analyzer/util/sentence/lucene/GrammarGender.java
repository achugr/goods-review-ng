package ru.goodsreview.analyzer.util.sentence.lucene;

import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;

/**
 * author : Ilya Makeev
 * date: 30.08.12
 */
public enum GrammarGender {
    MASCULINE("мр"),
    FEMININE("жр"),
    NEUTER("ср"),
    UNKNOWN (MystemReportAnalyzer.UNKNOUN);

    private final String value;

    private GrammarGender(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
