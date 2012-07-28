package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.goodsreview.api.provider.ContentAPIProvider;

import java.util.List;
/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */
public class ModelGrabber {
    private final static Logger log = Logger.getLogger(ModelGrabber.class);
    private final ContentAPIProvider contentApiProvider;

    public ModelGrabber(ContentAPIProvider contentApiProvider) {
        this.contentApiProvider = contentApiProvider;
    }


    public List<JSONObject> getModels(){
        return null;
    }
}
