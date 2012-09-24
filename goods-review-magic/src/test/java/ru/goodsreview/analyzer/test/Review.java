package ru.goodsreview.analyzer.test;

/**
 * Date: 08.07.12
 * Time: 01:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import java.util.ArrayList;
import javax.xml.bind.annotation.*;

@XmlType(propOrder = { "content", "phraseList"})
public class Review {
    private String content;
    private String quality;


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
    public void setQuality(String content) {
        this.quality = content;
    }

    public String getQuality() {
        return quality;
    }

}
