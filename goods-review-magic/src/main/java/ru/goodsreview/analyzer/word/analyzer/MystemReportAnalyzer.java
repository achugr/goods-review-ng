package ru.goodsreview.analyzer.word.analyzer;

import ru.goodsreview.analyzer.util.sentence.mystem.GrammarCase;
import ru.goodsreview.analyzer.util.sentence.mystem.GrammarGender;
import ru.goodsreview.analyzer.util.sentence.GrammarNumber;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Date: 13.07.12
 * Time: 23:45
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class MystemReportAnalyzer extends ReportAnalyzer{
    private static PartOfSpeech[] parts = PartOfSpeech.values();
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
        boolean t1 = false;
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < i; j++) {
                if (report.contains(cases[i].toString()) && report.contains(cases[j].toString())) {
                    if (i != 3 && j != 0) {
                        t1 = true;
                        break;
                    }
                }
            }
        }

        if (!t1) {
            for (GrammarCase aCase : cases) {
                if (report.contains(aCase.toString())) {
                    return aCase;
                }
            }
        } else {
            // System.out.println(report);
        }

        return GrammarCase.UNKNOWN;
    }


    public static PartOfSpeech getPartOfSpeech(String report) throws UnsupportedEncodingException {
        for (PartOfSpeech part:parts){
            if(report.contains(part.toString())){
                return part;
            }
        }

          return PartOfSpeech.UNKNOWN;
    }

    public static String getNormForm(String report) throws UnsupportedEncodingException {
        String norm = UNKNOUN;
        int n = report.indexOf("?=");
        if (n != -1) {
            norm = report.substring(0, n);
        } else {
            n = report.indexOf("=");
            if (n != -1) {
                norm = report.substring(0, n);
            }

        }

        if (norm.equals("")) {
            norm = UNKNOUN;
        }

        // System.out.println(report + " --> " + norm);

        return norm;
    }


    public static List<String> buildReportList(String report) throws UnsupportedEncodingException {
        ArrayList<String> reportList = new ArrayList<String>();
        int k = report.indexOf("{");
        if(k!=-1){
            String s = report.substring(k+1,report.length()-1);

            String[] reports = s.split("\\|");
            for (String str:reports){
                 reportList.add(str);
            }

        }

        return reportList;
    }


}
