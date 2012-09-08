package ru.goodsreview.analyzer.tool;

/**
 * author : Ilya Makeev
 * date: 08.09.12
 */


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


public class Review {
    private String quality;
    private String value;

    Review() {
    }

    public Review(String quality, String value) {
        this.quality = quality;
        this.value = value;
    }

    @XmlAttribute
    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
