package ru.goods.review.indexer

import ru.goodsreview.scheduler.{TaskResult, SchedulerTask}
import scala.reflect.BeanProperty
import ru.goodsreview.indexer.Indexer
import ru.goodsreview.scheduler.context.Context

/**
 * @author daddy-bear
 */
class IndexerTask extends SchedulerTask {

  @BeanProperty
  var indexer: Indexer = _

  def run(context: Context) = {

    indexer.index(context)

    TaskResult.ok()
  }
}
