package ru.goodsreview.analyzer.tool;

/**
 * author : Ilya Makeev
 * date: 08.09.12
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class ProductList{

    @XmlElement(name = "product")
    ArrayList<Product> productList;

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }


}