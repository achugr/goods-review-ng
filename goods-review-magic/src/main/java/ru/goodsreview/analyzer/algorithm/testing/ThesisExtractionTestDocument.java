package ru.goodsreview.analyzer.algorithm.testing;

/**
 * Date: 05.02.12
 * Time: 21:18
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import ru.goodsreview.analyzer.ExtractThesis;
import ru.goodsreview.analyzer.word.analyzer.MystemAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.ReportAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ThesisExtractionTestDocument {
    private static double successExtract = 0;
    private static double numAlgo = 0;
    private static double numHum = 0;
    private static HashMap<String, int[]> dictionaryScores = new HashMap<String, int[]>();
    static MystemAnalyzer mystemAnalyzer = MystemAnalyzer.getInstance();

    //   build list of Products for human markup file
    static ArrayList<Product> buildHumanProductList(String filePath, String encoding) throws IOException {
        ArrayList<Product> ProductList = new ArrayList<Product>();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        BufferedReader in = new BufferedReader(isr);

        ArrayList<Review> reviewsList = new ArrayList<Review>();
        ArrayList<Phrase> thesisList = new ArrayList<Phrase>();
        String reviewID = "-1";
        String productID = "-1";
        String s = in.readLine();

        boolean reviewOpen = false;
        StringBuffer sentenceBuff = new StringBuffer();

        while (s != null) {
            s = s.trim();

            if (s.contains("<product id=")) {
                if (!productID.equals("-1")) {
                    Review newReview = new Review(reviewID, thesisList);
                    reviewsList.add(newReview);
                    thesisList = new ArrayList<Phrase>();
                    reviewID = "-1";

                    Product newProduct = new Product(productID, reviewsList);
                    ProductList.add(newProduct);
                    reviewsList = new ArrayList<Review>();
                }
                String s1 = s.substring(0, s.indexOf("name="));
                productID = s.substring(s1.indexOf("\"") + 1, s1.lastIndexOf("\""));
            }

            if (!productID.equals("-1")) {
                if (s.contains("<review")) {
                    reviewOpen = true;
                    if (!reviewID.equals("-1")) {
                        Review newReview = new Review(reviewID, thesisList);
                        reviewsList.add(newReview);
                        thesisList = new ArrayList<Phrase>();
                    }
                    reviewID = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
                }

                if (s.contains("</review>")) {
                    reviewOpen = false;
                    if (sentenceBuff.length() != 0) {
                        addToThesisList(sentenceBuff.toString(), thesisList);
                        sentenceBuff.delete(0, sentenceBuff.length());
                    }
                }

                if (reviewOpen) {
                    if (s.contains("##")) {

                        if (sentenceBuff.length() == 0) {
                            sentenceBuff.append(s.trim());
                        } else {
                            String sentBuff = sentenceBuff.toString();
                            addToThesisList(sentBuff, thesisList);

                            sentenceBuff.delete(0, sentenceBuff.length());
                            sentenceBuff.append(s.trim());
                        }
                    } else {
                        if (sentenceBuff.length() != 0) {
                            sentenceBuff.append(" " + s.trim());
                        }
                    }
                }

                s = in.readLine();
            }
        }

        reviewsList.add(new Review(reviewID, thesisList));
        ProductList.add(new Product(productID, reviewsList));

        return ProductList;
    }

    static void addToThesisList(String s, ArrayList<Phrase> thesisList) {
        String t = s.substring(0, s.indexOf("##")).trim();
        String sentence = s.substring(s.indexOf("##") + 2).trim();

        if (!t.equals("")) {
            if (t.contains(",")) {
                String[] arr = t.split(",");
                for (String anArr : arr) {
                    String s1 = anArr;
                    s1 = s1.trim();
                    if (s1.contains("[") && !s1.contains("|u")) {
                        s1 = splitBracket(s1);
                        if (!s1.equals("")) {
                            boolean flag = false;
                            for (Phrase pr : thesisList) {
                                if (pr.getFeature().equals(s1)) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                thesisList.add(new Phrase(s1.trim().toLowerCase(), sentence));
                            }
                        }
                    }

                }
            } else {
                if (t.contains("|u")) {
                    t = splitBracket(t);
                    t = t.trim();
                    if (!t.equals("") && t.charAt(0) != '{') {
                        thesisList.add(new Phrase(t.trim().toLowerCase(), sentence));
                    }
                }
            }
        }
    }

    static String splitBracket(String s) {
        if (s.contains("[")) {
            s = s.substring(0, s.indexOf("["));
        }
        return s;
    }


    //   build list of Products for algo markup file
    static ArrayList<Product> buildAlgoProductList(String filePath, String encoding) throws IOException, InterruptedException {
        ArrayList<Product> ProductList = new ArrayList<Product>();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        BufferedReader in = new BufferedReader(isr);

        ArrayList<Review> reviewsList = new ArrayList<Review>();
        ArrayList<Phrase> thesisList = new ArrayList<Phrase>();
        String reviewID = "-1";
        String productID = "-1";
        String s = in.readLine();

        while (s != null) {
            s = s.trim();

            if (s.contains("<product id=")) {
                if (!productID.equals("-1")) {
                    Review newReview = new Review(reviewID, thesisList);
                    reviewsList.add(newReview);
                    thesisList = new ArrayList<Phrase>();
                    reviewID = "-1";

                    Product newProduct = new Product(productID, reviewsList);
                    ProductList.add(newProduct);
                    reviewsList = new ArrayList<Review>();
                }
                String s1 = s.substring(0, s.indexOf("name="));
                productID = s.substring(s1.indexOf("\"") + 1, s1.lastIndexOf("\""));

            }

            boolean reviewOpen = false;
            if (!productID.equals("-1")) {
                if (s.contains("<review")) {
                    reviewOpen = true;
                    if (!reviewID.equals("-1")) {
                        Review newReview = new Review(reviewID, thesisList);
                        reviewsList.add(newReview);
                        thesisList = new ArrayList<Phrase>();
                    }
                    reviewID = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
                }


                if (reviewOpen) {
                    StringBuffer sb = new StringBuffer();
                    while (reviewOpen && s != null) {
                        s = in.readLine();
                        if (s.contains("</review>")) {
                            reviewOpen = false;
                            String review = sb.toString();

                            ArrayList<Phrase> tList = ExtractThesis.doExtraction(review);

                            for (Phrase phrase : tList) {
                                String token1 = phrase.getFeature();
                                String token2 = phrase.getOpinion();

                                thesisList.add(new Phrase(token1, token2));

                            }
                        } else {
                            sb.append(" " + s);
                        }

                    }
                }

                s = in.readLine();
            }
        }

        reviewsList.add(new Review(reviewID, thesisList));
        ProductList.add(new Product(productID, reviewsList));


        return ProductList;
    }


    // comparison of thesis for two products lists
    static void compare(ArrayList<Product> algoProThesis, ArrayList<Product> humProThesis, String filePath) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));

        for (Product humProduct : humProThesis) {
            for (Product algoProduct : algoProThesis) {
                if (algoProduct.getId().equals(humProduct.getId())) {
                    comparator(algoProduct, humProduct, out);
                    break;
                }
            }

        }


        out.flush();
    }

    // comparison of thesis for two products
    static void comparator(Product algoProduct, Product humProduct, PrintWriter out) throws UnsupportedEncodingException {
        out.println("<product id=\"" + algoProduct.getId() + "\">");
        if (algoProduct.getReviews().size() > 0 && !algoProduct.getReviews().get(0).getReview().equals("-1")) {
            if (algoProduct.getReviews().size() != humProduct.getReviews().size()) {
                System.out.print("сравнение продуктов с разным числом ревью: ");
                System.out.println("id = " + algoProduct.getId());
            } else {
                compareThesisLists(algoProduct.getReviews(), humProduct.getReviews(), out);
            }

        }

        out.println("</product>");
    }

    // comparison of thesis for two Review lists
    static void compareThesisLists(ArrayList<Review> algoReview, ArrayList<Review> humReview, PrintWriter out) throws UnsupportedEncodingException {

        for (int k = 0; k < algoReview.size(); k++) {
            String reviewID = algoReview.get(k).getReview();

            if (!reviewID.equals("-1")) {
                out.println("   <review id=\"" + reviewID + "\">");

                ArrayList<Phrase> algoThesis = algoReview.get(k).getFeatures();
                ArrayList<Phrase> humThesis = humReview.get(k).getFeatures();

                numAlgo += algoThesis.size();
                numHum += humThesis.size();

                for (Phrase hThesis : humThesis) {
                    String humFeature = hThesis.getFeature();
                    String sentence = hThesis.getOpinion();
                    // System.out.println("   "+hThesis+" "+sentence);
                    for (Phrase aThesis : algoThesis) {
                        String algoFeature = aThesis.getFeature();
                        String opinion = aThesis.getOpinion();
                        // System.out.println(alThesis+" "+opinion);

                        if (contains(humFeature, ReportAnalyzer.normalizer(mystemAnalyzer.report(algoFeature)))) {
                            if (contains(sentence, algoFeature) && contains(sentence, opinion)) {
                                out.println("      <OK>" + humFeature + " " + opinion + "</OK>");
                                // System.out.println(alThesis+" "+opinion+" ## "+sentence);
                                successExtract++;
                                add(dictionaryScores, ReportAnalyzer.normalizer(mystemAnalyzer.report(opinion)), true);
                                break;
                            }
                        }
                    }
                }


                for (Phrase aThesis : algoThesis) {
                    boolean t = false;
                    String algoFeature = aThesis.getFeature();
                    String opinion = aThesis.getOpinion();
                    for (Phrase hThesis : humThesis) {
                        String humFeature = hThesis.getFeature();
                        String sentence = hThesis.getOpinion();
                        if (contains(humFeature, ReportAnalyzer.normalizer(mystemAnalyzer.report(algoFeature)))) {
                            if (contains(sentence, algoFeature) && contains(sentence, opinion)) {
                                t = true;
                                break;
                            }
                        }
                    }
                    if (!t) {
                        out.println("      <algo>" + algoFeature + " " + opinion + "</algo>");
                        add(dictionaryScores, ReportAnalyzer.normalizer(mystemAnalyzer.report(opinion)), false);
                        //System.out.println("      "+algoFeature + " "+opinion);
                    }
                }

                for (Phrase hThesis : humThesis) {
                    boolean t = false;
                    String humFeature = hThesis.getFeature();
                    String sentence = hThesis.getOpinion();

                    for (Phrase aThesis : algoThesis) {
                        String algoFeature = aThesis.getFeature();
                        String opinion = aThesis.getOpinion();

                        if (contains(humFeature, ReportAnalyzer.normalizer(mystemAnalyzer.report(algoFeature)))) {
                            if (contains(sentence, algoFeature) && contains(sentence, opinion)) {
                                t = true;
                                break;
                            }
                        }
                    }
                    if (!t) {
                        out.println("      <hum>" + humFeature + "</hum>");
                    }
                }
                //out.println("   </review>");
            }
        }

    }

    static boolean contains(String sentence, String s) {
        sentence = sentence.toLowerCase();
        if(!s.equals(ReportAnalyzer.UNKNOUN)){
            s = s.toLowerCase();
            return sentence.contains(s);
        }else{
            return false;
        }
    }

    static void add(HashMap<String, int[]> map, String s, boolean t) {
        if (map.containsKey(s)) {
            if (t) {
                map.get(s)[0]++;
            } else {
                map.get(s)[1]++;
            }
        } else {
            if (t) {
                map.put(s, new int[]{1, 0});
            } else {
                map.put(s, new int[]{0, 1});
            }
        }
    }

    static void printDictionary() {
        for (String key : dictionaryScores.keySet()) {
            System.out.println(key + " +" + dictionaryScores.get(key)[0] + " -" + dictionaryScores.get(key)[1] + " " +
                    dictionaryScores.get(key)[1] * 1.0 / (dictionaryScores.get(key)[0] + dictionaryScores.get(key)[1]));
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Product> algoProThesis = buildAlgoProductList("src/main/resources/Notebooks.txt", "utf8");

        /*
        for (Product p:algoProThesis){
            System.out.println("Product_Id = "+p.getId());
            for (Review r:p.getReviews()){
                if(r.getReview()!="-1"){
                    System.out.println("        Review_Id = "+r.getReview());
                    for (Phrase t:r.getFeatures()){
                        System.out.println("            "+t.getFeature()+" "+t.getOpinion());
                    }
                }
            }

        }*/

        ArrayList<Product> humProThesis = buildHumanProductList("src/main/resources/Notebooks_marked_ds.txt", "utf8");

        /*
        for (Product p:humProThesis){
            System.out.println("Product_Id = "+p.getId());
            for (Review r:p.getReviews()){
                if(r.getReview()!="-1"){
                    System.out.println("        Review_Id= "+r.getReview());
                    for (Phrase t:r.getFeatures()){
                        System.out.println("            "+t.getFeature());
                    }
                }
            }
        }*/
        compare(algoProThesis, humProThesis, "result.txt");


        System.out.println("successExtract = " + successExtract);
        System.out.println("numAlgo = " + numAlgo);
        System.out.println("numHum = " + numHum);

        if (numAlgo != 0) {
            System.out.println("precision = " + successExtract / numAlgo);
        }
        if (numHum != 0) {
            System.out.println("recall = " + successExtract / numHum);
        }

        //printDictionary();
        MystemAnalyzer.getInstance().close();
    }
}

