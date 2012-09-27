package ru.goods.review.indexer

import ru.goodsreview.scheduler.context.Context

/**
 * @author daddy-bear
 */
trait Indexer {

  def index(context: Context)

}
