package analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:22
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */


import analyzer.util.dictionary.Dictionary;
import analyzer.util.dictionary.MapDictionary;
import analyzer.wordAnalyzer.MystemAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReviewTokens {
    //    list of tokens
    private ArrayList<Token> tokensList;
    //    position of current token
    private int currentPosition = -1;
    //    "pointer" for traverse next/previous by currentPosition
    private int traversePosition = currentPosition;



    private static Dictionary featureDictionary = new Dictionary("feat_dic.txt", "windows-1251");



    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review, MystemAnalyzer mystemAnalyzer, MapDictionary opinionDictionary) throws IOException, InterruptedException {
        Token token;
        tokensList = new ArrayList<Token>();
        // StringTokenizer stringTokenizer = new StringTokenizer(review, " .,-—:;!()+\'\"\\«»");
        StringTokenizer stringTokenizer = new StringTokenizer(review, " ");
        while (stringTokenizer.hasMoreElements()) {
            String currToken = stringTokenizer.nextToken();
            currToken = currToken.trim();
            currToken = currToken.toLowerCase();

//            TODO it's strange, but here we can get empty string
            if (currToken.equals("")) {
                System.out.println("fail");
                continue;
            }

            token = new Token(currToken);

            if (isRussianWord(currToken)) {
                PartOfSpeech partOfSpeech = mystemAnalyzer.partOfSpeech(currToken);
                if (partOfSpeech.equals(PartOfSpeech.ADJECTIVE)) {
//                    if(normDictionary.contains(currToken)){
                    //  String normToken = (String)normDictionary.getDictionary().get(currToken);
                    //   System.out.println(currToken+" "+mystemAnalyzer.normalizer(currToken));
                    String normToken = mystemAnalyzer.normalizer(currToken);

                    if (opinionDictionary.contains(normToken)) {
                        token.setMystemPartOfSpeech(PartOfSpeech.ADJECTIVE);
                    } else {
                        token.setMystemPartOfSpeech(PartOfSpeech.UNKNOWN);
                    }
//                    }else{
//                        token.setMystemPartOfSpeech(PartOfSpeech.UNKNOWN);
//                    }
                } else {
                    if (partOfSpeech.equals(PartOfSpeech.NOUN)) {
                        String normToken = mystemAnalyzer.normalizer(currToken);
                        if(featureDictionary.contains(normToken)) {
                        // System.out.println(normToken);
                        token.setMystemPartOfSpeech(PartOfSpeech.NOUN);
                       }else{
                            token.setMystemPartOfSpeech(PartOfSpeech.UNKNOWN);
                        }
                    } else {
                        token.setMystemPartOfSpeech(partOfSpeech);
                    }
                }
            } else {
                token.setMystemPartOfSpeech(PartOfSpeech.UNKNOWN);
            }

            tokensList.add(token);
        }

    }

    private static boolean isRussianLetter(char letter) {
        if ((letter >= 0x0410) && (letter <= 0x044F)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRussianWord(String word) {
        char[] wordChars = word.toCharArray();
        for (int i = 0, j = wordChars.length; i < j; i++) {
            if (!isRussianLetter(wordChars[i])) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Token> getTokensList() {
        return tokensList;
    }

//    public MapDictionary getDic() {
//        return opinionDictionary;
//    }

    public Dictionary getFeatureDic() {
        return featureDictionary;
    }

}