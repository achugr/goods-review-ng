package ru.goodsreview.analyzer.util;



/**
 * Date: 31.03.12
 * Time: 23:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

//TODO мутабл модельные классы -- зло полное
public final class Phrase {
    private final String feature;
    private final String opinion;
    private String normFeature;

    public Phrase(final String feature, final String opinion) {
        this.opinion = opinion;
        this.feature = feature;
    }

    public Phrase(final String feature, final String opinion, String normFeature) {
        this.opinion = opinion;
        this.feature = feature;
        this.normFeature = normFeature;
    }

    public String getNormFeature(){
        return normFeature;
    }



    public String getFeature(){
        return feature;
    }

    public String getOpinion(){
        return opinion;
    }

    public static void main(String[] args) {
        Phrase phrase = new Phrase("", "");
        phrase = new Phrase("", "asd");
    }

}