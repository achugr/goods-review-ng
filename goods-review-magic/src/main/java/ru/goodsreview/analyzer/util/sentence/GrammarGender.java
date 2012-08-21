package ru.goodsreview.analyzer.util.sentence;

import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;

/**
 * Created with IntelliJ IDEA.
 * Date: 17.08.12
 * Author: Ilya Makeev
 */
public enum GrammarGender {
    MASCULINE("муж"),
    FEMININE("жен"),
    NEUTER("сред"),
    UNKNOWN (ReportAnalyzer.UNKNOUN);

    private final String value;

    private GrammarGender(String value){
        this.value = value;
    }

    public String toString(){
        return this.value;
    }
}
