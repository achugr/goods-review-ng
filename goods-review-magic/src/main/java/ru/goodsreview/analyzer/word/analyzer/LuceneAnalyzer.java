package ru.goodsreview.analyzer.word.analyzer;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.junit.Test;
import ru.goodsreview.analyzer.util.sentence.GrammarNumber;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsreview.analyzer.util.sentence.Token;
import ru.goodsreview.analyzer.util.sentence.lucene.GrammarCase;
import ru.goodsreview.analyzer.util.sentence.lucene.GrammarGender;

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
                        String normForm = LuceneReportAnalyzer.getNormForm(rep);
                   // System.out.println(normForm);
                        PartOfSpeech partOfSpeech = LuceneReportAnalyzer.getPartOfSpeech(tRep);
                    //System.out.println(getPartOfSpeech.toString());

                        GrammarGender gender = LuceneReportAnalyzer.getGender(tRep);
                        GrammarNumber number = LuceneReportAnalyzer.getNum(tRep);
                        GrammarCase caseOf = LuceneReportAnalyzer.getCase(tRep);

                        Token newToken = new Token(word, normForm, partOfSpeech, gender.toString(), number.toString(), caseOf.toString());
                        tokensList.add(newToken);

                }
            }


        if(tokensList.size()==0){
            tokensList.add(new Token(word, MystemReportAnalyzer.UNKNOUN,PartOfSpeech.UNKNOWN, MystemReportAnalyzer.UNKNOUN, MystemReportAnalyzer.UNKNOUN,MystemReportAnalyzer.UNKNOUN));
        }

        return tokensList;
    }

    public String report(String word){
        List<String> reportList = luceneMorph.getMorphInfo(word);
        if(reportList.size()!=0){
            return reportList.get(0);
        }
        return "";
    }

    public void close(){
    }

    @Test
    public  void test(){
        String str = "кот";
        List<String> wordBaseForms = luceneMorph.getMorphInfo(str);

        for (String s:wordBaseForms){
            System.out.println(s);
            System.out.println(LuceneReportAnalyzer.getNormForm(s));

            System.out.println(LuceneReportAnalyzer.getPartOfSpeech(s));

        }
    }



}
