package ru.goodsreview.analyzer.util.sentence.mystem;


import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;

public enum PartOfSpeech {
    ADJECTIVE("A"),
    NOUN("S"),
    ADVERB("ADV"),
    VERB("V"),
    PARTICLE("PART"),
    PREPOSITION("PR"),
    UNKNOWN(MystemReportAnalyzer.UNKNOUN);

    private final String value;

    private PartOfSpeech(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }

}
