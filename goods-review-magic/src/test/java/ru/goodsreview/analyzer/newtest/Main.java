package ru.goodsreview.analyzer.newtest;

/**
 * Date: 08.07.12
 * Time: 01:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



public class Main {

    private static final String BOOKSTORE_XML = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/input.xml";

    public static void main(String[] args) throws JAXBException, IOException {


      JAXBContext context = JAXBContext.newInstance(ProductList.class);


        System.out.println("Output from our XML File: ");
        Unmarshaller um = context.createUnmarshaller();
        ProductList productList = (ProductList) um.unmarshal(new FileReader(BOOKSTORE_XML));

        for (Product p:productList.productList){
            System.out.println(p.getName());
            for (Review r:p.reviewList){
                System.out.println("review: "+r.getContent());
                for (Phrase phrase:r.phraseList){
                    System.out.println("  "+phrase.getFeature());
                    System.out.println("  "+phrase.getOpinion());
                    System.out.println("  "+phrase.getValue());
                }
            }

        }


    }
}
