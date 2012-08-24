package ru.goodsreview.indexer.helpers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import ru.goodsreview.core.model.Category;

import static ru.goodsreview.core.model.impl.json.TransformerUtil.fromCategory;

/**
 * User: daddy-bear
 * Date: 10.08.12
 * Time: 15:04
 */
public final class DocumentHelper {
    private final static String JSON_BODY_FILED_NAME = "json-body";

    private DocumentHelper() {
    }

    public static Document categoryAsDocument(final Category category) {
        final Document document = new Document();

        document.add(new Field("name", category.getName(), Field.Store.NO, Field.Index.ANALYZED));
        document.add(new NumericField("parent-id", Field.Store.NO, true).setLongValue(category.getParentId()));
        document.add(new NumericField("id", Field.Store.NO, true).setLongValue(category.getId()));
        document.add(new Field(JSON_BODY_FILED_NAME, fromCategory(category).toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));

        return document;
    }
}
