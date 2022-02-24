package com.wqzeng.springbtgradle.pattern.iterator;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class SelectIterator<T> implements Iterator<List<T>> {
    private boolean hasNext = true;
    private long cursor = 0;
    private int pageSize;
    private  T lastRecord;

    public SelectIterator(int pageSize) {
        this.pageSize = pageSize;
    }

    public SelectIterator(long cursor, int pageSize) {
        this.cursor = cursor;
        this.pageSize = pageSize;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public List<T> next() {
        if (!hasNext) {
            throw new NoSuchElementException("No more element");
        }
        long statTime = System.currentTimeMillis();
        List<T> results = select(cursor, pageSize);
        if (CollectionUtils.isEmpty(results) || results.size() < pageSize) {
            hasNext = false;
        } else {
            cursor = getCursor(results);
            lastRecord = results.get(results.size() - 1) ;
        }
        results = results == null ? new ArrayList<>() : results;
        return results;
    }

    /**
     * 获取当前游标
     * @param results
     * @return
     */
    protected abstract long getCursor(List<T> results);

    /**
     * 查询数据
     * @param cursor
     * @param pageSize
     * @return
     */
    protected abstract List<T> select(long cursor, int pageSize);
}
