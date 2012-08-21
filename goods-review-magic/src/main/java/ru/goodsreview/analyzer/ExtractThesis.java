package ru.goodsreview.analyzer;
/*
 *  Date: 18.02.12
 *   Time: 16:04
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import ru.goodsreview.analyzer.util.Phrase;
import ru.goodsreview.analyzer.util.ThesisPattern;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsreview.analyzer.util.sentence.ReviewTokens;
import ru.goodsreview.analyzer.util.sentence.Token;
import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ExtractThesis{

    public static ArrayList<Phrase> doExtraction(String content) throws IOException, InterruptedException {
        ArrayList<Phrase> extractedThesisList = new ArrayList<Phrase>();

        List<ThesisPattern> thesisPatternList = new ArrayList<ThesisPattern>();
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.NOUN, PartOfSpeech.ADJECTIVE));
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.ADJECTIVE, PartOfSpeech.NOUN));

        StringTokenizer stringTokenizer = new StringTokenizer(content, ".,-—:;!()+\'\"\\«»");

        while (stringTokenizer.hasMoreElements()) {
            String sentence = stringTokenizer.nextToken();

            ReviewTokens reviewTokens = new ReviewTokens(sentence);
            ArrayList<ArrayList<Token>> tokensList = reviewTokens.getTokensList();

//            for (int i = 0; i < tokensList.size(); i++) {
//                System.out.print(tokensList.get(i).get(0).getContent()+"("+tokensList.get(i).get(0).getPartOfSpeech().name()+")"+" ");
//            } System.out.println();

            for (ThesisPattern thesisPattern : thesisPatternList) {
                extractPattern(extractedThesisList, tokensList, thesisPattern);
            }
        }

        return extractedThesisList;
    }


    static void extractPattern(ArrayList<Phrase> extractedThesisList, ArrayList<ArrayList<Token>> tokensList, ThesisPattern pattern) throws UnsupportedEncodingException {
        PartOfSpeech part1 = pattern.getPattern().get(0);
        PartOfSpeech part2 = pattern.getPattern().get(1);

        for (int i = 0; i < tokensList.size(); i++) {
            Token rightToken = tokensList.get(i).get(0);

            if (rightToken.getPartOfSpeech().equals(part2)) {
                int k = -1;
                for (int j = i - 1; j >= 0; j--) {
                    Token leftToken = tokensList.get(j).get(0);
                    if (leftToken.getPartOfSpeech().equals(part1)) {
                        k = j;
                        break;
                    }
                }
                if (k != -1) {
                    if (Math.abs(i - k) == 1) {
                        Token token1 = tokensList.get(k).get(0);
                        Token token2 = tokensList.get(i).get(0);
                        // System.out.println("#"+token1.getContent()+" "+token2.getContent());
                        if (checkTokenListCorrespondence(tokensList.get(k), tokensList.get(i))) {
                            extractedThesisList.add(new Phrase(token1.getContent(), token2.getContent()));
                        }
                    }
                }
            }
        }
    }

    static boolean checkWordsCorrespondence(Token token1, Token token2) throws UnsupportedEncodingException {
        String p1 = token1.getGender();
        String p2 = token2.getGender();
        String num1 = token1.getNumber();
        String num2 = token2.getNumber();
        String case1 = token1.getCase();
        String case2 = token2.getCase();
        boolean con1 = check(p1, p2);       // Род
        boolean con2 = check(num1, num2);   // Число
        boolean con3 = check(case1, case2); // Падеж

      boolean sep = con1 && con2 && con3;
       // boolean sep = con1 && con2;
       // boolean sep = (con1 && con2) || (con1 && con3);

        return sep;
    }

    static boolean checkTokenListCorrespondence(ArrayList<Token> tokenList1, ArrayList<Token> tokenList2) throws UnsupportedEncodingException {
        for (Token aTokenList1 : tokenList1) {
            for (Token aTokenList2 : tokenList2) {
                if (checkWordsCorrespondence(aTokenList1, aTokenList2)) {
                    return true;
                }
            }
        }

        return false;
    }

    static boolean check(String s1, String s2) {
        String unk = ReportAnalyzer.UNKNOUN;
        return !s1.equals(unk) && !s2.equals(unk) && s1.equals(s2);
    }

}
