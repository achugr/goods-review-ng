package ru.goodsreview.analyzer;
/**
* Date: 18.07.12
* Time: 03:20
* Author:
* Ilya Makeev
* ilya.makeev@gmail.com
*/
import ru.goodsreview.analyzer.util.sentence.ReviewTokens;
import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestClass extends TestCase {

    public TestClass(String testName) {
        super(testName);
    }

    public void testMystemAnalyzer() throws UnsupportedEncodingException {
//        String s = "телефоном{телефон=S,муж,неод=твор,ед}";
//        String report = ReviewTokens.getWordAnalyzer().report("телефоном");

        System.out.println(ReviewTokens.getWordAnalyzer().report("конь"));
       // assertTrue(report.equals(s));
    }
//
    public void testReportList() throws UnsupportedEncodingException {
        String s = "сборки{сборка=S,жен,неод=(им,мн|род,ед|вин,мн)}";
        List<String> reportList = MystemReportAnalyzer.buildReportList(s);
        for (String str:reportList){
            System.out.println(str);
        }
        assertTrue(reportList.size()==3);
    }

    public void testFeatureDictionary() throws UnsupportedEncodingException {
         ReviewTokens.getFeatureDictionary().print();
    }

    public void testOpinionDictionary() throws UnsupportedEncodingException {
        ReviewTokens.getMapDictionary().print();

    }

    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        TestSuite suite = new TestSuite();
        suite.addTest(new TestClass("testReportList"));
     // suite.addTest(new TestClass("testMystemAnalyzer"));
      //  suite.addTest(new TestClass("testFeatureDictionary"));
      //  suite.addTest(new TestClass("testOpinionDictionary"));
        runner.doRun(suite);
    }
}