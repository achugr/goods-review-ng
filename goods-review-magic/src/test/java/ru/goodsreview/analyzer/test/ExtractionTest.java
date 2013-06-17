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
import java.util.HashMap;
import java.util.List;

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
        int k1 = 1;
        for (Product product : productList.productList) {
            out.println("<product name=\"" + product.getName() + "\">");
            if (product.reviewList != null) {
                for (Review review : product.reviewList) {
                    String content = review.getContent();
                    out.println("     <review>");
                    out.println("        <content>" + content + "</content>");
                    System.out.println( "#"+k1++ + content );
                    List<ru.goodsreview.analyzer.util.Phrase> algoList = ExtractThesis.doExtraction(content);

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

//        ReviewTokens.getWordAnalyzer().close();

    }

    @Test
    public void tomitaExtractionTest() throws JAXBException, IOException, InterruptedException {
        HashMap<String, List<String>> tomitaData = TomitaParser.getData();
      //  System.out.println( tomitaData.size());
        JAXBContext context = JAXBContext.newInstance(ProductList.class);

        Unmarshaller um = context.createUnmarshaller();
        ProductList productList = (ProductList) um.unmarshal(new FileReader(filePath));

        String path = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/result.xml";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
        int k1 = 0;
        for (Product product : productList.productList) {
            out.println("<product name=\"" + product.getName() + "\">");
            if (product.reviewList != null) {
                for (Review review : product.reviewList) {
                    String content = review.getContent();
                    out.println("     <review>");
                    out.println("        <content>" + content + "</content>");
                    k1++;
                   // System.out.println(k1);
                   // System.out.println( "#"+k1 + content );
                    //List<ru.goodsreview.analyzer.util.Phrase> algoList = ExtractThesis.doExtraction(content);

                    List<String> algoList;
                    String key = Integer.toString(k1);
                    if(tomitaData.containsKey(key)){
                        algoList = tomitaData.get(key);

                    }else{
                        algoList = new ArrayList<String>();
                    }


                    for (String algoPhrase : algoList) {
                        // System.out.println("algo:  " + algoPhrase.getFeature() + " " + algoPhrase.getOpinion());
                        if (review.phraseList != null) {
                            for (Phrase phrase : review.phraseList) {
                                if (symContains(algoPhrase,phrase.getFeature())) {

                                        successExtract++;
                                        out.println("            <OK>" + algoPhrase + "</OK>");
                                        break;

                                }
                            }
                        }
                    }

                    for (String algoPhrase : algoList) {
                        boolean t = false;
                        if (review.phraseList != null) {
                            for (Phrase phrase : review.phraseList) {
                                if (symContains(algoPhrase,phrase.getFeature())){

                                        t = true;
                                        break;

                                }
                            }
                        }
                        if (!t) {
                            out.println("            <algo>" + algoPhrase+  "</algo>");
                        }
                    }


                    if (review.phraseList != null) {
                        for (Phrase phrase : review.phraseList) {
                            boolean t = false;
                            for (String algoPhrase : algoList) {
                                if (symContains(algoPhrase,phrase.getFeature())){
                                   // if (contains(phrase.getContext(), algoPhrase.getFeature()) && contains(phrase.getContext(), algoPhrase.getOpinion())) {
                                        t = true;
                                        break;
                                    //}
                                }
                            }
                            if (!t) {
                                out.println("            <hum>" + phrase.getFeature() + "</hum>");
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

//        ReviewTokens.getWordAnalyzer().close();

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

    static boolean symContains(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        return s1.contains(s2)||s2.contains(s1);
    }

    static boolean equals(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        return s1.equals(s2);
    }

}
