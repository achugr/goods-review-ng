package ru.goodsreview.analyzer.word.analyzer;


import ru.goodsreview.analyzer.util.sentence.GrammarNumber;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsreview.analyzer.util.sentence.lucene.GrammarCase;
import ru.goodsreview.analyzer.util.sentence.lucene.GrammarGender;


/**
 * Created with IntelliJ IDEA.
 * Date: 27.08.12
 * Author: Ilya Makeev
 */
public class LuceneReportAnalyzer {


    public static GrammarGender getGender(String report) {
        GrammarGender[] genderCases = GrammarGender.values();
        for (GrammarGender gender : genderCases) {
            if (report.contains(gender.toString())) {
                return gender;
            }
        }

        return GrammarGender.UNKNOWN;
    }

    public static GrammarNumber getNum(String report) {
        GrammarNumber[] numCases = GrammarNumber.values();
        for (GrammarNumber num : numCases) {
            if (report.contains(num.toString())) {
                return num;
            }
        }

        return GrammarNumber.UNKNOWN;
    }


    public static GrammarCase getCase(String report) {
        GrammarCase[] cases = GrammarCase.values();
        for (GrammarCase grammarCase : cases) {
            if (report.contains(grammarCase.toString())) {
                return grammarCase;
            }
        }

        return GrammarCase.UNKNOWN;
    }


    public static String getNormForm(String report) {
        int n = report.indexOf("|");
        return report.substring(0, n);
    }

    public static PartOfSpeech getPartOfSpeech(String report) {
        int n = report.indexOf(" ");
        String partOfSpeech = report;
        if(n!=-1){
            partOfSpeech = report.substring(0, n);
        }

        return PartOfSpeech.getByName(partOfSpeech);
    }
}
