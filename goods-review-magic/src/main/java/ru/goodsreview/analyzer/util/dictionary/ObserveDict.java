package ru.goodsreview.analyzer.util.dictionary;

import java.util.HashSet;
import java.util.Set;

/**
 * Date: 18.07.12
 * Time: 18:20
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

//TODO утилитные классы всегда имеют приватный конструктор и являются абстрактными или файнал
public final class ObserveDict implements Dictionary<String>{
    private final static Set<String> dictionary = new HashSet<String>();

    static {
        dictionary.add("не");
        dictionary.add("более");
        dictionary.add("достаточно");
        dictionary.add("очень");
        dictionary.add("слишком");
        dictionary.add("довольно");
    }

    @Override
    public boolean contains(final String key){
        return dictionary.contains(key);
    }

    public void print() {
        for (String word : dictionary) {
            System.out.println(word);
        }
    }

}
