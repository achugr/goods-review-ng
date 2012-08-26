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

        tokensList = new ArrayList<ArrayList<Token>>();

        StringTokenizer stringTokenizer = new StringTokenizer(review, " ");
        while (stringTokenizer.hasMoreElements()) {
            String currToken = stringTokenizer.nextToken();
            currToken = currToken.trim();


            if (!currToken.equals("")) {
                currToken = currToken.toLowerCase();

                ArrayList<Token> list = mystemAnalyzer.getTokenList(currToken);

                dictionaryCheck(list);

                ArrayList<Token> newTokensList = new ArrayList<Token>();
                for (Token token :list){
                       if(!token.getPartOfSpeech().equals(PartOfSpeech.UNKNOWN)){
                           newTokensList.add(token);
                       }
                }

                if(newTokensList.size()>0){
                    tokensList.add(newTokensList);
                }

            }
        }
    }

    private static void dictionaryCheck(ArrayList<Token> list){
        for (Token token : list) {
            if (token.getPartOfSpeech().equals(PartOfSpeech.ADJECTIVE)) {
                if (!opinionDictionary.contains(token.getNormForm())) {
                    token.setPartOfSpeech(PartOfSpeech.UNKNOWN);
                }
            }

            if (token.getPartOfSpeech().equals(PartOfSpeech.NOUN)) {
                if (!featureDictionary.contains(token.getNormForm())) {
                    token.setPartOfSpeech(PartOfSpeech.UNKNOWN);
                }
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