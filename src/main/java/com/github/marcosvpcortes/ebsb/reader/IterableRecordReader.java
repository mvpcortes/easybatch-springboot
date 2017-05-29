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
 * @see https://stackoverflow.com/questions/20310209/how-to-perform-stream-functions-on-an-iterable
 */
public class IterableRecordReader<T extends Record> implements Iterable<T> {

    private class IteratorImpl<X extends Record> implements Iterator<X> {

        private final RecordReader recordReader;

        private boolean start = false;
        private X next = null;

        public IteratorImpl(RecordReader rr) {
            this.recordReader = rr;
        }

        @Override
        public boolean hasNext() {
            if (!start) {
                try {
                    next = (X) this.recordReader.readRecord();
                    start = true;
                } catch (Exception e) {
                    throw new IllegalStateException("Erro na interação do recordReader", e);
                }
            }
            return next != null;
        }

        @Override
        public X next() {
            if (hasNext()) {

                Record actual = next;
                try {
                    next = (X) recordReader.readRecord();
                } catch (Exception e) {
                    throw new IllegalStateException("Error iterating recordReader", e);
                }
                return (X) actual;
            } else {
                return null;
            }
        }
    }

    private final RecordReader recordReader;

    public IterableRecordReader(RecordReader recordReader) {
        this.recordReader = recordReader;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImpl<>(recordReader);
    }

}
