package ru.goodsreview.analyzer.tool;

import org.junit.Test;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;


/**
 * author : Ilya Makeev
 * date: 08.09.12
 */
public class LayoutGenerator {
    private static final String filePath = "goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/marking_input.xml";


    @Test
    public void generate() throws JAXBException, IOException, InterruptedException {

        JAXBContext context = JAXBContext.newInstance(ProductList.class);

        Unmarshaller um = context.createUnmarshaller();
        ProductList productList = (ProductList) um.unmarshal(new FileReader(filePath));

        for (Product p : productList.productList) {
            System.out.println(p.getName());

            for (Review r : p.reviewList) {
                String content = r.getValue();
                System.out.println("review: " + r.getQuality());
                System.out.println("review: " + content);

            }


        }


    }

}
