package analyzer.wordAnalyzer;

import analyzer.util.sentence.GrammarCase;
import analyzer.util.sentence.PartOfSpeech;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Date: 13.07.12
 * Time: 23:45
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class ReportAnalyzer {
    public static final String UNKNOUN = "unk";

    static boolean isCorrect (String report){
        boolean t1  = report.contains("жен")||report.contains("муж")||report.contains("сред");
        boolean t2  = report.contains("ед")||report.contains("мн");
        boolean t3  = report.contains("им")||report.contains("род")||report.contains("дат")||
                report.contains("вин")||report.contains("твор")||report.contains("пр")||
                report.contains("парт")||report.contains("местн")||report.contains("зват");

        return t1 && t2 && t3;
    }

    public static String[] wordCharacteristic(String report) {
        String[] a = {UNKNOUN, UNKNOUN, UNKNOUN};

        if (!report.equals(MystemAnalyzer.getEmptyReportValue())&&isCorrect(report)) {
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
                //  System.out.println(report);
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

            GrammarCase[] cases = GrammarCase.values();

            boolean t1 = false;
            for (int i = 0; i < cases.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (report.contains(cases[i].toString()) && report.contains(cases[j].toString())) {
                        if (i != 3 && j != 0) {
                            t1 = true;
                            break;
                        }
                    }
                }
            }

            if (!t1) {
                for (GrammarCase aCase : cases) {
                    if (report.contains(aCase.toString())) {
                        a[2] = aCase.toString();
                        break;
                    }
                }
            } else {
                // System.out.println(report);
            }

        }

        return a;
    }


    public static PartOfSpeech partOfSpeech(String report) throws UnsupportedEncodingException {
        if (!report.equals(MystemAnalyzer.getEmptyReportValue())) {
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

    public static String normalizer(String report) throws UnsupportedEncodingException {
        String norm = UNKNOUN;
        if (!report.equals(MystemAnalyzer.getEmptyReportValue())) {
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

    public static ArrayList<String> buildReportList(String report) throws UnsupportedEncodingException {
        ArrayList<String> reportList = new ArrayList<String>();
        if (!report.equals(MystemAnalyzer.getEmptyReportValue())) {
            String norm = normalizer(report);
            ArrayList<String> normList = new ArrayList<String>();
            buildNormList(normList, report, norm);

            for (String s : normList) {
                int n1 = s.indexOf("(");
                int n2 = s.indexOf(")");
                if (n1 != -1 && n1 < n2) {
                    String s1 = s.substring(n1 + 1, n2);

                    StringTokenizer stringTokenizer = new StringTokenizer(s1, "|");
                    while (stringTokenizer.hasMoreElements()) {
                        String str = stringTokenizer.nextToken();
                        reportList.add(s.substring(0, n1) + "," + str);
                    }
                } else {
                    reportList.add(s);
                }
            }


        }
//        for (String s:reportList){
//            System.out.println(s);
//        }
        return reportList;
    }

    static void buildNormList(ArrayList<String> list, String report, String norm) {
        int n1 = report.indexOf("|" + norm);
        if (n1 == -1) {
            list.add(report);
        } else {
            list.add(report.substring(0, n1));
            buildNormList(list, report.substring(n1 + norm.length() + 1), norm);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
       String s = "сборки{сборка=S,жен,неод=(им,мн|род,ед|вин,мн)}";
       String s1 = "модели{модель=S,жен,неод=(им,мн|род,ед|дат,ед|вин,мн|пр,ед)|модель=S,жен,од=(им,мн|род,ед|дат,ед|вин,мн|пр,ед)}";
       String s2 = "телефоном{телефон=S,муж,неод=твор,ед}";
       String s3 = "достаточно{достаточный=A=ед,кр,сред|достаточно=ADV=|достаточно=ADV,прдк=}";
       buildReportList(s);

    }
}
