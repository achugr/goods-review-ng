package ru.goodsreview.analyzer.word.analyzer;

import ru.goodsreview.analyzer.util.sentence.*;


/**
 * Created with IntelliJ IDEA.
 * Date: 27.08.12
 * Author: Ilya Makeev
 */
public class LuceneReportAnalyzer {

    public static WordProperties wordProperties(String report) {
        WordProperties property = new WordProperties(GrammarGender.UNKNOWN, GrammarNumber.UNKNOWN, GrammarCase.UNKNOWN);

        if(report.contains("мр")){
             property.setGender(GrammarGender.MASCULINE);
        }
        if(report.contains("жр")){
            property.setGender(GrammarGender.FEMININE);
        }
        if(report.contains("ср")){
            property.setGender(GrammarGender.NEUTER);
        }

        if(report.contains("ед")){
            property.setNumber(GrammarNumber.SINGULAR);
        }
        if(report.contains("мн")){
            property.setNumber(GrammarNumber.PLURAL);
        }

        if(report.contains("им")){
            property.setCase(GrammarCase.IM);
        }
        if(report.contains("рд")){
            property.setCase(GrammarCase.ROD);
        }
        if(report.contains("вн")){
            property.setCase(GrammarCase.VIN);
        }
        if(report.contains("дт")){
            property.setCase(GrammarCase.DAT);
        }
        if(report.contains("тв")){
            property.setCase(GrammarCase.TVOR);
        }
        if(report.contains("пр")){
            property.setCase(GrammarCase.PRED);
        }

        return property;
    }


    public static String normalizer(String report)  {
        int n = report.indexOf("|");
        return report.substring(0,n);
    }

    public static PartOfSpeech partOfSpeech(String report)  {
        if(report.contains("С")){
            return PartOfSpeech.NOUN;
        }
        if(report.contains("П")||report.contains("КР_ПРИЛ")){
            return PartOfSpeech.ADJECTIVE;
        }
        if(report.contains("Г")){
            return PartOfSpeech.VERB;
        }
        if(report.contains("ПРЕДЛ")){
            return PartOfSpeech.PREPOSITION;
        }
        return PartOfSpeech.UNKNOWN;
    }
}
