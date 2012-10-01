package ru.goodsreview.analyzer;

import org.json.JSONObject;
import ru.goodsreview.analyzer.util.Phrase;
import ru.goodsreview.core.model.Thesis;
import ru.goodsreview.core.model.impl.json.ReviewOverJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 01.10.12
 * Ilya Makeev
 */
public class AnalyzingTask extends AnalyzingSchedulerTask {
    @Override
    protected JSONObject process(JSONObject object)  {
        String review = object.toString();
        ArrayList<Phrase> phrases = ExtractThesis.doExtraction(review);
        List<Thesis> thesisList = new ArrayList<Thesis>();
        for (Phrase phrase : phrases) {
            Thesis thesis = new EvaluativeThesis(phrase.toString());
            thesisList.add(thesis);
        }
        ReviewOverJson reviewOverJson = new ReviewOverJson(object);
        reviewOverJson.addThesises(thesisList);
        JSONObject newJsonObject = reviewOverJson.getJsonObject();
        return newJsonObject;
    }
}
