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
import ru.goodsreview.analyzer.word.analyzer.MystemAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;

import java.io.*;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class NewExtractionTest {

    private static final String BOOKSTORE_XML = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/input.xml";
    private static double successExtract = 0;
    private static double numAlgo = 0;
    private static double numHum = 0;

    @Test
    public void extractThesisTest() throws JAXBException, IOException, InterruptedException {

        JAXBContext context = JAXBContext.newInstance(ProductList.class);

        System.out.println("Output from our XML File: ");
        Unmarshaller um = context.createUnmarshaller();
        ProductList productList = (ProductList) um.unmarshal(new FileReader(BOOKSTORE_XML));

        for (Product p : productList.productList) {
            System.out.println(p.getName());
            for (Review r : p.reviewList) {
                System.out.println("review: " + r.getContent());
                ArrayList<ru.goodsreview.analyzer.util.Phrase> algoList = ExtractThesis.doExtraction(r.getContent());
                for (ru.goodsreview.analyzer.util.Phrase algoPhrase : algoList) {
                    System.out.println("algo:  " + algoPhrase.getFeature() + " " + algoPhrase.getOpinion());
                    for (Phrase phrase : r.phraseList) {
                        if (match(algoPhrase.getFeature(), phrase.getFeature()) && match(algoPhrase.getOpinion(), phrase.getOpinion())) {
                            successExtract++;
                            break;
                        }
                    }
                }
                numAlgo += algoList.size();
                numHum += r.phraseList.size();
                for (Phrase phrase : r.phraseList) {
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

        MystemAnalyzer.getInstance().close();
    }

    static boolean match(String s1, String s2) throws UnsupportedEncodingException {
        return ReportAnalyzer.normalizer(MystemAnalyzer.getInstance().report(s1)).equals(ReportAnalyzer.normalizer(MystemAnalyzer.getInstance().report(s2)));
    }
}
