package ru.goodsreview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:22
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.goodsreview.analyzer.util.dictionary.Dictionary;
import ru.goodsreview.analyzer.util.dictionary.MapDictionary;
import ru.goodsreview.analyzer.util.dictionary.SetDictionary;
import ru.goodsreview.analyzer.word.analyzer.MystemAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ReviewTokens {
    //    list of tokens
    private ArrayList<ArrayList<Token>> tokensList;

    //TODO удалить, переделать
    //TODO про кодировки даж говорить не буду
    private static Dictionary featureDictionary = new SetDictionary().getInstance("/ru/goodsreview/analyzer/util/dictionary/feat_dic.txt");
    private static MapDictionary opinionDictionary = new MapDictionary().getInstance("/ru/goodsreview/analyzer/util/dictionary/adjective_opinion_words.txt");

    private static MystemAnalyzer mystemAnalyzer = (MystemAnalyzer)new ClassPathXmlApplicationContext("beans.xml").getBean("myStem");

//    @Required
//    public void setMystemAnalyzer(MystemAnalyzer mystemAnalyzer) {
//        this.mystemAnalyzer = mystemAnalyzer;
//    }

    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review) throws IOException, InterruptedException {

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
                String normForm;

                String mystemReport = mystemAnalyzer.report(currToken);

                if (!mystemReport.equals(MystemAnalyzer.EMPTY_REPORT)) {

                    PartOfSpeech mystemPartOfSpeech = ReportAnalyzer.partOfSpeech(mystemReport);

                    String normToken = ReportAnalyzer.normalizer(mystemReport);
                    normForm = normToken;


                    switch (mystemPartOfSpeech) {
                        case ADJECTIVE:
                            if (opinionDictionary.contains(normToken)) {
                                partOfSpeach = PartOfSpeech.ADJECTIVE;
                            }
                            break;
                        case NOUN:
                            if (featureDictionary.contains(normToken)) {
                                partOfSpeach = PartOfSpeech.NOUN;
                            }
                            break;
                        default:
                            partOfSpeach = mystemPartOfSpeech;
                            break;
                    }


                    List<String> reportList;
                    if (partOfSpeach.equals(PartOfSpeech.NOUN) || partOfSpeach.equals(PartOfSpeech.ADJECTIVE)) {
                        reportList = ReportAnalyzer.buildReportList(mystemReport);
                    } else {
                        reportList = new ArrayList<String>();
                        reportList.add(mystemReport);
                    }

                    for (String rep : reportList) {
                        WordProperty property = ReportAnalyzer.wordProperty(rep);
                        String gender = property.getGender().toString();
                        String number = property.getNumber().toString();
                        String caseOf = property.getCase().toString();
                        Token newToken = new Token(currToken, normForm, partOfSpeach, gender, number, caseOf);
                        newTokensList.add(newToken);
                    }


                } else {
                    newTokensList.add(new Token(currToken, unk, PartOfSpeech.UNKNOWN, unk, unk, unk));
                }


                tokensList.add(newTokensList);
            }
        }
    }

    public static MystemAnalyzer getMystemAnalyzer(){
        return mystemAnalyzer;
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