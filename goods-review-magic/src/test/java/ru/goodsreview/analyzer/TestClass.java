package ru.goodsreview.analyzer;
/**
 * Date: 18.07.12
 * Time: 03:20
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import org.json.JSONObject;
import org.junit.Test;
import ru.goodsreview.analyzer.util.sentence.ReviewTokens;
import ru.goodsreview.analyzer.word.analyzer.MystemReportAnalyzer;
import ru.goodsreview.analyzer.word.analyzer.WordAnalyzer;
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



}
