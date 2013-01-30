package ru.goodsreview.analyzer.research;

import ru.goodsreview.analyzer.util.yaxml.YaXmlClient;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         27.01.13
 */
// code is shit
public class YaXmlStatisticResearch {

    private static void updateStatistic() throws IOException {
        final List<String> okThesis = new ArrayList<String>();
        final List<String> algoThesis = new ArrayList<String>();
        final Pattern okPattern = Pattern.compile("<OK>(.*)</OK>");
        final Pattern algoPattern = Pattern.compile("<algo>(.*)</algo>");

        Scanner scanner = new Scanner(new File("goods-review-magic/src/test/resources/result-for-research.xml"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher okMatcher = okPattern.matcher(line);
            if (okMatcher.find()) {
                okThesis.add(okMatcher.group(1));
//                System.out.println("ok thesis: " + okMatcher.group(1));
            }
            Matcher algoMatcher = algoPattern.matcher(line);
            if (algoMatcher.find()) {
                algoThesis.add(algoMatcher.group(1));
//                System.out.println("algo thesis: " + algoMatcher.group(1));
            }
        }


        final Map<String, Double> algoMap = new HashMap<String, Double>();
        final Map<String, Double> okMap = new HashMap<String, Double>();

        Pattern foundDocs = Pattern.compile("<found-docs priority=\"all\">(\\d*)</found-docs>");
        System.out.println("algo thesises: ");
        for (int i = 0; i < algoThesis.size(); i++) {
            try {
                String thesis = algoThesis.get(i);
                YaXmlClient yaXmlClient = new YaXmlClient();
                double freq = Double.parseDouble(yaXmlClient.extractData("\"" + thesis + "\"", foundDocs));
                double freqOnMarket = Double.parseDouble(yaXmlClient.extractData("\"" + thesis + "\"" + " host:market.yandex.ru", foundDocs));
                algoMap.put(thesis, (freqOnMarket / freq));
                System.out.println(thesis + " ->> " + algoMap.get(thesis));
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
//        System.out.println("ok thesises: ");
//        for (int i = 0; i < okThesis.size(); i++) {
//            try {
//                String thesis = okThesis.get(i);
//                YaXmlClient yaXmlClient = new YaXmlClient();
//                double freq = Double.parseDouble(yaXmlClient.extractData("\"" + thesis + "\"", foundDocs));
//                double freqOnMarket = Double.parseDouble(yaXmlClient.extractData("\"" + thesis + "\"" + " host:market.yandex.ru", foundDocs));
//                okMap.put(thesis, (freqOnMarket / freq));
//                System.out.println(thesis + " ->> " + okMap.get(thesis));
//            } catch (NumberFormatException e) {
//                YaXmlClient yaXmlClient = new YaXmlClient();
//                System.out.println(yaXmlClient.perform("\"" + okThesis.get(i) + "\""));
//                System.out.println(e);
//            }
//        }

        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("algo-thesis"));
            out.writeObject(algoMap);
        }
//        {
//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ok-thesis"));
//            out.writeObject(okMap);
//        }
    }

    private static double average(final Map<String, Double> statisticsMap) {
        double avg = 0;
        for (Map.Entry<String, Double> entry : statisticsMap.entrySet()) {
            avg += entry.getValue();
        }
        return avg / statisticsMap.keySet().size();
    }

    private static double deviation(final Map<String, Double> statisticsMap, double avg) {
        double deviation = 0;
        for (Map.Entry<String, Double> entry : statisticsMap.entrySet()) {
            deviation += Math.pow((entry.getValue() - avg), 2);
        }
        deviation /= statisticsMap.keySet().size();
        return Math.sqrt(deviation);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        updateStatistic();

        Map<String, Double> algoMap = new HashMap<String, Double>();
        Map<String, Double> okMap = new HashMap<String, Double>();
        {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("algo-thesis")));
            algoMap = ((HashMap<String, Double>) is.readObject());
        }
        {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("ok-thesis")));
            okMap = ((HashMap<String, Double>) is.readObject());
        }

//        System.out.println("algo map: ");
//        for (Map.Entry<String, Double> entry : algoMap.entrySet()) {
//            System.out.println(entry.getKey() + " -> " + entry.getValue());
//        }
//        System.out.println("ok map:");
//        for (Map.Entry<String, Double> entry : okMap.entrySet()) {
//            System.out.println(entry.getKey() + " -> " + entry.getValue());
//        }
        System.out.println("average of algo: " + average(algoMap));
        System.out.println("deviation of algo: " + deviation(algoMap, average(algoMap)));

        System.out.println("average of ok: " + average(okMap));
        System.out.println("deviation of ok: " + deviation(okMap, average(okMap)));
    }
}
