package ru.goodsreview.analyzer;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ru.goodsreview.analyzer.util.Phrase;
import ru.goodsreview.core.db.entity.EntityService;
import ru.goodsreview.core.db.visitor.Visitor;
import ru.goodsreview.core.model.Thesis;
import ru.goodsreview.core.model.impl.json.ReviewOverJson;
import ru.goodsreview.core.model.impl.json.ReviewOverJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 01.10.12
 * Ilya Makeev
 */

public class AnalyzingTask extends AnalyzingSchedulerTask {
    private final static Logger log = Logger.getLogger(AnalyzingTask.class);

    @Override
    protected JSONObject process(JSONObject object) {
        log.info("Review processing started..");
        String[] atrs = {"text", "pro", "contra"};

        try {
            List<Thesis> thesisList = new ArrayList<Thesis>();

            for (String atr : atrs) {
                if (object.has(atr)) {
                    String review = object.get(atr).toString();
                    ArrayList<Phrase> phrases = ExtractThesis.doExtraction(review);
                    for (Thesis thesis : phrases) {
                        thesisList.add(thesis);
                        //    log.info("Extracted thesis: " + thesis.getValue());
                    }
                }
            }


            ReviewOverJson.updateObject(object, thesisList);

            // log.info("thesises: " + object.get("thesises").toString());

            return object;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

}
