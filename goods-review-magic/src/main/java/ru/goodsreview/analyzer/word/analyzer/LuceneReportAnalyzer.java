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
public class LuceneReportAnalyzer extends ReportAnalyzer{
    private static GrammarGender[] genderCases = GrammarGender.values();
    private static GrammarCase[] cases = GrammarCase.values();

    public static GrammarGender getGender(String report) {
        for (GrammarGender gender : genderCases) {
            if (report.contains(gender.toString())) {
                return gender;
            }
        }

        return GrammarGender.UNKNOWN;
    }

    public static GrammarCase getCase(String report) {
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
        if (n != -1) {
            partOfSpeech = report.substring(0, n);
        }

        return PartOfSpeech.getByName(partOfSpeech);
    }
}
