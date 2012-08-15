package ru.goodsreview.analyzer.util.dictionary;

import java.util.HashSet;

/**
 * Date: 18.07.12
 * Time: 18:20
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

//TODO утилитные классы всегда имеют приватный конструктор и являются абстрактными или файнал
public class ObserveDict {
    //TODO java6 style
    //TODO final
    private static HashSet<String> dictionary = new HashSet();

    static {
        dictionary.add("не");
        dictionary.add("более");
        dictionary.add("достаточно");
        dictionary.add("очень");
        dictionary.add("слишком");
        dictionary.add("довольно");
    }


    //TODO обязательно ли человеку, который будет это использовать знать, что это именно HashSet, а не просто Set?
    public static HashSet<String> getDictionary() {
        return dictionary;
    }

}
