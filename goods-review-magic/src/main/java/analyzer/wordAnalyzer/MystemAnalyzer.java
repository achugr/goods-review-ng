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
import analyzer.util.sentence.PartOfSpeech;

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
    private static final String UNKNOUN = "unk";

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

    public static String getUnkValue() {
        return UNKNOUN;
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

    public String[] wordCharacteristic(String report) {
        String[] a = {UNKNOUN, UNKNOUN, UNKNOUN};

        if (!report.equals(emptyReport)) {
            if (!((report.contains("жен") && report.contains("муж")) ||
                    (report.contains("жен") && report.contains("сред")) ||
                    (report.contains("муж") && report.contains("сред")))) {

                if (report.contains("жен")) {
                    a[0] = "жен";
                }
                if (report.contains("муж")) {
                    a[0] = "муж";
                }
                if (report.contains("сред")) {
                    a[0] = "сред";
                }
            } else {
                // System.out.println(report);
            }

            if (!((report.contains("ед") && report.contains("мн")))) {
                if (report.contains("ед")) {
                    a[1] = "ед";
                }
                if (report.contains("мн")) {
                    a[1] = "мн";
                }
            } else {
                //  System.out.println(report);
            }

            String[] cases = {"им",
                    "род",
                    "дат",
                    "вин",
                    "твор",
                    "пр",
                    "парт",
                    "местн",
                    "зват"
            };

            boolean t1 = false;
            for (int i = 0; i < cases.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (report.contains(cases[i]) && report.contains(cases[j])) {
                        if (i != 3 && j != 0) {
                            t1 = true;
                            break;
                        }
                    }
                }
            }

            if (!t1) {
                for (int i = 0; i < cases.length; i++) {
                    if (report.contains(cases[i])) {
                        a[2] = cases[i];
                        break;
                    }
                }
            } else {
                // System.out.println(report);
            }

        }

        return a;
    }

    public String normalizer(String report) throws UnsupportedEncodingException {
        String norm = UNKNOUN;
        if (!report.equals(emptyReport)) {
            int n = report.indexOf("=");
            if (n != -1) {
                norm = report.substring(report.indexOf("{") + 1, n);
            } else {
                n = report.indexOf("?");
                if (n != -1) {
                    norm = report.substring(report.indexOf("{") + 1, n);
                }
            }

            //   System.out.println(word + " --> " + norm);
        }
        return norm;
    }


    public PartOfSpeech partOfSpeech(String report) throws UnsupportedEncodingException {
        if (!report.equals(emptyReport)) {
            int pos1 = report.indexOf('=') + 1;
            int pos2 = pos1;
            while (Character.isUpperCase(report.charAt(pos2))) {
                pos2++;
            }
            String partOfSpeech = report.substring(pos1, pos2);

            if (partOfSpeech.equals("A")) {
                return PartOfSpeech.ADJECTIVE;
            }
            if (partOfSpeech.equals("S")) {
                return PartOfSpeech.NOUN;
            }
            if (partOfSpeech.equals("ADV")) {
                return PartOfSpeech.ADVERB;
            }
            if (partOfSpeech.equals("V")) {
                return PartOfSpeech.VERB;
            }
            if (partOfSpeech.equals("PR")) {
                return PartOfSpeech.PREPOSITION;
            }
            if (partOfSpeech.equals("PART")) {
                return PartOfSpeech.PARTICLE;
            }
            if (partOfSpeech.equals("")) {
                return PartOfSpeech.UNKNOWN;
            }
        }
        return PartOfSpeech.UNKNOWN;
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
