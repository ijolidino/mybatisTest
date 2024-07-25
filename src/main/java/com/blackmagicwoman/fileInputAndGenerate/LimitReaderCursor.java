package com.blackmagicwoman.fileInputAndGenerate;

import lombok.Data;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * @program: mybatisTest
 * @description: 限量读取数据
 * @author: heise
 * @create: 2022-09-07 20:10
 * */
@Data
public class LimitReaderCursor<T> implements Closeable, Iterable<List<T>> {

    /**
     * 文件分割符
     */
    private String spilt;

    private ILineStr2BeanHandler<T> iLineStr2BeanHandler;

    private final BufferedReader bufferedReader;

    private int limit = 1;

    private final FileReaderIterator cursorIterator = new FileReaderIterator();

    private boolean iteratorRetrieved;

    private CursorStatus status = CursorStatus.CREATED;

    private int indexLineNum = -1;

    public LimitReaderCursor(BufferedReader bufferedReader,
                             String spilt,
                             int limit,
                             ILineStr2BeanHandler<T> lineStr2BeanHandler) {
        this.bufferedReader = bufferedReader;
        this.spilt = spilt;
        this.iLineStr2BeanHandler = lineStr2BeanHandler;
        if (limit >= 1) {
            this.limit = limit;
        }
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    @Override
    public Iterator<List<T>> iterator() {
        if (iteratorRetrieved) {
            throw new IllegalStateException("Cannot open more than one iterator on a Cursor");
        }
        if (isClosed()) {
            throw new IllegalStateException("A Cursor is already closed.");
        }
        iteratorRetrieved = true;
        return cursorIterator;
    }

    private boolean isClosed() {
        return status == CursorStatus.CLOSED || status == CursorStatus.CONSUMED;
    }


    private List<T> fetchNextUsingRowBound() {
        return fetchNextObjectFromFile();
    }


    protected List<T> fetchNextObjectFromFile() {
        try {
            boolean readNullFlag = false;
            if (isClosed()) {
                return null;
            }
            List<T> next = new ArrayList<>();
            status = CursorStatus.OPEN;
            for (int i = 0; i < limit; i++) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    readNullFlag = true;
                    break;
                }
                indexLineNum++;
                if ("".equals(readLine)) {
                    continue;
                }
                next.add(iLineStr2BeanHandler.convert(readLine, spilt));
            }
            if (readNullFlag) {
                //已经没有数据了
                close();
                status = CursorStatus.CONSUMED;
            }
            return next;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private class FileReaderIterator implements Iterator<List<T>> {

        List<T> object;

        @Override
        public boolean hasNext() {
            if (object == null) {
                object = fetchNextUsingRowBound();
            }
            return object != null;
        }

        @Override
        public List<T> next() {
            // Fill next with object fetched from hasNext()
            List<T> next = object;
            if (next == null) {
                next = fetchNextUsingRowBound();
            }
            if (next != null) {
                object = null;
                return next;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove element from Cursor");
        }
    }
}
