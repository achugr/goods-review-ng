package ru.goodsreview.analyzer.test;

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

    //private static final String filePath = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/input.xml";
    private static final String filePath = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/tInput.xml";
   // private static final String filePath = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/marking_output.xml";
    private static double successExtract = 0;
    private static double numAlgo = 0;
    private static double numHum = 0;

    @Test
    public void extractThesisTest() throws JAXBException, IOException, InterruptedException {

        JAXBContext context = JAXBContext.newInstance(ProductList.class);

        Unmarshaller um = context.createUnmarshaller();
        ProductList productList = (ProductList) um.unmarshal(new FileReader(filePath));

        String path = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/result.xml";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));

        for (Product product : productList.productList) {
            out.println("<product name=\"" + product.getName() + "\">");
            if (product.reviewList != null) {
                for (Review review : product.reviewList) {
                    String content = review.getContent();
                    out.println("     <review>");
                    out.println("        <content>" + content + "</content>");
                    ArrayList<ru.goodsreview.analyzer.util.Phrase> algoList = ExtractThesis.doExtraction(content);

                    for (ru.goodsreview.analyzer.util.Phrase algoPhrase : algoList) {
                        // System.out.println("algo:  " + algoPhrase.getFeature() + " " + algoPhrase.getOpinion());
                        if (review.phraseList != null) {
                            for (Phrase phrase : review.phraseList) {
                                if (contains(phrase.getFeature(), algoPhrase.getNormFeature())) {
                                    if (contains(phrase.getContext(), algoPhrase.getFeature()) && contains(phrase.getContext(), algoPhrase.getOpinion())) {
                                        successExtract++;
                                        out.println("            <OK>" + algoPhrase.getFeature() + " " + algoPhrase.getOpinion() + "</OK>");
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (ru.goodsreview.analyzer.util.Phrase algoPhrase : algoList) {
                        boolean t = false;
                        if (review.phraseList != null) {
                            for (Phrase phrase : review.phraseList) {
                                if (contains(phrase.getFeature(), algoPhrase.getNormFeature())) {
                                    if (contains(phrase.getContext(), algoPhrase.getFeature()) && contains(phrase.getContext(), algoPhrase.getOpinion())) {
                                        t = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!t) {
                            out.println("            <algo>" + algoPhrase.getFeature() + " " + algoPhrase.getOpinion() + "</algo>");
                        }
                    }


                    if (review.phraseList != null) {
                        for (Phrase phrase : review.phraseList) {
                            boolean t = false;
                            for (ru.goodsreview.analyzer.util.Phrase algoPhrase : algoList) {
                                if (contains(phrase.getFeature(), algoPhrase.getNormFeature())) {
                                    if (contains(phrase.getContext(), algoPhrase.getFeature()) && contains(phrase.getContext(), algoPhrase.getOpinion())) {
                                        t = true;
                                        break;
                                    }
                                }
                            }
                            if (!t) {
                                out.println("            <hum>" + phrase.getFeature() + " " + phrase.getOpinion() + "</hum>");
                            }
                        }
                    }


                    numAlgo += algoList.size();
                    if (review.phraseList != null) {
                        numHum += review.phraseList.size();
                    }


                    out.println("      </review>");
                }
            }
            out.println("</product>");
        }

        out.flush();

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
        if (!s.equals(ReportAnalyzer.UNKNOUN)) {
            s = s.toLowerCase();
            return sentence.contains(s);
        } else {
            return false;
        }
    }

    static boolean equals(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        return s1.equals(s2);
    }

}
