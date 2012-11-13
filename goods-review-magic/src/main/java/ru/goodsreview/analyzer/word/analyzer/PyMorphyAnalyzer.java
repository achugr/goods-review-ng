package ru.goodsreview.analyzer.word.analyzer;


import ru.goodsreview.analyzer.util.dictionary.MapDictionary;
import ru.goodsreview.analyzer.util.sentence.mystem.GrammarGender;

import java.io.*;


/**
 * User: ilya
 * Date: 11.11.12
 */

public class PyMorphyAnalyzer {

    public static String transform(String word, GrammarGender gender) {
        String newWord = word;

        String requiredGender;
        if (gender.equals(GrammarGender.FEMININE)) {
            requiredGender = "жр";
        } else
        if (gender.equals(GrammarGender.MASCULINE)) {
            requiredGender = "мр";
        } else
        if (gender.equals(GrammarGender.NEUTER)) {
            requiredGender = "ср";
        } else{
            requiredGender = "unk";
        }

        if (!requiredGender.equals("unk")) {

        }

        return newWord;
    }





    public static void main(final String[] args) throws IOException {

        System.out.println(transform("нормальный",GrammarGender.FEMININE));


    }



}