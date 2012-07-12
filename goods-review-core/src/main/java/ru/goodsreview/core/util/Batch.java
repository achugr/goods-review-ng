package ru.goodsreview.core.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:07
 */
public abstract class Batch<T> {
    private final static Logger log = Logger.getLogger(Batch.class);

    private final static int BATCH_SIZE = 512;

    private final int batchSize;
    private List<T> batchList;

    public Batch() {
        this(BATCH_SIZE);
    }

    public Batch(final int size) {
        batchSize = size;
        batchList = new ArrayList<T>(size);
    }

    public void submit(final T element) {
        batchList.add(element);
        log.info("[Batch] submitted");
        if (batchList.size() == batchSize) {
            log.info("[Batch] handling");
            handle(batchList);
            batchList.clear();
        }
    }

    public void flush() {
        if (batchList.size() != 0) {
            log.info("[Batch] flushed");
            handle(batchList);
            batchList.clear();
        }
    }

    public abstract void handle(List<T> list);
}
