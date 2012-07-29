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


//@XmlRootElement
@XmlType(propOrder = { "content", "phraseList"})
public class Review {
    private String content;
    private String score;


    @XmlElementWrapper(name = "phraseList")
    @XmlElement(name = "phrase")
    ArrayList<Phrase> phraseList;


    public void setPhraseList(ArrayList<Phrase> phraseList) {
        this.phraseList = phraseList;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlAttribute
    public void setScore(String content) {
        this.score = content;
    }

    public String getScore() {
        return score;
    }

}
