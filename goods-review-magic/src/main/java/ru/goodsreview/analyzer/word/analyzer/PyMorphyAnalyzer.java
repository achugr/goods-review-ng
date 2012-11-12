package ru.goodsreview.analyzer.word.analyzer;


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

    public String transform(String word, String gender) {
        String newWord = word;
        String command = word+"#"+gender+"\n";
        try {
            String line;

            BufferedReader bri = new BufferedReader(new InputStreamReader(analyzer.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(analyzer.getErrorStream()));

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(analyzer.getOutputStream()));
            writer.write(command);
            writer.flush();

            while ((line = bri.readLine()) != null) {
                newWord = line.toLowerCase();
            }
            bri.close();

            while ((line = bre.readLine()) != null) {
                System.out.println(line);
            }
            bre.close();

            analyzer.waitFor();

        } catch (Exception err) {
            err.printStackTrace();
        }
        return newWord;
    }


    public static void main(final String[] args) throws IOException {
        PyMorphyAnalyzer analyzer1 = new PyMorphyAnalyzer("analyzeOneWord.py");
        System.out.println(analyzer1.transform("красивое","мр"));
        close();
    }



}