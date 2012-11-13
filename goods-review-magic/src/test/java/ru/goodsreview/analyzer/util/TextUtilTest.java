package ru.goodsreview.analyzer.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         14.11.12
 */
public class TextUtilTest {

    @Test
    public void divideOnSentencesTest() {
        {
            final String text = "купил ноут. зашибись ноут! не жалею \n что купил";
            List<String> sentences = new LinkedList<String>();
            sentences.add("купил ноут.");
            sentences.add("зашибись ноут!");
            sentences.add("не жалею \n что купил");

            List<String> actualSentences = TextUtil.divideOnSentences(text);
            Assert.assertEquals(sentences.get(0), actualSentences.get(0));
            Assert.assertEquals(sentences.get(1), actualSentences.get(1));
            Assert.assertEquals(sentences.get(2), actualSentences.get(2));
        }
    }

    @Test
    public void getSentencesWhichContainsTest() {
        final String text = "классный ноут. невероятно ноут классный! не жалею \n что купил";
        List<String> sentences = new LinkedList<String>();
        sentences.add("классный ноут.");
        sentences.add("невероятно ноут классный!");

        final Pattern pattern = Pattern.compile(".*(" + "ноут" + " " + "классный" + "|" + "классный" + " " + "ноут" + ").*");
        List<String> actualSentences = TextUtil.getSentencesWhichContains(text, pattern);
        Assert.assertEquals(sentences.get(0), actualSentences.get(0));
        Assert.assertEquals(sentences.get(1), actualSentences.get(1));

    }
}
