package ru.goods.review.indexer.impl

import ru.goodsreview.scheduler.context.Context
import ru.goods.review.indexer.Indexer
import ru.goodsreview.core.db.entity.{Visitor, EntityType, EntityService}
import org.apache.lucene.index.{IndexWriter, IndexReader}
import reflect.BeanProperty
import org.json.JSONObject
import ru.goodsreview.core.model.impl.json.CategoryOverJson

/**
 * @author daddy-bear
 */
class CategoryIndexer extends Indexer {

  @BeanProperty
  var entityService: EntityService = _

  @BeanProperty
  var indexReader: IndexReader = _

  @BeanProperty
  var indexWriter: IndexWriter = _

  override def index(context: Context) {
    entityService.visitEntities(EntityType.CATEGORY.getTypeId, new Visitor[JSONObject] {
      override def visit(jsonObject: JSONObject) {
        val document = categoryAsDocument(new CategoryOverJson(jsonObject))
      }
    })


  }

}
