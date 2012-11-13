package ru.goodsreview.analyzer.word.analyzer;


import ru.goodsreview.analyzer.util.dictionary.MapDictionary;
import ru.goodsreview.analyzer.util.sentence.mystem.GrammarGender;

import java.io.*;


/**
 * User: ilya
 * Date: 11.11.12
 */

public class PyMorphyAnalyzer {
    private static Process analyzer;


    public PyMorphyAnalyzer(String path) {
        try {
            analyzer = Runtime.getRuntime().exec("python "+path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        analyzer.destroy();
    }

//    public String transform(String word, GrammarGender gender) {
//        String newWord = word;
//
//        String requiredGender;
//        if (gender.equals(GrammarGender.FEMININE)) {
//            requiredGender = "жр";
//        } else
//        if (gender.equals(GrammarGender.MASCULINE)) {
//            requiredGender = "мр";
//        } else
//        if (gender.equals(GrammarGender.NEUTER)) {
//            requiredGender = "ср";
//        } else{
//            requiredGender = "unk";
//        }
//
//        if (!requiredGender.equals("unk")) {
//            String command = word + "#" + requiredGender + "\n";
//            try {
//                String line;
//
//                BufferedReader bri = new BufferedReader(new InputStreamReader(analyzer.getInputStream()));
//                BufferedReader bre = new BufferedReader(new InputStreamReader(analyzer.getErrorStream()));
//
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(analyzer.getOutputStream()));
//                writer.write(command);
//                writer.flush();
//
//                while ((line = bri.readLine()) != null) {
//                    newWord = line.toLowerCase();
//                }
//                bri.close();
//
//                while ((line = bre.readLine()) != null) {
//                    System.out.println(line);
//                }
//                bre.close();
//
//                analyzer.waitFor();
//
//            } catch (Exception err) {
//                err.printStackTrace();
//            }
//        }
//
//        return newWord;
//    }

//    public void printDic(String str) {
//
//            String command = str + "\n";
//            try {
//                String line;
//
//               // BufferedReader bri = new BufferedReader(new InputStreamReader(analyzer.getInputStream()));
//                BufferedReader bre = new BufferedReader(new InputStreamReader(analyzer.getErrorStream()));
//
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(analyzer.getOutputStream()));
//                writer.write(command);
////                writer.flush();
//
////                while ((line = bri.readLine()) != null) {
////                    newWord = line.toLowerCase();
////                }
////                bri.close();
//
//                while ((line = bre.readLine()) != null) {
//                    System.out.println(line);
//                }
//                bre.close();
//
//                analyzer.waitFor();
//
//            } catch (Exception err) {
//                err.printStackTrace();
//            }
//
//    }
//
//
//
//    public static void main(final String[] args) throws IOException {
//        PyMorphyAnalyzer analyzer1 = new PyMorphyAnalyzer("analyzeOneWord.py");
//     //   System.out.println(analyzer1.transform("нормальный",GrammarGender.FEMININE));
//        String str = MapDictionary.keysToString();
//        analyzer1.printDic("сверхскоростной досадный практичный фирменный");
//        close();
//    }



}