package ru.goodsreview.analyzer.word.analyzer;
/*
 *  Date: 12.02.12
 *   Time: 20:51
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsreview.analyzer.util.sentence.Token;
import ru.goodsreview.analyzer.util.sentence.WordProperties;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for the analysis of words, using mystem program http://company.yandex.ru/technologies/mystem/
 */
public class MystemAnalyzer implements WordAnalyzer{
    private Process analyzerProcess;
    private Scanner sc;
    private PrintStream ps;

    public static final String EMPTY_REPORT = "";

    private final static Logger log = Logger.getLogger(MystemAnalyzer.class);

    @Required
    public void setAnalyzerProcess(String path) {
        try {
            String CHARSET = "UTF8";
            String command = "./";

           // String command = "";   //for Windows

            analyzerProcess = Runtime.getRuntime().exec(command + path + "mystem -nig -e " + "UTF8");
            sc = new Scanner(analyzerProcess.getInputStream(), CHARSET);
            ps = new PrintStream(analyzerProcess.getOutputStream(), true, CHARSET);

        } catch (IOException e) {
            // log.error("Caution! Analyzer wasn't created. Check if mystem is installed", e);
            throw new RuntimeException(e);
        }
    }


    public void close() {
        analyzerProcess.destroy();
    }

    public  ArrayList<Token> getTokenList(String word) throws UnsupportedEncodingException {
        ArrayList<Token> tokensList = new ArrayList<Token>();

        String mystemReport =  report(word);

        if (!mystemReport.equals(EMPTY_REPORT)) {
            List<String> reportList = ReportAnalyzer.buildReportList(mystemReport);

            for (String rep : reportList) {
                if(!rep.equals(EMPTY_REPORT)){
                    if(ReportAnalyzer.isCorrect(rep)){
                        String normForm = ReportAnalyzer.normalizer(rep);

                        PartOfSpeech partOfSpeech = ReportAnalyzer.partOfSpeech(rep);

                        WordProperties wordProp = ReportAnalyzer.wordProperties(rep);
                        String gender = wordProp.getGender().toString();
                        String number = wordProp.getNumber().toString();
                        String caseOf = wordProp.getCase().toString();

                        Token newToken = new Token(word, normForm, partOfSpeech, gender, number, caseOf);
                        tokensList.add(newToken);
                    }
                }
            }
        }


        return tokensList;
    }


    public String report(final String word) {
        if (isRussianWord(word)) {
            //TODO а если процесс не ответит, ну подвиснет на секунду?
            ps.println(word);
            while (!sc.hasNext()){
                try {
                    sc.wait(100);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
            return sc.nextLine();
        } else {
            return EMPTY_REPORT;
        }
    }

    /**
     * Checks if letter belongs to russian alphabet.
     *
     * @param letter The letter itself.
     * @return True if letter is russian, false — otherwise.
     */
    private static boolean isRussianLetter(char letter) {
        return (letter >= 0x0410) && (letter <= 0x044F);
    }

    public static boolean isRussianWord(String word) {
        for (char c : word.toCharArray()) {
            if (!isRussianLetter(c)) {
                return false;
            }
        }
        return true;
    }

}
