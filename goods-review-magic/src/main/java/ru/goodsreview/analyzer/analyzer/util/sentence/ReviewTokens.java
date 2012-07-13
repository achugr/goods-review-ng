package ru.goodsreview.analyzer.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:22
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */


import ru.goodsreview.analyzer.analyzer.util.dictionary.Dictionary;
import ru.goodsreview.analyzer.analyzer.util.dictionary.MapDictionary;
import ru.goodsreview.analyzer.analyzer.wordAnalyzer.MystemAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReviewTokens {
    //    list of tokens
    private ArrayList<Token> tokensList;

    private static Dictionary featureDictionary = new Dictionary("feat_dic.txt", "windows-1251");
    private static MapDictionary opinionDictionary = new MapDictionary("adjective_opinion_words.txt", "utf-8");


    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review) throws IOException, InterruptedException {
        MystemAnalyzer mystemAnalyzer = MystemAnalyzer.getInstance();
        String unk = MystemAnalyzer.getUnkValue();

        tokensList = new ArrayList<Token>();

        StringTokenizer stringTokenizer = new StringTokenizer(review, " ");
        while (stringTokenizer.hasMoreElements()) {
            String currToken = stringTokenizer.nextToken();
            currToken = currToken.trim();
            currToken = currToken.toLowerCase();

            if (!currToken.equals("")) {
                PartOfSpeech partOfSpeach = PartOfSpeech.UNKNOWN;
                String normForm = unk;
                String gender = unk;
                String number = unk;
                String caseOf = unk;

                String mystemReport = mystemAnalyzer.report(currToken);

                if (!mystemReport.equals(MystemAnalyzer.getEmptyReportValue())) {
                    PartOfSpeech mystemPartOfSpeech = mystemAnalyzer.partOfSpeech(mystemReport);

                    String normToken = mystemAnalyzer.normalizer(mystemReport);
                    normForm = normToken;

                    if (mystemPartOfSpeech.equals(PartOfSpeech.ADJECTIVE)) {
                        if (opinionDictionary.contains(normToken)) {
                            partOfSpeach = PartOfSpeech.ADJECTIVE;
                        }
                    } else {
                        if (mystemPartOfSpeech.equals(PartOfSpeech.NOUN)) {
                            if (featureDictionary.contains(normToken)) {
                                partOfSpeach = PartOfSpeech.NOUN;
                            }
                        } else {
                            partOfSpeach = mystemPartOfSpeech;
                        }
                    }


                    String[] a = mystemAnalyzer.wordCharacteristic(mystemReport);
                    gender = a[0];
                    number = a[1];
                    caseOf = a[2];
                }

                Token token = new Token(currToken, normForm, partOfSpeach, gender, number, caseOf);

                tokensList.add(token);
            }
    }
    }



    public ArrayList<Token> getTokensList() {
        return tokensList;
    }

}