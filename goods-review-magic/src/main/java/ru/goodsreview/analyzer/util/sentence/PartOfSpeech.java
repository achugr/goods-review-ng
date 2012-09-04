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
        if (name.equals("П")||name.equals("КР_ПРИЛ")) {
            return ADJECTIVE;
        } else if (name.equals("С")) {
            return NOUN;
        } else if (name.equals("Г")) {
            return VERB;
        }   else {
            return UNKNOWN;
        }
    }
}
