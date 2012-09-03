package ru.goodsreview.analyzer.word.analyzer;

import ru.goodsreview.analyzer.util.sentence.GrammarNumber;

/**
 * author : Ilya Makeev
 * date: 04.09.12
 */
public abstract class ReportAnalyzer {
    public static final String UNKNOUN = "unk";
    private static GrammarNumber[] numCases = GrammarNumber.values();


    public static GrammarNumber getNum(String report) {
        for (GrammarNumber num : numCases) {
            if (report.contains(num.toString())) {
                return num;
            }
        }

        return GrammarNumber.UNKNOWN;
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
