package ru.goodsreview.analyzer.util.dictionary;

import java.util.HashSet;

/**
 * Date: 18.07.12
 * Time: 18:20
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class ObserveDict {
    private static HashSet<String> dictionary = new HashSet();

    static {
        dictionary.add("не");
        dictionary.add("более");
        dictionary.add("достаточно");
        dictionary.add("очень");
        dictionary.add("слишком");
        dictionary.add("довольно");
    }


    public static HashSet<String> getDictionary() {
        return dictionary;
    }

}
