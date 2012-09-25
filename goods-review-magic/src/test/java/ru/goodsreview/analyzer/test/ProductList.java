package ru.goodsreview.analyzer.test;

/**
 * Date: 08.07.12
 * Time: 01:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Date: 08.07.12
 * Time: 01:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

@XmlRootElement
public class ProductList{

    @XmlElement(name = "product")
    ArrayList<Product> productList;

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }


}
