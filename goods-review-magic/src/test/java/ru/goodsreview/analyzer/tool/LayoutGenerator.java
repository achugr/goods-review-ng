package ru.goodsreview.analyzer.tool;

import ru.goodsreview.analyzer.newtest.Phrase;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * author : Ilya Makeev
 * date: 08.09.12
 */
public class LayoutGenerator {
    private static final String inputFilePath = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/marking_input.xml";
    private static final String outputFilePath = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/marking_output.xml";


    public static void main(String[] args) throws JAXBException, IOException, InterruptedException {

        JAXBContext jaxbContext = JAXBContext.newInstance(ProductList.class);

        Unmarshaller um = jaxbContext.createUnmarshaller();
        ProductList productList = (ProductList) um.unmarshal(new FileReader(inputFilePath));

        ru.goodsreview.analyzer.newtest.ProductList outProductList = new  ru.goodsreview.analyzer.newtest.ProductList();
        ArrayList<ru.goodsreview.analyzer.newtest.Product> newProductList = new  ArrayList<ru.goodsreview.analyzer.newtest.Product>();

        Scanner in = new Scanner(System.in);
        for (Product product : productList.productList) {
           // System.out.println(p.getName());

            ru.goodsreview.analyzer.newtest.Product newProduct = new  ru.goodsreview.analyzer.newtest.Product();
            newProduct.setName(product.getName());

            ArrayList<ru.goodsreview.analyzer.newtest.Review> newReviewList = new  ArrayList<ru.goodsreview.analyzer.newtest.Review>();
            for (Review review : product.reviewList) {
                //System.out.println("review: " + r.getQuality());
                System.out.println("review: " + review.getValue());

                ru.goodsreview.analyzer.newtest.Review newReview = new  ru.goodsreview.analyzer.newtest.Review();
                newReview.setQuality(review.getQuality());
                newReview.setContent(review.getValue());

                ArrayList<Phrase> newPhraseList = new  ArrayList<Phrase>();

                boolean t = true;
                while (t){
                    System.out.println("добавить фразу?(y/n)");
                    String more = in.nextLine();
                    if(more.equals("y")){
                        Phrase phrase = new Phrase();

                        System.out.println("context:");
                         String context=in.nextLine();
                        System.out.println("feature:");
                         String feature=in.nextLine();
                        System.out.println("opinion:");
                         String opinion=in.nextLine();
                        System.out.println("value:");
                         String value=in.nextLine();

                        phrase.setContext(context);
                        phrase.setFeature(feature);
                        phrase.setOpinion(opinion);
                        phrase.setValue(value);

                        newPhraseList.add(phrase);
                    }else{
                        t = false;
                    }

                }

                if(newPhraseList.size()!=0){
                    newReview.setPhraseList(newPhraseList);
                }

                newReviewList.add(newReview);
            }
            newProduct.setReviewList(newReviewList);
            newProductList.add(newProduct);

        }
        outProductList.setProductList(newProductList);


        File file = new File(outputFilePath);
        JAXBContext jaxbContext1 = JAXBContext.newInstance(ru.goodsreview.analyzer.newtest.ProductList.class);
        Marshaller jaxbMarshaller = jaxbContext1.createMarshaller();


        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal( outProductList, file);
        //jaxbMarshaller.marshal( newProductList, System.out);
    }

}
