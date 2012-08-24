package ru.goodsreview.analyzer.util.sentence;

/**
 * Created with IntelliJ IDEA.
 * Date: 21.08.12
 * Author: Ilya Makeev
 */
public class WordProperty {
    private GrammarGender gender;
    private GrammarNumber number;
    private GrammarCase gCase;


    public WordProperty(GrammarGender gender, GrammarNumber number, GrammarCase gCase) {
        this.gender = gender;
        this.number = number;
        this.gCase = gCase;
    }

    public GrammarGender getGender() {
        return gender;
    }

    public GrammarNumber getNumber() {
        return number;
    }

    public GrammarCase getCase() {
        return gCase;
    }

    //TODO снова мутабл
    public void setGender(GrammarGender gender) {
        this.gender = gender;
    }

    public void setNumber(GrammarNumber number) {
        this.number = number;
    }

    public void setCase(GrammarCase gCase) {
        this.gCase = gCase;
    }
}
