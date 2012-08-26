package ru.goodsreview.analyzer.util;

/*
 *  Date: 12.02.12
 *   Time: 20:51
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */
public class OSValidator {
    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    public static boolean isUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("nix") || os.contains("nux");
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac");
    }
}

