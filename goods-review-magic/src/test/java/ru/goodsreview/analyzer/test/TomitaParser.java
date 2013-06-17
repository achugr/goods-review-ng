package ru.goodsreview.analyzer.test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author : Ilya Makeev
 * date: 17.06.13
 */
public class TomitaParser {


    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> list = new HashMap<String, List<String>>();
        try {

            FileInputStream fstream = new FileInputStream("goods-review-magic/src/test/resources/ru/goodsreview/analyzer/test/data/tomitaInput.txt");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            String lastNum = "";
            String attribute = "";
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine.startsWith("#")) {
                    lastNum = strLine.substring(1, strLine.indexOf(" "));
                }
                if (strLine.startsWith("Attribute")) {
                    attribute = strLine.substring(strLine.indexOf("=") + 1);

                    if (list.containsKey(lastNum)) {
                        List<String> list1 = list.get(lastNum);
                        list1.add(attribute);
                    } else {
                        List<String> list1 = new ArrayList<String>();
                        list1.add(attribute);
                        list.put(lastNum, list1);
                    }

                }

             //   System.out.println(lastNum);
            }

            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return list;
    }

    @Test
    public void tomitaTest() {
        HashMap<String, List<String>> tomitaData = getData();
        for (String key : tomitaData.keySet()) {
            System.out.println();
            List<String> list = tomitaData.get(key);
            for (String s:list){
                System.out.print(s+"; ");
            }
        }
    }
}
