package ru.goodsreview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:22
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */


import ru.goodsreview.analyzer.util.dictionary.*;
import ru.goodsreview.analyzer.util.sentence.mystem.PartOfSpeech;
import ru.goodsreview.analyzer.word.analyzer.MystemAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.WordAnalyzer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ReviewTokens {
    //    list of tokens
    private List<List<Token>> listsOfToken;

    //TODO удалить, переделать
    private static Dictionary featureDictionary = new SetDictionary().getInstance("/ru/goodsreview/analyzer/util/dictionary/feat_dic.txt");
    private static MapDictionary opinionDictionary = new MapDictionary().getInstance("/ru/goodsreview/analyzer/util/dictionary/adjective_opinion_words.txt");
    private static ObserveDict observeDict = new ObserveDict();

    private static PyMorphyDict pymorphyDict = PyMorphyDict.getInstance("/ru/goodsreview/analyzer/util/dictionary/pyDict.txt");

//    private static WordAnalyzer wordAnalyzer = (WordAnalyzer)new ClassPathXmlApplicationContext("beans.xml").getBean("wordAnalyzer");
    private static WordAnalyzer wordAnalyzer = new MystemAnalyzer();
//    @Required
//    public void setMystemAnalyzer(WordAnalyzer wordAnalyzer) {
//        this.wordAnalyzer = wordAnalyzer;
//    }

    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review)  {

        listsOfToken = new ArrayList<List<Token>>();

        StringTokenizer stringTokenizer = new StringTokenizer(review, " ");
        while (stringTokenizer.hasMoreElements()) {
            String currToken = stringTokenizer.nextToken();

            if (ReportAnalyzer.isRussianWord(currToken)) {

                currToken = currToken.trim();

                if (!currToken.isEmpty()) {
                    currToken = currToken.toLowerCase();

                    List<Token> list = null;

                        list = wordAnalyzer.getTokenList(currToken);


                    dictionaryCheck(list);

                    listsOfToken.add(list);

                }
            }
        }
    }

    private static void dictionaryCheck(List<Token> list) {
        for (Token token : list) {
            if (token.getPartOfSpeech().equals(PartOfSpeech.ADJECTIVE)) {
                Double opinionValue = opinionDictionary.getValue(token.getNormForm());
                if (opinionValue != null) {
                    token.setSentiment(opinionValue.doubleValue());
                } else {
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

    public static PyMorphyDict getPymorphyDict(){
        return pymorphyDict;
    }

    public static Dictionary getFeatureDictionary(){
        return featureDictionary;
    }

    public static MapDictionary getMapDictionary(){
        return opinionDictionary;
    }

    public static ObserveDict getObserveDict(){
        return observeDict;
    }

    public List<List<Token>> getListsOfToken() {
        return listsOfToken;
    }

}