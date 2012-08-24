package ru.goods.review.indexer.impl

import ru.goodsreview.scheduler.context.Context
import ru.goods.review.indexer.Indexer
import ru.goodsreview.core.db.entity.{Visitor, EntityType, EntityService}
import org.apache.lucene.index.{IndexWriterConfig, IndexWriter}
import org.apache.lucene.analysis.ru.RussianAnalyzer
import reflect.BeanProperty
import org.json.JSONObject
import ru.goodsreview.core.model.impl.json.CategoryOverJson
import ru.goodsreview.indexer.helpers.DocumentHelper.categoryAsDocument
import org.apache.lucene.store.SimpleFSDirectory
import org.apache.lucene.util.Version

/**
 * @author daddy-bear
 */
class CategoryIndexer extends Indexer {

  @BeanProperty
  var entityService: EntityService = _

  @BeanProperty
  var categoryIndexPath: String = _

  override def index(context: Context) {

    val categoryIndexWriter = new IndexWriter(
      new SimpleFSDirectory(categoryIndexPath),
      new IndexWriterConfig(Version.LUCENE_36, new RussianAnalyzer(Version.LUCENE_36))
    )
    categoryIndexWriter.

    entityService.visitEntities(EntityType.CATEGORY.getTypeId, new Visitor[JSONObject] {
      override def visit(jsonObject: JSONObject) {
        val document = categoryAsDocument(new CategoryOverJson(jsonObject))
      }
    })


  }

}
