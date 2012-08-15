package ru.goodsreview.analyzer.util;



/**
 * Date: 31.03.12
 * Time: 23:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

//TODO мутабл модельные классы -- зло полное
public class Phrase {
    private String feature;
    private String opinion;


    public Phrase(String feature, String opinion) {
        this.opinion = opinion;
        this.feature = feature;
    }

    public String getFeature(){
        return feature;
    }

    public String getOpinion(){
        return opinion;
    }

}