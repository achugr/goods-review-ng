package ru.goodsreview.analyzer.word.analyzer;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.junit.Test;
import ru.goodsreview.analyzer.util.sentence.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Date: 27.08.12
 * Author: Ilya Makeev
 */
public class LuceneAnalyzer implements WordAnalyzer{
    private static LuceneMorphology luceneMorph;

    static {
        try {
            luceneMorph = new RussianLuceneMorphology();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public  List<Token> getTokenList(String word) throws UnsupportedEncodingException {
        ArrayList<Token> tokensList = new ArrayList<Token>();

        List<String> reportList = luceneMorph.getMorphInfo(word);

            for (String rep : reportList) {
               // System.out.println(rep);
                int k = rep.indexOf(" ");
                if (k != -1 && k + 1 < rep.length()) {
                    String tRep = rep.substring(k + 1);
                        String normForm = LuceneReportAnalyzer.normalizer(rep);

                        PartOfSpeech partOfSpeech = LuceneReportAnalyzer.partOfSpeech(tRep);

                        WordProperties wordProp = LuceneReportAnalyzer.wordProperties(tRep);
                        GrammarGender gender = wordProp.getGender();
                        GrammarNumber number = wordProp.getNumber();
                        GrammarCase caseOf = wordProp.getCase();

                        Token newToken = new Token(word, normForm, partOfSpeech, gender, number, caseOf);
                        tokensList.add(newToken);

                }
            }


        if(tokensList.size()==0){
            tokensList.add(new Token(word, MystemReportAnalyzer.UNKNOUN,PartOfSpeech.UNKNOWN, GrammarGender.UNKNOWN, GrammarNumber.UNKNOWN,GrammarCase.UNKNOWN));
        }

        return tokensList;
    }

//    public String report(String word){
//        List<String> reportList = luceneMorph.getMorphInfo(word);
//        if(reportList.size()!=0){
//            return reportList.get(0);
//        }
//        return "";
//    }

    public void close(){
    }

    @Test
    public  void test(){
        String str = "столовая";
        List<String> wordBaseForms = luceneMorph.getMorphInfo(str);

        for (String s:wordBaseForms){
            System.out.println(s);
            System.out.println(LuceneReportAnalyzer.normalizer(s));
            WordProperties wp = LuceneReportAnalyzer.wordProperties(s);
            System.out.println(LuceneReportAnalyzer.partOfSpeech(s));
            System.out.println(wp.getGender());
            System.out.println(wp.getNumber());
            System.out.println(wp.getCase());
        }
    }



}
