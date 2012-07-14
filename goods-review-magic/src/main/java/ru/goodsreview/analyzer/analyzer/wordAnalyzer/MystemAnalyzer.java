package analyzer.wordAnalyzer;
/*
 *  Date: 12.02.12
 *   Time: 20:51
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

//import org.apache.log4j.Logger;

import analyzer.util.OSValidator;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Class for the analysis of words, using mystem program http://company.yandex.ru/technologies/mystem/
 */
public class MystemAnalyzer implements WordAnalyzer{
    private static MystemAnalyzer instance;

   // private static final Logger log = Logger.getLogger(MystemAnalyzer.class);

    private static final String CHARSET = "UTF8";
    private static final String emptyReport = "";

    private Process analyzer;
    private Scanner sc;
    private PrintStream ps;

    private MystemAnalyzer() {
        try {
            String command = "";
            if (OSValidator.isUnix() || OSValidator.isMac()) {
                command = "./";
            }
            analyzer = Runtime.getRuntime().exec(command + "mystem -nig -e " + CHARSET);
        } catch (IOException e) {

            //   log.error("Caution! Analyzer wasn't created. Check if mystem is installed", e);
//            throw new IOException();
        }

    }



    public static MystemAnalyzer getInstance() {
        if (instance == null) {
            instance = new MystemAnalyzer();
        }
        return instance;
    }


    public void close() {
        analyzer.destroy();
    }



    public static String getEmptyReportValue() {
        return emptyReport;
    }

    /**
     * Checks if letter belongs to russian alphabet.
     * @param letter The letter itself.
     * @return True if letter is russian, false — otherwise.
     */
    private static boolean isRussianLetter(char letter) {
        if ((letter >= 0x0410) && (letter <= 0x044F)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRussianWord(String word) {
        char[] wordChars = word.toCharArray();
        for (int i = 0, j = wordChars.length; i < j; i++) {
            if (!isRussianLetter(wordChars[i])) {
                return false;
            }
        }
        return true;
    }

    public String report(String word) throws UnsupportedEncodingException {
        if (isRussianWord(word)) {
            sc = new Scanner(analyzer.getInputStream(), CHARSET);
            ps = new PrintStream(analyzer.getOutputStream(), true, CHARSET);

            ps.println(word);
            String report = sc.nextLine();

            // System.out.println(report);

            return report;
        } else {
            return emptyReport;
        }
    }

    public static void main(String [] args){
        try {
            MystemAnalyzer mystemAnalyzer = new MystemAnalyzer();
            System.out.println(mystemAnalyzer.report("телефоном"));
            mystemAnalyzer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
