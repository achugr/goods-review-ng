package analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

public class Token {
    private String content;
    private PartOfSpeech partOfSpeech;
    private String normForm;
    private String gender;
    private String number;
    private String caseOf;

    public Token(String content, String normForm, PartOfSpeech partOfSpeech, String gender, String number,String caseOf) {
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

    public String getGender() {
        return gender;
    }

    public String getNumber() {
        return number;
    }

    public String getCaseOf() {
        return caseOf;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
