package ru.goodsreview.analyzer.util;


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
        List<JSONObject> thesises = extractThesises(reviews);
        Map<String, List<JSONObject>> featureMap = groupByFeature(thesises);
        Map<String, Map<String, JSONObject>> info = groupByOpinion(featureMap);

        return new JSONObject();
    }

    private JSONObject buildProductInfo(Map<String, Map<String, JSONObject>> info){
        List<String> features = new ArrayList<String>(info.keySet());
        Collections.sort(features);
        final JSONObject productInfo = new JSONObject();
        for(String featureName : features){
            productInfo.put("")
        }

    }

    private List<JSONObject> extractThesises(List<JSONObject> reviews) {
        final List<JSONObject> thesises = new LinkedList<JSONObject>();
        try {
            for (JSONObject review : reviews) {
                JSONArray thesisArray = review.getJSONArray("thesises");
                for (int i = 0; i < thesisArray.length(); i++) {
                    System.out.println("i am here");
                    JSONObject thesis = thesisArray.getJSONObject(i);
                    final String[] thesisParts = thesis.getString("value").split(" ");
                    final String feature = thesisParts[0];
                    final String opinion = thesisParts[1];
                    thesis.remove("value");
                    thesis.put("feature", feature);
                    thesis.put("opinion", opinion);
                    List<String> sentences = findSentencesForThesis(thesis, review);
                    thesis.put("sentences", sentences);
                    thesises.add(thesis);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return thesises;
    }


    private Map<String, List<JSONObject>> groupByFeature(List<JSONObject> thesises) {
//        final Map<String, List<JSONObject>> featureMap = new HashMap<String, List<JSONObject>>();
        final Map<String, List<JSONObject>> featureMap = new HashMap<String, List<JSONObject>>();
        for (JSONObject thesis : thesises) {
            try {
                final String feature = thesis.getString("feature");
                thesis.remove("feature");
                if (featureMap.containsKey(feature)) {
                    featureMap.get(feature).add(thesis);
                } else {
                    final List<JSONObject> tmp = new LinkedList<JSONObject>();
                    tmp.add(thesis);
                    featureMap.put(feature, tmp);
                }
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return featureMap;
    }

    private Map<String, Map<String, JSONObject>> groupByOpinion(final Map<String, List<JSONObject>> featureMap) {
        final Map<String, Map<String, JSONObject>> classifiedThesises = new HashMap<String, Map<String, JSONObject>>();
        try {
            for (Map.Entry<String, List<JSONObject>> entry : featureMap.entrySet()) {
                @SuppressWarnings("unchecked")
                final Map<String, JSONObject> opinionMap = new HashMap<String, JSONObject>();
                for (JSONObject thesis : entry.getValue()) {
                    final String opinion = thesis.getString("opinion");
                    if (opinionMap.containsKey(opinion)) {
                        JSONObject info = opinionMap.get(opinion);
                        final JSONArray sentences = thesis.getJSONArray("sentences");
                        for (int i = 0; i < sentences.length(); i++) {
                            info.getJSONArray("sentences").put(sentences.get(i));
                        }
                        opinionMap.put(opinion, info);
                    } else {
                        final JSONObject info = new JSONObject();
                        info.put("sentiment", thesis.getString("sentiment"));
                        info.put("importance", thesis.getString("importance"));
                        info.put("sentences", thesis.getJSONArray("sentences"));
                        opinionMap.put(opinion, info);
                    }
                }
                classifiedThesises.put(entry.getKey(), opinionMap);
            }
        } catch (JSONException e) {
            log.error("Something wrong with json", e);
            throw new RuntimeException(e);
        }
        return classifiedThesises;
    }

    private List<String> findSentencesForThesis(JSONObject thesis, JSONObject review) {
        final List<String> sentences = new LinkedList<String>();
        try {
            final String feature = thesis.getString("feature");
            final String opinion = thesis.getString("opinion");
            final String regexp = ".*(" + feature + " " + opinion + "|" + opinion + " " + feature + ").*";
            final Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
            System.out.println(pattern);
            if (review.has("text")) {
                final String text = review.getString("text");
                sentences.addAll(TextUtil.getSentencesWhichContains(text, pattern));
            }
            if (review.has("contra")) {
                final String contra = review.getString("contra");
                sentences.addAll(TextUtil.getSentencesWhichContains(contra, pattern));
            }
            if (review.has("pro")) {
                final String pro = review.getString("pro");
                sentences.addAll(TextUtil.getSentencesWhichContains(pro, pattern));
            }

        } catch (JSONException e) {
            log.error("Something wrong with json", e);
            throw new RuntimeException(e);
        }
        return sentences;
    }

    public static void main(String[] args) {
        final List<JSONObject> reviews = new LinkedList<JSONObject>();
        try {
            reviews.add(new JSONObject("{\"id\":24826722,\"modelId\":7284127,\"reject\":0,\"contra\":\"звук великолепный просто!\",\"thesises\":[{\"sentiment\":2.75,\"importance\":0,\"value\":\"звук великолепный\"}, {\"sentiment\":2.75,\"importance\":0,\"value\":\"ноутбук отличный\"},{\"sentiment\":2,\"importance\":0,\"value\":\"звук хороший\"}],\"pro\":\"Отличный ноутбук! Не греется,хороший звук,видео,все игры тянет.wi-fi работает отлично.Советую всем приобрести этот ноутбук,не пожалеете.\",\"grade\":2,\"date\":1334330677000,\"typeId\":2,\"agree\":3}\n"));
            reviews.add(new JSONObject("{\"id\":11646215,\"modelId\":6386529,\"text\":\"Удобный, компактный, беззвучный, мощный, ну и стоит умеренно \\r\\n:)\",\"reject\":18,\"contra\":\"слабая видюха и всё. я вам скажу, ноутбук великолепный!\",\"thesises\":[{\"sentiment\":2,\"importance\":0,\"value\":\"дисплей хороший\"}, {\"sentiment\":2.75,\"importance\":0,\"value\":\"ноутбук отличный\"}, {\"sentiment\":2,\"importance\":0,\"value\":\"ноутбук великолепный\"}],\"pro\":\"Хороший дисплей, очень мощный симс 3. ну просто Ноутбук отличный! идёт хорошо на минимальных настройках без лагов\\r\\n\",\"grade\":2,\"date\":1290934577000,\"typeId\":2,\"agree\":24}\n"));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ProductInfoPreparatory preparatory = new ProductInfoPreparatory();

        List<JSONObject> thesises = preparatory.extractThesises(reviews);
        Map<String, List<JSONObject>> featureMap = preparatory.groupByFeature(thesises);
        for (Map.Entry<String, List<JSONObject>> entry : featureMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        Map<String, Map<String, JSONObject>> info = preparatory.groupByOpinion(featureMap);

        System.out.println("result");
        for (Map.Entry<String, Map<String, JSONObject>> entry : info.entrySet()) {
            System.out.println(entry.getKey());
            for (Map.Entry<String, JSONObject> entry1 : entry.getValue().entrySet()) {
                System.out.println(entry1.getKey() + " " + entry1.getValue());
            }
        }
    }


}
