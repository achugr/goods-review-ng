package ru.goodsreview.analyzer.tool;
/**
 * author : Ilya Makeev
 * date: 08.09.12
 */


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class Product {
    private String name;

    @XmlElement(name = "review")
     ArrayList<Review> reviewList;


    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String content) {
        this.name = content;
    }


}

