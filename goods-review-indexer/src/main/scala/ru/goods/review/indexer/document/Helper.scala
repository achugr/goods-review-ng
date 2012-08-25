package ru.goods.review.indexer.document

import ru.goodsreview.core.model.Category
import org.apache.lucene.document.{NumericField, Field, Document}

/**
 * @author daddy-bear
 */
object Helper {

  def asDocument(category: Category) = {
    val document = new Document

    document.add(new Field("name", category.getName, Field.Store.NO, Field.Index.ANALYZED))
    document.add(new NumericField("parent-id", Field.Store.NO, true).setLongValue(category.getParentId))
    document.add(new NumericField("id", Field.Store.NO, true).setLongValue(category.getId))
    document.add(new Field(JSON_BODY_FILED_NAME, fromCategory(category).toString(), Field.Store.YES, Field.Index.NOT_ANALYZED))

    document
  }

  private val JSON_BODY_FILED_NAME = "json-body"
}
