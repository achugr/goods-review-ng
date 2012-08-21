package ru.goodsreview.analyzer.util.sentence;

import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;

/**
 * Date: 17.07.12
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

public enum GrammarCase {
    IM("им"),
    ROD("род"),
    DAT("дат"),
    VIN("вин"),
    TVOR("твор"),
    PRED("пр"),
    PART("парт"),
    MESTN("местн"),
    ZVAT("зват"),
    UNKNOWN (ReportAnalyzer.UNKNOUN);

    private final String value;

    private GrammarCase(String value){
        this.value = value;
    }

    public String toString(){
        return this.value;
    }
}

