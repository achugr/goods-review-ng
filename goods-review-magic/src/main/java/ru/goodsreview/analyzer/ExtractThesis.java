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
import ru.goodsreview.analyzer.util.sentence.*;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsreview.analyzer.util.sentence.mystem.GrammarGender;
import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ExtractThesis{

    public static List<Phrase> doExtraction(String content){
        ArrayList<Phrase> extractedThesisList = new ArrayList<Phrase>();

        List<ThesisPattern> thesisPatternList = new ArrayList<ThesisPattern>();
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.NOUN, PartOfSpeech.ADJECTIVE));
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.ADJECTIVE, PartOfSpeech.NOUN));

        int distance = 2;

        StringTokenizer stringTokenizer = new StringTokenizer(content, ".,-—:;!()+\'\"\\«»");  //!without space:" "

        while (stringTokenizer.hasMoreElements()) {
            String sentence = stringTokenizer.nextToken();

            ReviewTokens reviewTokens = new ReviewTokens(sentence);
            List<List<Token>> listsOfToken = reviewTokens.getListsOfToken();

//            for (int i = 0; i < listsOfToken.size(); i++) {
//                System.out.print(listsOfToken.get(i).get(0).getContent()+"("+listsOfToken.get(i).get(0).getPartOfSpeech().name()+")"+" ");
//            } System.out.println();

            for (ThesisPattern thesisPattern : thesisPatternList) {
                int pos = -1;
                if(thesisPattern.get(0).equals(PartOfSpeech.NOUN)){
                    pos = 1;
                }
                if(thesisPattern.get(1).equals(PartOfSpeech.NOUN)){
                    pos = 2;
                }
                if(pos == -1){
                    System.out.println("incorrect pattern");
                } else{
                    extractPattern(extractedThesisList, listsOfToken, thesisPattern, pos, distance);
                }

            }
        }

        return extractedThesisList;
    }


    static void extractPattern(ArrayList<Phrase> extractedThesisList, List<List<Token>> listsOfToken, ThesisPattern<PartOfSpeech> pattern, int pos, int distance) {
        PartOfSpeech part1 = pattern.get(0);
        PartOfSpeech part2 = pattern.get(1);

        for (int i = 0; i < listsOfToken.size(); i++) {
            List<Token> rightTokenList = listsOfToken.get(i);

            for (Token rightToken : rightTokenList) {
                if (rightToken.getPartOfSpeech().equals(part2)) {

                    for (int j = i - 1; j >= 0; j--) {

                        if (Math.abs(i - j) <= distance) {

                            List<Token> leftTokenList = listsOfToken.get(j);
                            for (Token leftToken : leftTokenList) {
                                if (leftToken.getPartOfSpeech().equals(part1)) {

                                    if (checkTokenListCorrespondence(leftTokenList, rightTokenList)) {

                                        Phrase newPhrase;
                                        String feature;
                                        String opinion;
                                        String normFeature;
                                        String normOpinion;
                                        double sentiment;
                                        String proem = "";
                                        if (pos == 1) {
                                            if (Math.abs(i - j) > 1) {
                                                String str = listsOfToken.get(i - 1).get(0).getContent();
                                                if (ReviewTokens.getObserveDict().contains(str)) {
                                                    proem = str;
                                                }
                                            }

                                            feature = leftToken.getContent();
                                            opinion = rightToken.getContent();
                                            normFeature = leftToken.getNormForm();
                                            normOpinion = rightToken.getNormForm();
                                            sentiment = rightToken.getSentiment();
                                        } else {
                                            if (j > 0) {
                                                String str = listsOfToken.get(j - 1).get(0).getContent();
                                                if (ReviewTokens.getObserveDict().contains(str)) {
                                                    proem = str;
                                                }
                                            }

                                            feature = rightToken.getContent();
                                            opinion = leftToken.getContent();
                                            normFeature = rightToken.getNormForm();
                                            normOpinion = leftToken.getNormForm();
                                            sentiment = leftToken.getSentiment();
                                        }

                                        if (!(feature.equals(normFeature) && opinion.equals(normOpinion))) {
                                            GrammarGender requiredGender = ReviewTokens.getWordAnalyzer().getGenger(normFeature);
                                            normOpinion = transform(normOpinion, requiredGender);
                                            //System.out.println(feature+" "+opinion+" # "+normFeature+" "+normOpinion);
                                        }

                                        if (proem.length() != 0) {
                                            opinion = proem + " " + opinion;
                                            normOpinion = proem + " " + normOpinion;
                                           // System.out.println(feature+" "+opinion+" # "+normFeature+" "+normOpinion);
                                        }

                                        newPhrase = new Phrase(feature, opinion, normFeature, normOpinion, sentiment);

                                        extractedThesisList.add(newPhrase);
                                        break;
                                    }

                                }
                            }
                        }
                    }
                    break;
                }
            }

        }
    }




    static boolean checkWordsCorrespondence(Token token1, Token token2) {
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
      //  boolean sep = con1 && con2;
       // boolean sep = (con1 && con2) || (con1 && con3);
      // boolean sep = true;
        return sep;
    }

    static boolean checkTokenListCorrespondence(List<Token> tokenList1, List<Token> tokenList2)  {
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
        String unk = MystemReportAnalyzer.UNKNOUN;
        return !s1.equals(unk) && !s2.equals(unk) && s1.equals(s2);
    }


    public static String transform(String word, GrammarGender gender) {
        String newWord = word;

        int indexOfGender;
        if (gender.equals(GrammarGender.MASCULINE)) {
            indexOfGender = 0;
        } else if (gender.equals(GrammarGender.FEMININE)) {
            indexOfGender = 1;
        } else if (gender.equals(GrammarGender.NEUTER)) {
            indexOfGender = 2;
        } else {
            indexOfGender = -1;
        }

        if (indexOfGender != -1) {
            String[] forms = ReviewTokens.getPymorphyDict().getForms(word);
            if (forms.length == 3) {
                newWord = forms[indexOfGender];
            }
        }

        return newWord;
    }


}
