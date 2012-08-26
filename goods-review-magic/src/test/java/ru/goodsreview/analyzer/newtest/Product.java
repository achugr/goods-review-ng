package ru.goodsreview.analyzer.newtest;
/**
 * Date: 08.07.12
 * Time: 01:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import java.util.ArrayList;
import javax.xml.bind.annotation.*;

public class Product{
    private String name;

    @XmlElementWrapper(name = "reviewList")
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

