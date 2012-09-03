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
import ru.goodsreview.analyzer.word.analyzer.WordAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ReviewTokens {
    //    list of tokens
    private List<List<Token>> listsOfToken;

    //TODO удалить, переделать
    private static Dictionary featureDictionary = new SetDictionary().getInstance("/ru/goodsreview/analyzer/util/dictionary/feat_dic.txt");
    private static MapDictionary opinionDictionary = new MapDictionary().getInstance("/ru/goodsreview/analyzer/util/dictionary/adjective_opinion_words.txt");

    private static WordAnalyzer wordAnalyzer = (WordAnalyzer)new ClassPathXmlApplicationContext("beans.xml").getBean("wordAnalyzer");


//    @Required
//    public void setMystemAnalyzer(MystemAnalyzer wordAnalyzer) {
//        this.wordAnalyzer = wordAnalyzer;
//    }

    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review) throws IOException, InterruptedException {

        listsOfToken = new ArrayList<List<Token>>();

        StringTokenizer stringTokenizer = new StringTokenizer(review, " ");
        while (stringTokenizer.hasMoreElements()) {
            String currToken = stringTokenizer.nextToken();

            if (ReportAnalyzer.isRussianWord(currToken)) {

                currToken = currToken.trim();

                if (!currToken.equals("")) {
                    currToken = currToken.toLowerCase();

                    List<Token> list = wordAnalyzer.getTokenList(currToken);

                    dictionaryCheck(list);

                    listsOfToken.add(list);

                }
            }
        }
    }

    private static void dictionaryCheck(List<Token> list){
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

    public static WordAnalyzer getWordAnalyzer(){
        return wordAnalyzer;
    }

    public static Dictionary getFeatureDictionary(){
        return featureDictionary;
    }

    public static MapDictionary getMapDictionary(){
        return opinionDictionary;
    }

    public List<List<Token>> getListsOfToken() {
        return listsOfToken;
    }

}