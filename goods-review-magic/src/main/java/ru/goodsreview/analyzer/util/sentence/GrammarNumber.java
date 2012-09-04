package ru.goodsreview.analyzer.util.sentence;

import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;

/**
 * Date: 13.07.12
 * Time: 23:45
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
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
