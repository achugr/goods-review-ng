package ru.goods.review.indexer.impl

import ru.goodsreview.scheduler.context.Context
import ru.goods.review.indexer.Indexer
import ru.goodsreview.core.db.entity.{Visitor, EntityType, EntityService}
import org.apache.lucene.index.{IndexWriterConfig, IndexWriter}
import org.apache.lucene.analysis.ru.RussianAnalyzer
import reflect.BeanProperty
import org.json.JSONObject
import ru.goodsreview.core.model.impl.json.CategoryOverJson
import org.apache.lucene.store.SimpleFSDirectory
import org.apache.lucene.util.Version
import ru.goods.review.indexer.document.Helper.asDocument

/**
 * @author daddy-bear
 */
class CategoryIndexer extends Indexer {

  @BeanProperty
  var entityService: EntityService = _

  @BeanProperty
  var categoryIndexPath: String = _

  override def index(context: Context) {

    val tmpDirectory = new SimpleFSDirectory(categoryIndexPath + PrefixHolder.tmpIndex)

    val categoryIndexWriter = new IndexWriter(
      tmpDirectory,
      new IndexWriterConfig(Version.LUCENE_36, new RussianAnalyzer(Version.LUCENE_36))
    )
    categoryIndexWriter.deleteAll()

    entityService.visitEntities(EntityType.CATEGORY.getTypeId, new Visitor[JSONObject] {
      override def visit(jsonObject: JSONObject) {
        categoryIndexWriter.addDocument(asDocument(new CategoryOverJson(jsonObject)))
      }
    })

    categoryIndexWriter.commit()
    categoryIndexWriter.close()

    val indexDirectory = new SimpleFSDirectory(categoryIndexPath)
    List(tmpDirectory.listAll()) map (_ => tmpDirectory.copy(indexDirectory, _, _))
  }

}

private object PrefixHolder {

  def tmpIndex = "T_M_P"

}
