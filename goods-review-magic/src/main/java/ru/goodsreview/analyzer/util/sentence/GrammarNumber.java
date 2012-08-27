package ru.goodsreview.analyzer.util.sentence;

import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;

/**
 * Created with IntelliJ IDEA.
 * Date: 17.08.12
 * Author: Ilya Makeev
 */
public enum GrammarNumber {
    SINGULAR("ед"),
    PLURAL("мн"),
    UNKNOWN (MystemReportAnalyzer.UNKNOUN);

    private final String value;

    private GrammarNumber(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}