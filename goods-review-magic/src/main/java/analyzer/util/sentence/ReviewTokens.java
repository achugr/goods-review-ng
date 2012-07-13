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

    private static Dictionary featureDictionary = new Dictionary("feat_dic.txt", "windows-1251");
    private static MapDictionary opinionDictionary = new MapDictionary("adjective_opinion_words.txt", "utf-8");


    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review) throws IOException, InterruptedException {
        MystemAnalyzer mystemAnalyzer = MystemAnalyzer.getInstance();

        tokensList = new ArrayList<Token>();

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


            PartOfSpeech partOfSpeach = PartOfSpeech.UNKNOWN;
            String normForm = "unk";
            String gender = "unk";
            String number = "unk";
            String caseOf = "unk";

            if (MystemAnalyzer.isRussianWord(currToken)) {
                String  mystemReport = mystemAnalyzer.report(currToken);

                PartOfSpeech mystemPartOfSpeech = mystemAnalyzer.partOfSpeech(mystemReport);

                String normToken = mystemAnalyzer.normalizer(mystemReport);
                normForm = normToken;

                if (mystemPartOfSpeech.equals(PartOfSpeech.ADJECTIVE)) {
                    if (opinionDictionary.contains(normToken)) {
                        partOfSpeach = PartOfSpeech.ADJECTIVE;
                    }
                } else {
                    if (mystemPartOfSpeech.equals(PartOfSpeech.NOUN)) {
                        if (featureDictionary.contains(normToken)) {
                            partOfSpeach = PartOfSpeech.NOUN;
                        }
                    } else {
                        partOfSpeach = mystemPartOfSpeech;
                    }
                }


            String[] a = mystemAnalyzer.wordCharacteristic(mystemReport);
            gender = a[0];
            number = a[1];
            caseOf = a[2];
        }

            Token token = new Token(currToken, normForm, partOfSpeach, gender, number, caseOf);

            tokensList.add(token);
        }

    }



    public ArrayList<Token> getTokensList() {
        return tokensList;
    }

//    public MapDictionary getDic() {
//        return opinionDictionary;
//    }

//    public Dictionary getFeatureDic() {
//        return featureDictionary;
//    }

}