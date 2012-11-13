package ru.goodsreview.analyzer.util;


import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         13.11.12
 */
public class ProductInfoPreparatory {

    private static final Logger log = Logger.getLogger(ProductInfoPreparatory.class);

    public JSONObject prepareInfo(final List<JSONObject> reviews) {
        extractThesises(reviews);
        return new JSONObject();
    }

    private List<JSONObject> extractThesises(List<JSONObject> reviews) {
        final List<JSONObject> thesises = new LinkedList<JSONObject>();
        for (JSONObject review : reviews) {
            try {
                log.debug("review is: " + review.toString());
                JSONArray thesisArray = review.getJSONArray("thesises");
                for (int i = 0; i < thesisArray.length(); i++) {
                    JSONObject thesis = thesisArray.getJSONObject(i);
                    final String[] thesisParts = thesis.getString("value").split(" ");
                    final String feature = thesisParts[0];
                    final String opinion = thesisParts[1];
                    thesis.remove("value");
                    thesis.put("feature", feature);
                    thesis.put("opinion", opinion);
                    thesis.put("sentences", findSentencesForThesis(thesis, review));
                    thesises.add(thesis);
                    log.debug("thesis is: " + thesis);
//                    thesis.put("sentences", getSentenceForThesis(thesis, review))
//                    thesises.add(thesisArray.getJSONObject(i));
//                    log.debug("thesis array: " + thesisArray.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return thesises;
    }


    private MultiMap groupByFeature(List<JSONObject> thesises) {
//        final Map<String, List<JSONObject>> featureMap = new HashMap<String, List<JSONObject>>();
        final MultiMap featureMap = new MultiValueMap();
        for (JSONObject thesis : thesises) {
            try {

                featureMap.put(thesis.getString("feature"), thesis);
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        return featureMap;
    }

    private Map<String, Map<String, JSONObject>> groupByOpinion(MultiMap featureMap) {

        return new HashMap<String, Map<String, JSONObject>>();
    }

    private List<String> findSentencesForThesis(JSONObject thesis, JSONObject review) {
        final List<String> sentences = new LinkedList<String>();
        try {

            final String text = review.getString("text");
            final String contra = review.getString("contra");
            final String pro = review.getString("pro");

            final String feature = thesis.getString("feature");
            final String opinion = thesis.getString("opinion");
            final String regexp = ".*(" + feature + " " + opinion + "|" + opinion + " " + feature + ").*";
            final Pattern pattern = Pattern.compile(regexp);
            sentences.addAll(TextUtil.getSentencesWhichContains(text, pattern));
            sentences.addAll(TextUtil.getSentencesWhichContains(contra, pattern));
            sentences.addAll(TextUtil.getSentencesWhichContains(pro, pattern));

        } catch (JSONException e) {
//            TODO
        }
        return sentences;
    }

    public static void main(String[] args) {
        final List<JSONObject> reviews = new LinkedList<JSONObject>();
        try {
            reviews.add(new JSONObject("{\"id\":24826722,\"modelId\":7284127,\"reject\":0,\"contra\":\"Их в этом ноутбуке просто нету!глянцевый экран это не помеха(просто не нужно щеткой по металлу протирать)))Аккумулятора примерно хватает часа на два,вполне нормально,зарядить в наше время не проблема. звук великолепный просто!\",\"thesises\":[{\"sentiment\":2.75,\"importance\":0,\"value\":\"звук великолепный\"}, {\"sentiment\":2.75,\"importance\":0,\"value\":\"ноутбук отличный\"},{\"sentiment\":2,\"importance\":0,\"value\":\"звук хороший\"}],\"pro\":\"Отличный ноутбук!Пользуюсь почти год,и всё никак не нарадуюсь покупке.Не греется,хороший звук,видео,все игры тянет.wi-fi работает отлично.Быстро передаёт файлы по Bluetooth.1000gb памяти просто очень много.Заполнил 800gb,переживал что тормозить будет,зависать,а ничего,быстро открывает файлы,игры,видео.Советую всем приобрести этот ноутбук,не пожалеете.\",\"grade\":2,\"date\":1334330677000,\"typeId\":2,\"agree\":3}\n"));
            reviews.add(new JSONObject("{\"id\":11646215,\"modelId\":6386529,\"text\":\"Удобный, компактный, беззвучный, мощный, ну и стоит умеренно \\r\\n:)\",\"reject\":18,\"contra\":\"слабая видюха и всё. я вам скажу, ноутбук великолепный!\",\"thesises\":[{\"sentiment\":2,\"importance\":0,\"value\":\"дисплей хороший\"}, {\"sentiment\":2,\"importance\":0,\"value\":\"ноутбук великолепный\"}],\"pro\":\"Хороший дисплей, очень мощный симс 3  идёт хорошо на минимальных настройках без лагов\\r\\nОчень обьёмный аккомулятор, стоит помимо 7рки ещё асусовская ось которая грузится за пару сек.\",\"grade\":2,\"date\":1290934577000,\"typeId\":2,\"agree\":24}\n"));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ProductInfoPreparatory preparatory = new ProductInfoPreparatory();

        List<JSONObject> thesises = preparatory.extractThesises(reviews);
        MultiMap featureMap = preparatory.groupByFeature(thesises);
        for (Object entry : featureMap.entrySet()) {
            System.out.println(entry);
        }
    }


}
