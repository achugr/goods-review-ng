package ru.goodsreview.analyzer;
/**
 * Date: 18.07.12
 * Time: 03:20
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.goodsreview.analyzer.util.Phrase;
import ru.goodsreview.analyzer.util.sentence.ReviewTokens;
import ru.goodsreview.analyzer.word.analyzer.MystemAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import ru.goodsreview.analyzer.word.analyzer.WordAnalyzer;
import ru.goodsreview.core.util.FileReader;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:run-analyzer-bean.xml")
public class TestClass {

    @Test
    public void testWordAnalyzer() throws UnsupportedEncodingException {
        String word = "дериватив";
        WordAnalyzer analyzer = ReviewTokens.getWordAnalyzer();
        System.out.println(analyzer.report(word));
        analyzer.close();
    }


    public void testReportList() throws UnsupportedEncodingException {
        String s = "мурелки{мурелка?=S,жен,од=им,мн|?=S,жен,од=род,ед|мурелка?=S,жен,неод=им,мн|?=S,жен,неод=род,ед|?=S,жен,неод=вин,мн|мурелки?=S,мн,неод=им|?=S,мн,неод=вин|мурелок?=S,муж,од=им,мн}";
        List<String> reportList = MystemReportAnalyzer.buildReportList(s);
        for (String str : reportList) {
            System.out.println(str);
        }

    }

    public void testFeatureDictionary() throws UnsupportedEncodingException {
        ReviewTokens.getFeatureDictionary().print();
    }

    public void testOpinionDictionary() throws UnsupportedEncodingException {
        ReviewTokens.getMapDictionary().print();

    }

    public static void main(String[] args) {
//        Scanner scanner = null;
//        try {
//            scanner = new Scanner(new File("reviews.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        while (scanner.hasNext()){
//            String review = scanner.nextLine();
//            try {
//                List<Phrase> phrases = ExtractThesis.doExtraction(review);
//                for(Phrase phrase : phrases){
//                    System.out.println(phrase.toString());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            } catch (InterruptedException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
    }
//        TestRunner runner = new TestRunner();
//        TestSuite suite = new TestSuite();
////        suite.addTest(new TestClass("testReportList"));
//     suite.addTest(new TestClass("testWordAnalyzer"));
//      //  suite.addTest(new TestClass("testFeatureDictionary"));
//      //  suite.addTest(new TestClass("testOpinionDictionary"));
//        runner.doRun(suite);
}
