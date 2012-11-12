package ru.goodsreview.analyzer.word.analyzer;


import java.io.*;


/**
 * User: ilya
 * Date: 11.11.12
 */

public class PyMorphyAnalyzer {
    private static Process analyzer;


    private PyMorphyAnalyzer() throws IOException {
        try {
            analyzer = Runtime.getRuntime().exec("python analyzeOneWord.py");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        analyzer.destroy();
    }

    public String normalizeWord(String word) {
        try {
            String line;

            BufferedReader bri = new BufferedReader(new InputStreamReader(analyzer.getInputStream()));
        //    BufferedReader bre = new BufferedReader(new InputStreamReader(analyzer.getErrorStream()));

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(analyzer.getOutputStream()));
            writer.write(word+ "\n");
            writer.flush();

            while ((line = bri.readLine()) != null) {
                word = line;
                word = word.toLowerCase();
            }
            bri.close();

//            if ((line = bre.readLine()) != null) {
//                System.out.println(line);
//            }
         //   bre.close();

            analyzer.waitFor();

        } catch (Exception err) {
            err.printStackTrace();
        }
        return word;
    }


    public static void main(final String[] args) throws IOException {
        PyMorphyAnalyzer analyzer1 = new PyMorphyAnalyzer();
        System.out.println(analyzer1.normalizeWord("красивое"));
        close();
    }



}