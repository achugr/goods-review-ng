package ru.goodsreview.analyzer.test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.goodsreview.analyzer.util.Phrase;

/**
 * author : Ilya Makeev
 * date: 17.06.13
 */
public class TomitaParser {


    public static HashMap<String, List<Phrase>> getData() {
        HashMap<String, List<Phrase>> list = new HashMap<String, List<Phrase>>();
        try {

            FileInputStream fstream = new FileInputStream("goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/tomitaOutput.txt");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            String lastNum = "";
            String lastAttribute = "";
            String lastParticle = "";
            String opinion = "";
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine.startsWith("#")) {
                    lastNum = strLine.substring(1, strLine.indexOf(" ")).trim();
                }
                if (strLine.startsWith("Attribute")) {
                    lastAttribute = strLine.substring(strLine.indexOf("=") + 1).trim();
                }
                if (strLine.startsWith("Particle")) {
                    lastParticle = strLine.substring(strLine.indexOf("=") + 1).trim();
                }
                if (strLine.startsWith("Opinion")) {
                    opinion = strLine.substring(strLine.indexOf("=") + 1).trim();
                    if (lastParticle.length() != 0) {
                        opinion = lastParticle + " " + opinion;
                    }

                    if (list.containsKey(lastNum)) {
                        List<Phrase> list1 = list.get(lastNum);
                        list1.add(new Phrase(lastAttribute, opinion, "", "", 0.0));
                    } else {
                        List<Phrase> list1 = new ArrayList<Phrase>();
                        list1.add(new Phrase(lastAttribute, opinion, "", "", 0.0));
                        list.put(lastNum, list1);
                    }
                    lastAttribute = "";
                    lastParticle = "";

                }


            }

            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return list;
    }

    @Test
    public void tomitaTest() {
        HashMap<String, List<Phrase>> tomitaData = getData();
        for (String key : tomitaData.keySet()) {
            List<Phrase> list = tomitaData.get(key);
            for (Phrase phrase : list) {
                System.out.print(phrase.toString() + "; ");
            }
            System.out.println();
        }
    }
}
