package ru.goodsreview.analyzer.util;


import ru.goodsreview.core.model.Thesis;

/**
 * Date: 31.03.12
 * Time: 23:16
 * Author:
 * Ilya Makeev
 */

//TODO мутабл модельные классы -- зло полное
public final class Phrase implements Thesis {
    private final String feature;
    private final String opinion;
    private String normFeature;
    private String normOpinion;
    private double importace = 0;
    private double sentiment = 0;


    public Phrase(final String feature, final String opinion, String normFeature, String normOpinion, double sentiment) {
        this.opinion = opinion;
        this.feature = feature;
        this.normFeature = normFeature;
        this.normOpinion = normOpinion;
        this.sentiment = sentiment;
    }

    public String getNormFeature(){
        return normFeature;
    }

    public String getNormOpinion(){
        return normOpinion;
    }

    public String getFeature(){
        return feature;
    }

    public String getOpinion(){
        return opinion;
    }

    public String toString() {
        return feature + " " + opinion;
    }

    @Override
    public String getValue() {
        return feature + " " + opinion;
    }

    @Override
    public String getNormValue() {
        return normFeature + " " + normOpinion;
    }

    @Override
    public double getImportance() {
        return importace;
    }

    @Override
    public double getSentiment() {
        return sentiment;
    }

}