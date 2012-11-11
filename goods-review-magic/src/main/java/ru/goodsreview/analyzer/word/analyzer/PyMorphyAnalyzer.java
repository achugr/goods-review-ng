package ru.goodsreview.analyzer.word.analyzer;


import java.io.*;
import java.util.Scanner;

/**
 * User: ilya
 * Date: 11.11.12
 */

public class PyMorphyAnalyzer {
    private static Process analyzer;


    PyMorphyAnalyzer() throws IOException {
        try {
            analyzer = Runtime.getRuntime().exec("python analyzeOneWord.py");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        analyzer.destroy();
    }

    public String normalizeWord(String word) {
        OutputStream stdin = analyzer.getOutputStream();
        InputStream stdout = analyzer.getInputStream();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        Scanner scanner = new Scanner(stdout);
        PrintWriter writer = new PrintWriter(stdin);
        InputStreamReader reader = new InputStreamReader(stdout);
//        OutputStreamWriter writer = new OutputStreamWriter(stdin);

        writer.write(word);
        writer.flush();

        String line;
//        while ((line = reader.readLine()) != null) {
//            return line;
//        }

        while (scanner.hasNext()){
            System.out.println(scanner.next());
        }
        return "1";
    }

    public static String getNormalizedWord(String s) throws IOException {
        String res = s;
        PyMorphyAnalyzer pyMorphyAnalyzer = new PyMorphyAnalyzer();
        try {

            res = pyMorphyAnalyzer.normalizeWord(s);

        } finally {
            pyMorphyAnalyzer.close();
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getNormalizedWord("клавиатуры"));

    }
}