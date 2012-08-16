package ru.goodsreview.analyzer.util.sentence;

/**
 * Date: 17.07.12
 * Time: 01:00
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
    ZVAT("зват");

    private final String value;

    private GrammarCase(String value){
        this.value = value;
    }

    public String toString(){
        return this.value;
    }
}

