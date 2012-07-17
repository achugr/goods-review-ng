  /**
 * Date: 18.07.12
 * Time: 03:20
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import analyzer.wordAnalyzer.ReportAnalyzer;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Date: 08.07.12
 * Time: 01:17
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class TestClass extends TestCase {

    public TestClass(String testName) {
        super(testName);
    }


    public void testReportList() throws UnsupportedEncodingException {
        String s = "сборки{сборка=S,жен,неод=(им,мн|род,ед|вин,мн)}";
        ArrayList<String> reportList = ReportAnalyzer.buildReportList(s);
        assertTrue(reportList.size()==3);
    }

    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        TestSuite suite = new TestSuite();

        suite.addTest(new TestClass("testReportList"));
        runner.doRun(suite);
    }
}