package ru.goodsreview.analyzer.util.sentence.mystem;

import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;

/**
 * Created with IntelliJ IDEA.
 * Date: 17.08.12
 * Author: Ilya Makeev
 */
public enum GrammarGender {
    MASCULINE("муж"),
    FEMININE("жен"),
    NEUTER("сред"),
    UNKNOWN (MystemReportAnalyzer.UNKNOUN);

    private final String value;

    private GrammarGender(String value){
        this.value = value;
    }

    @Override //TODO над перегруженными методами лучше ставить аннотацию -- читается лучше
    public String toString(){
        return this.value;
    }
}
