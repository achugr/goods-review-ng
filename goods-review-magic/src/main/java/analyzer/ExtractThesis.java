package analyzer;
/*
 *  Date: 18.02.12
 *   Time: 16:04
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import analyzer.algorithmTesting.Phrase;
import analyzer.util.ThesisPattern;
import analyzer.util.sentence.PartOfSpeech;
import analyzer.util.sentence.ReviewTokens;
import analyzer.util.sentence.Token;
import analyzer.wordAnalyzer.ReportAnalyzer;

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
            String str = stringTokenizer.nextToken();

            ReviewTokens reviewTokens = new ReviewTokens(str);
            ArrayList<ArrayList<Token>> tokensList = reviewTokens.getTokensList();

            for (ThesisPattern thesisPattern : thesisPatternList) {
                ThesisPattern pattern = thesisPattern;

                if (pattern.getPattern().get(0).equals(PartOfSpeech.NOUN)) {
                    nounAtFirstPositionExtraction(extractedThesisList, tokensList, pattern);
                } else {
                    if (pattern.getPattern().get(1).equals(PartOfSpeech.NOUN)) {
                        nounAtSecondPositionExtraction(extractedThesisList, tokensList, pattern);
                    } else {
                        System.out.println("incorrect pattern");
                    }
                }

            }
        }


        return extractedThesisList;
    }


    static void nounAtFirstPositionExtraction(ArrayList<Phrase> extractedThesisList, ArrayList<ArrayList<Token>> tokensList, ThesisPattern pattern) throws UnsupportedEncodingException {
        String token1 = null;
        PartOfSpeech noun = pattern.getPattern().get(0);
        PartOfSpeech part2 = pattern.getPattern().get(1);
        int n1 = 0;
        int n2 = 0;

        for (int i = 0; i < tokensList.size(); i++) {
            Token currToken = tokensList.get(i).get(0);

            if (currToken.getPartOfSpeech().equals(noun)) {
                token1 = currToken.getContent();
                 n1 = i;
            } else {
                if (token1 != null && currToken.getPartOfSpeech().equals(part2)) {
                     n2 = i;
                    
                   // boolean patternCondition = (Math.abs(n1-n2)==1)&&(dictContains(tokensList.get(n1+1).get(0).getContent()));
                    boolean patternCondition = false;
                    if(Math.abs(n1-n2)==1||patternCondition){

                        String token2 = currToken.getContent();

                        if(checkTokenListCorrespondence(tokensList.get(n1), tokensList.get(n2))) {

                            //   System.out.println(token1+" "+tokensList.get(n1+1).getContent()+" "+token2);
                            //   token2= tokensList.get(n1+1).getContent()+" "+token2;
                         // }
                            extractedThesisList.add(new Phrase(token1,token2));
                        }
                    }

                    token1 = null;
                }
            }
        }
    }

    static void nounAtSecondPositionExtraction(ArrayList<Phrase> extractedThesisList, ArrayList<ArrayList<Token>> tokensList, ThesisPattern pattern) throws UnsupportedEncodingException {
        String token1 = null;
        PartOfSpeech part2 = pattern.getPattern().get(0);
        PartOfSpeech noun = pattern.getPattern().get(1);
        int n1 = 0;
        int n2 = 0;
        for (int i = tokensList.size()-1; i >= 0 ; i--) {
            Token currToken = tokensList.get(i).get(0);

            if (currToken.getPartOfSpeech().equals(noun)) {
                token1 = currToken.getContent();
                n1 = i;
            } else {
                if (token1 != null && currToken.getPartOfSpeech().equals(part2)) {
                    n2 = i;
                    boolean patternCondition = false;
//                    if(n2!=0){
//                        patternCondition = (Math.abs(n1-n2)==1)&&(dictContains(tokensList.get(n2-1).getContent()));
//                    }

                    if(Math.abs(n1-n2)==1||patternCondition){

                        String token2 = currToken.getContent();

                        if(checkTokenListCorrespondence(tokensList.get(n1),tokensList.get(n2))) {
                           // if(patternCondition){
                              //  System.out.println(tokensList.get(n2-1).getContent()+" "+token2+" "+token1);
                              //  token2 = tokensList.get(n2-1).getContent()+" "+token2;
                           // }
                            extractedThesisList.add(new Phrase(token1,token2));
                        }
                    }
                    token1 = null;
                }
            }
        }
    }

    static boolean checkWordsCorrespondence(Token token1, Token token2) throws UnsupportedEncodingException {
        String p1 = token1.getGender();
        String p2 = token2.getGender();
        String num1 = token1.getNumber();
        String num2 = token2.getNumber();
        String case1 = token1.getCaseOf();
        String case2 = token2.getCaseOf();
        boolean con1 = check(p1, p2);       // Род
        boolean con2 = check(num1, num2);   // Число
        boolean con3 = check(case1, case2); // Падеж

      boolean sep = con1 && con2 && con3;
       // boolean sep = con1 && con2;
       // boolean sep = (con1 && con2) || (con1 && con3);

        return sep;
    }

    static boolean checkTokenListCorrespondence(ArrayList<Token> tokenList1, ArrayList<Token> tokenList2) throws UnsupportedEncodingException {
        for (int i = 0; i < tokenList1.size(); i++) {
            for (int j = 0; j < tokenList2.size(); j++) {
                 if(checkWordsCorrespondence(tokenList1.get(i),tokenList2.get(j))){
                     return true;
                 }
            }
        }

        return false;
    }

    static boolean check(String s1, String s2) {
        String unk = ReportAnalyzer.UNKNOUN;
        if (!s1.equals(unk) && !s2.equals(unk)) {
            return s1.equals(s2);
        }
        return false;
    }

    static String[] dict = {"более", "достаточно", "очень", "не", "слишком", "довольно"};

    static boolean dictContains(String s) {
        for (String str:dict){
            if(s.equals(str)){
                return true;
            }
        }
        return false;
    }




}
