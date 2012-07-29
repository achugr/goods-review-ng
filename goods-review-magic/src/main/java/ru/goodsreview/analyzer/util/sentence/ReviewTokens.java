package ru.goodsreview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:22
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */


import ru.goodsreview.analyzer.util.dictionary.Dictionary;
import ru.goodsreview.analyzer.util.dictionary.MapDictionary;
import ru.goodsreview.analyzer.util.dictionary.SetDictionary;
import ru.goodsreview.analyzer.word.analyzer.MystemAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReviewTokens {
    //    list of tokens
    private ArrayList<ArrayList<Token>> tokensList;

    private static Dictionary featureDictionary = new SetDictionary("/ru/goodsreview/analyzer/util/dictionary/feat_dic.txt", "windows-1251");
    private static MapDictionary opinionDictionary = new MapDictionary("/ru/goodsreview/analyzer/util/dictionary/adjective_opinion_words.txt", "utf-8");


    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review) throws IOException, InterruptedException {
        MystemAnalyzer mystemAnalyzer = MystemAnalyzer.getInstance();
        String unk = ReportAnalyzer.UNKNOUN;

        tokensList = new ArrayList<ArrayList<Token>>();

        StringTokenizer stringTokenizer = new StringTokenizer(review, " ");
        while (stringTokenizer.hasMoreElements()) {
            String currToken = stringTokenizer.nextToken();
            currToken = currToken.trim();
            currToken = currToken.toLowerCase();

            if (!currToken.equals("")) {
                ArrayList<Token> newTokensList = new ArrayList<Token>();
                PartOfSpeech partOfSpeach = PartOfSpeech.UNKNOWN;
                String normForm = unk;

                String mystemReport = mystemAnalyzer.report(currToken);

                if (!mystemReport.equals(MystemAnalyzer.getEmptyReportValue())) {

                    PartOfSpeech mystemPartOfSpeech = ReportAnalyzer.partOfSpeech(mystemReport);

                    String normToken = ReportAnalyzer.normalizer(mystemReport);
                    normForm = normToken;

                    if (mystemPartOfSpeech.equals(PartOfSpeech.ADJECTIVE)) {
                        if (opinionDictionary.contains(normToken)) {
                           // ReportAnalyzer.wordCharacteristic1(mystemReport);
                           // ReportAnalyzer.buildReportList(mystemReport);
                            partOfSpeach = PartOfSpeech.ADJECTIVE;
                        }
                    } else {
                        if (mystemPartOfSpeech.equals(PartOfSpeech.NOUN)) {
                            if (featureDictionary.contains(normToken)) {
                              //  ReportAnalyzer.wordCharacteristic1(mystemReport);
                                partOfSpeach = PartOfSpeech.NOUN;
                            }
                        } else {
                            partOfSpeach = mystemPartOfSpeech;
                        }
                    }

                    ArrayList<String> reportList;
                    if (partOfSpeach.equals(PartOfSpeech.NOUN) || partOfSpeach.equals(PartOfSpeech.ADJECTIVE)) {
                        reportList = ReportAnalyzer.buildReportList(mystemReport);
                    } else {
                        reportList = new ArrayList<String>();
                        reportList.add(mystemReport);
                    }
                    
                    for (String rep:reportList){
                        String[] a = ReportAnalyzer.wordCharacteristic(rep);
                        String gender = a[0];
                        String number = a[1];
                        String caseOf = a[2];
                        Token newToken = new Token(currToken, normForm, partOfSpeach, gender, number, caseOf);
                        newTokensList.add(newToken);
                    }


                }else{
                    newTokensList.add(new Token(currToken, unk, PartOfSpeech.UNKNOWN, unk, unk, unk));
                }


                tokensList.add(newTokensList);
            }
    }
    }

    public static Dictionary getFeatureDictionary(){
        return featureDictionary;
    }

    public static MapDictionary getMapDictionary(){
        return opinionDictionary;
    }

    public ArrayList<ArrayList<Token>> getTokensList() {
        return tokensList;
    }

}