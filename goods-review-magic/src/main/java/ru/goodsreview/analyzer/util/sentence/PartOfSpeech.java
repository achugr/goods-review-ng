package ru.goodsreview.analyzer.util.sentence;


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

    public static PartOfSpeech getByName(String name) {
        if (name.equals("A")) {
            return ADJECTIVE;
        } else if (name.equals("S")) {
            return NOUN;
        } else if (name.equals("ADV")) {
            return ADVERB;
        } else if (name.equals("V")) {
            return VERB;
        } else if (name.equals("PR")) {
            return PREPOSITION;
        } else if (name.equals("PART")) {
            return PARTICLE;
        } else if (name.equals("")) {
            return UNKNOWN;
        } else {
            return UNKNOWN;
        }
    }
}
