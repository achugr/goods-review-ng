package ru.goodsreview.analyzer.newtest;

/**
 * Date: 08.07.12
 * Time: 01:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//@XmlRootElement
@XmlType(propOrder = { "feature","opinion", "value"})
public class Phrase {
    private String feature;
    private String opinion;
    private String value;


    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
