package ru.goodsreview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

//TODO мутабл модельные классы -- зло полное
public class Token {
    private String content;
    private PartOfSpeech partOfSpeech;
    private String normForm;
    private GrammarGender gender;
    private GrammarNumber number;
    private GrammarCase caseOf;

    public Token(String content, String normForm, PartOfSpeech partOfSpeech, GrammarGender gender, GrammarNumber number,GrammarCase caseOf) {
        this.content = content;
        this.normForm = normForm;
        this.partOfSpeech = partOfSpeech;
        this.gender = gender;
        this.number = number;
        this.caseOf = caseOf;
    }

    public String getContent() {
        return content;
    }

    public String getNormForm() {
        return normForm;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GrammarGender getGender() {
        return gender;
    }

    public GrammarNumber getNumber() {
        return number;
    }

    public GrammarCase getCase() {
        return caseOf;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
