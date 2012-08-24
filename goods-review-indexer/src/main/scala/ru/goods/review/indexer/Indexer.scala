package ru.goods.review.indexer

import ru.goodsreview.scheduler.context.Context

/**
 * @author daddy-bear
 */
abstract class Indexer {

  abstract def index(context: Context)

}
