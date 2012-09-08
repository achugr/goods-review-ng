package ru.goodsreview.analyzer.newtest;

/**
* Date: 08.07.12
* Time: 01:16
* Author:
* Ilya Makeev
* ilya.makeev@gmail.com
*/

import org.junit.Test;
import ru.goodsreview.analyzer.ExtractThesis;
import ru.goodsreview.analyzer.util.sentence.ReviewTokens;
import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;


import java.io.*;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


public class ExtractionTest {

    private static final String filePath = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/input.xml";
    private static double successExtract = 0;
    private static double numAlgo = 0;
    private static double numHum = 0;

    @Test
    public void extractThesisTest() throws JAXBException, IOException, InterruptedException {

        JAXBContext context = JAXBContext.newInstance(ProductList.class);

        Unmarshaller um = context.createUnmarshaller();
        ProductList productList = (ProductList) um.unmarshal(new FileReader(filePath));

        for (Product p : productList.productList) {
            System.out.println(p.getName());
            for (Review r : p.reviewList) {
                String content = r.getContent();
                System.out.println("review: " + content );
                ArrayList<ru.goodsreview.analyzer.util.Phrase> algoList = ExtractThesis.doExtraction(content);
                for (ru.goodsreview.analyzer.util.Phrase algoPhrase : algoList) {
                    System.out.println("algo:  " + algoPhrase.getFeature() + " " + algoPhrase.getOpinion());
                    for (Phrase phrase : r.phraseList) {
                        if (contains(phrase.getFeature(), algoPhrase.getNormFeature())) {
                            if (contains(phrase.getContext(),algoPhrase.getFeature())&&contains(phrase.getContext(),algoPhrase.getOpinion())) {
                                successExtract++;
                                break;
                            }
                        }
                    }
                }

                numAlgo += algoList.size();
                numHum += r.phraseList.size();

                for (Phrase phrase : r.phraseList) {
                    System.out.println("  " + phrase.getContext());
                    System.out.println("  " + phrase.getFeature());
                    System.out.println("  " + phrase.getOpinion());
                    System.out.println("  " + phrase.getValue());
                }

            }
        }

        System.out.println("successExtract = " + successExtract);
        System.out.println("numAlgo = " + numAlgo);
        System.out.println("numHum = " + numHum);

        if (numAlgo != 0) {
            System.out.println("precision = " + successExtract / numAlgo);
        }
        if (numHum != 0) {
            System.out.println("recall = " + successExtract / numHum);
        }

        ReviewTokens.getWordAnalyzer().close();

    }


    static boolean contains(String sentence, String s) {
        sentence = sentence.toLowerCase();
        if(!s.equals(ReportAnalyzer.UNKNOUN)){
            s = s.toLowerCase();
            return sentence.contains(s);
        }else{
            return false;
        }
    }

    static boolean equals(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        return s1.equals(s2);
    }

    }
