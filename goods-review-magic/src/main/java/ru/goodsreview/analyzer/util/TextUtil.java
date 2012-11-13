package ru.goodsreview.analyzer.util;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         13.11.12
 */
public final class TextUtil {
    private static final Logger log = Logger.getLogger(TextUtil.class);

    public static final Pattern sentencePattern = Pattern.compile(
            "# Match a sentence ending in punctuation or EOS.\n" +
                    "[^.!?\\s]    # First char is non-punct, non-ws\n" +
                    "[^.!?]*      # Greedily consume up to punctuation.\n" +
                    "(?:          # Group for unrolling the loop.\n" +
                    "  [.!?]      # (special) inner punctuation ok if\n" +
                    "  (?!['\"]?\\s|$)  # not followed by ws or EOS.\n" +
                    "  [^.!?]*    # Greedily consume up to punctuation.\n" +
                    ")*           # Zero or more (special normal*)\n" +
                    "[.!?]?       # Optional ending punctuation.\n" +
                    "['\"]?       # Optional closing quote.\n" +
                    "(?=\\s|$)",
            Pattern.MULTILINE | Pattern.COMMENTS);

    private TextUtil(){}

    public static List<String> divideOnSentences(final String text){
        final List<String> sentences = new LinkedList<String>();
        Matcher sentenceMatcher = sentencePattern.matcher(text);
        while (sentenceMatcher.find()) {
            sentences.add(sentenceMatcher.group());
//            log.debug("sentence: " + sentenceMatcher.group());
        }
        return sentences;
    }

    public static List<String> getSentencesWhichContains(final String text, final Pattern pattern){
        List<String> sentences = divideOnSentences(text);
        log.debug("text sentences are: " + sentences);
        List<String> neededSentences = new LinkedList<String>();
        for(String sentence : sentences){
            Matcher matcher = pattern.matcher(sentence.toLowerCase());
            log.debug("sentence: " + sentence);
            if(matcher.matches()){
                log.debug("matches! " + matcher.group());
                neededSentences.add(matcher.group());
            }
        }
        return neededSentences;

    }


}
