package ru.goodsreview.analyzer.util.sentence;

public enum PartOfSpeech {
    ADJECTIVE,
    NOUN,
    ADVERB,
    VERB,
    PARTICLE,
    PREPOSITION,
    UNKNOWN;


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
