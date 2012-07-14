package ru.goodsreview.analyzer;

import org.json.JSONObject;

import java.util.Collection;

/**
 * @author daddy-bear
 *         Date: 22.06.12
 */

public interface EntityUpdater {
    
    void update(final Collection<JSONObject> entities);
    
}
