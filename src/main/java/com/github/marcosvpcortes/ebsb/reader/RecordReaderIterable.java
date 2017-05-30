/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.reader;

import java.util.Iterator;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.Record;

/**
 *
 * @author marcos
 * @param <T>
 * @see
 * https://stackoverflow.com/questions/20310209/how-to-perform-stream-functions-on-an-iterable
 */
public class RecordReaderIterable<T extends Record> implements Iterable<T> {

    private final boolean openCloseRecordReader;

    private final RecordReader recordReader;

    public RecordReaderIterable(RecordReader recordReader) {
        this(recordReader, true);
    }

    public RecordReaderIterable(RecordReader recordReader, boolean openCloseRecordReader) {
        this.recordReader = recordReader;
        this.openCloseRecordReader = openCloseRecordReader;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private boolean start = false;
            private T next = null;

            @Override
            public boolean hasNext() {
                if (!start) {
                    if (openCloseRecordReader) {
                        try {
                            recordReader.open();
                        } catch (Exception e) {
                            throw new IllegalStateException("Cannot open recordReader", e);
                        }
                    }
                    try {
                        next = (T) recordReader.readRecord();
                        start = true;
                    } catch (Exception e) {
                        throw new IllegalStateException("RecordReader iteration error on readRecord(startup)", e);
                    }
                }
                return next != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    Record actual = next;
                    try {
                        next = (T) recordReader.readRecord();
                    } catch (Exception e) {
                        throw new IllegalStateException("RecordReader iteration error on readRecord", e);
                    }
                    try{
                        if (openCloseRecordReader && next == null) {
                            recordReader.close();
                        }
                    }catch(Exception e){
                        throw new IllegalStateException("RecordReader iteration error on close", e);
                    }
                    
                    return (T) actual;
                } else {
                    return null;
                }
            }
        };
    }

}
