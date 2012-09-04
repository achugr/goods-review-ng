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
import ru.goodsreview.analyzer.util.sentence.*;
import ru.goodsreview.analyzer.util.sentence.mystem.GrammarCase;
import ru.goodsreview.analyzer.util.sentence.mystem.GrammarGender;
import ru.goodsreview.analyzer.util.sentence.GrammarNumber;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for the analysis of words, using mystem program http://company.yandex.ru/technologies/mystem/
 */
public class MystemAnalyzer  implements WordAnalyzer{
    private Process analyzerProcess;
    private Scanner sc;
    private PrintStream ps;

    public static final String EMPTY_REPORT = "";

    private final static Logger log = Logger.getLogger(MystemAnalyzer.class);

    @Required
    public void setAnalyzerProcess(String path) {
        try {
           String CHARSET = "UTF8";
           String command = "./"+ path + "mystem -ni -e " + CHARSET;

            analyzerProcess = Runtime.getRuntime().exec(command);
            sc = new Scanner(analyzerProcess.getInputStream(), CHARSET);
            ps = new PrintStream(analyzerProcess.getOutputStream(), true, CHARSET);

        } catch (IOException e) {
            log.error("Caution! Analyzer wasn't created. Check if mystem is installed", e);
            throw new RuntimeException(e);
        }
    }


    public void close() {
        analyzerProcess.destroy();
    }

    public  List<Token> getTokenList(String word) throws UnsupportedEncodingException {
        ArrayList<Token> tokensList = new ArrayList<Token>();

        String mystemReport =  report(word);

        if (!mystemReport.equals(EMPTY_REPORT)) {
            List<String> reportList = MystemReportAnalyzer.buildReportList(mystemReport);

            for (String rep : reportList) {
                if (!rep.equals(EMPTY_REPORT)) {
                    int k = rep.indexOf("=");
                    if (k != -1 && k + 1 < rep.length()) {
                        //without normForm
                        String tRep = rep.substring(k + 1);

                            String normForm = MystemReportAnalyzer.getNormForm(rep);

                            PartOfSpeech partOfSpeech = MystemReportAnalyzer.getPartOfSpeech(tRep);

                            GrammarGender gender = MystemReportAnalyzer.getGender(tRep);
                            GrammarNumber number =  MystemReportAnalyzer.getNum(tRep);
                            GrammarCase caseOf =  MystemReportAnalyzer.getCase(tRep);

                            Token newToken = new Token(word, normForm, partOfSpeech, gender.toString(), number.toString(), caseOf.toString());
                            tokensList.add(newToken);

                    } else {
                        // System.out.println(rep);
                    }

                }
            }
        }

        if(tokensList.size()==0){
            tokensList.add(new Token(word, ReportAnalyzer.UNKNOUN,PartOfSpeech.UNKNOWN, ReportAnalyzer.UNKNOUN, ReportAnalyzer.UNKNOUN,ReportAnalyzer.UNKNOUN));
        }

        return tokensList;
    }


    public String report(final String word)  {
        if (ReportAnalyzer.isRussianWord(word)) {
            //TODO а если процесс не ответит, ну подвиснет на секунду?
            ps.println(word);

//            while (!sc.hasNext()){
//                try {
//                   sc.wait(100);
//                } catch (InterruptedException e) {
//                    log.error(e.getMessage(), e);
//                }
//           }
            return sc.nextLine();
        } else {
            return EMPTY_REPORT;
        }
    }



}
