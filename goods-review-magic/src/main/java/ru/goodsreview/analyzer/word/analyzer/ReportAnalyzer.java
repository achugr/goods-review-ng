package ru.goodsreview.analyzer.word.analyzer;

import ru.goodsreview.analyzer.util.sentence.*;

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
public class ReportAnalyzer {
    public static final String UNKNOUN = "unk";

    public static WordProperties wordProperties(String report) {
        WordProperties property = new WordProperties(GrammarGender.UNKNOWN,GrammarNumber.UNKNOWN,GrammarCase.UNKNOWN);

            GrammarGender[] genderCases = GrammarGender.values();
            for (GrammarGender gender:genderCases){
                    if(report.contains(gender.toString())){
                        property.setGender(gender);
                        break;
                    }
                }

            GrammarNumber[] numCases = GrammarNumber.values();
                for (GrammarNumber number:numCases){
                    if(report.contains(number.toString())){
                        property.setNumber(number);
                        break;
                    }
                }

            GrammarCase[] cases = GrammarCase.values();

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
                        property.setCase(aCase);
                        break;
                    }
                }
            } else {
                // System.out.println(report);
            }



        return property;
    }


    static boolean hasDualConclusion(String report, Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (report.contains(arr[i].toString()) && report.contains(arr[j].toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasAnyDualConclusion(String report) {
        return hasDualConclusion(report, GrammarGender.values()) &&
                hasDualConclusion(report, GrammarNumber.values()) &&
                hasDualConclusion(report, GrammarCase.values());
    }

    static boolean isFull(String report) {
        return containsProperty(report, GrammarGender.values()) &&
                containsProperty(report, GrammarNumber.values()) &&
                containsProperty(report, GrammarCase.values());
    }

    static boolean containsProperty(String report, Object[] arr) {
        for (Object elem : arr) {
            if (report.contains(elem.toString())) {
                return true;
            }
        }
        return false;
    }

    static boolean isCorrect(String report) {
        return isFull(report) && !hasAnyDualConclusion(report);
    }

    public static PartOfSpeech partOfSpeech(String report) throws UnsupportedEncodingException {
            int pos1 = report.indexOf('=') + 1;
            int pos2 = pos1;
            while (Character.isUpperCase(report.charAt(pos2))) {
                pos2++;
            }
            String partOfSpeech = report.substring(pos1, pos2);

          return PartOfSpeech.getByName(partOfSpeech);
    }

    public static String normalizer(String report) throws UnsupportedEncodingException {
        String norm = UNKNOUN;
            int n = report.indexOf("=");
            if (n != -1) {
                norm = report.substring(report.indexOf("{") + 1, n);
            } else {
                n = report.indexOf("?");
                if (n != -1) {
                    norm = report.substring(report.indexOf("{") + 1, n);
                }
            }
            //   System.out.println(word + " --> " + norm);

        return norm;
    }


    public static List<String> buildReportList(String report) throws UnsupportedEncodingException {
        ArrayList<String> reportList = new ArrayList<String>();
        if (!report.equals(MystemAnalyzer.EMPTY_REPORT)) {
            String norm = normalizer(report);
            ArrayList<String> normList = new ArrayList<String>();
            buildNormList(normList, report, norm);

            for (String s : normList) {
                int n1 = s.indexOf("(");
                int n2 = s.indexOf(")");
                if (n1 != -1 && n1 < n2) {
                    String s1 = s.substring(n1 + 1, n2);

                    StringTokenizer stringTokenizer = new StringTokenizer(s1, "|");
                    while (stringTokenizer.hasMoreElements()) {
                        String str = stringTokenizer.nextToken();
                        reportList.add(s.substring(0, n1) + "," + str);
                    }
                } else {
                    reportList.add(s);
                }
            }


        }
//        for (String s:reportList){
//            System.out.println(s);
//        }
        return reportList;
    }


    public static void buildNormList(ArrayList<String> list, String report, String norm) {
        int n1 = report.indexOf("|" + norm);
        if (n1 == -1) {
            list.add(report);
        } else {
            list.add(report.substring(0, n1));
            buildNormList(list, report.substring(n1 + norm.length() + 1), norm);
        }
    }



}
