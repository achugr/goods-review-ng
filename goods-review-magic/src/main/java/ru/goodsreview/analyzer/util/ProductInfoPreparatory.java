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
//TODO code in this class is horrible
//    TODO structure programming style
public final class ProductInfoPreparatory {

    private static final Logger log = Logger.getLogger(ProductInfoPreparatory.class);

    public static JSONObject prepareInfo(final long modelId, final List<JSONObject> reviews) {
        List<JSONObject> thesises = extractThesises(reviews);
        Map<String, List<JSONObject>> featureMap = groupByFeature(thesises);
        Map<String, Map<String, JSONObject>> info = groupByOpinion(featureMap);
        final JSONObject productInfo = buildProductInfo(info);
        try {
            productInfo.put("modelId", modelId);
        } catch (JSONException e) {
            log.error("Some problem with json", e);
            throw new RuntimeException(e);
        }
        return productInfo;
    }

    private static JSONObject buildProductInfo(Map<String, Map<String, JSONObject>> info) {
        List<String> featureNames = new ArrayList<String>(info.keySet());
        Collections.sort(featureNames);
        final JSONObject productInfo = new JSONObject();
        JSONArray features = new JSONArray();
        try {
            for (String featureName : featureNames) {
                JSONObject feature = new JSONObject();
                feature.put("feature", featureName);
                JSONArray opinions = new JSONArray();
                List<String> opinionNames = new ArrayList<String>(info.get(featureName).keySet());
                Collections.sort(opinionNames);
                for (String opinionName : opinionNames) {
                    JSONObject opinion = new JSONObject();
                    opinion.put("opinion", opinionName);
                    opinion.put("sentiment", info.get(featureName).get(opinionName).getInt("sentiment"));
                    opinion.put("importance", info.get(featureName).get(opinionName).getDouble("importance"));
                    opinion.put("sentences", info.get(featureName).get(opinionName).getJSONArray("sentences"));
                    opinions.put(opinion);
                }
                feature.put("opinions", opinions);
                features.put(feature);
            }
            productInfo.put("features", features);
        } catch (JSONException e) {
            log.error("Some problem with json", e);
            throw new RuntimeException(e);
        }
        return productInfo;
    }

    private static List<JSONObject> extractThesises(List<JSONObject> reviews) {
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


    private static Map<String, List<JSONObject>> groupByFeature(List<JSONObject> thesises) {
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

    private static Map<String, Map<String, JSONObject>> groupByOpinion(final Map<String, List<JSONObject>> featureMap) {
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

    private static List<String> findSentencesForThesis(JSONObject thesis, JSONObject review) {
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

}
