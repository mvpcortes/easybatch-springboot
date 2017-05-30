/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.Record;
import org.springframework.util.Assert;

/**
 *
 * @author marcos
 */
public class MergeRecordReader implements RecordReader {

    private final List<RecordReader> listReaders;
    private int posReader;

    public MergeRecordReader(RecordReader... rrs) {
        this(Arrays.asList(rrs));
    }

    public MergeRecordReader(List<RecordReader> rrs) {
        IntStream.range(0, rrs.size())
                .forEach(i -> Assert.notNull(rrs.get(i), String.format("The recordReader arg %d cannot be null", i)));
        listReaders = rrs;
        posReader = -1;
    }

    @Override
    public void open() throws Exception {
        posReader = 0;
        if (!listReaders.isEmpty()) {
            try {
                getActualReader(posReader).open();
            } catch (Exception e) {
                throw new IllegalStateException(String.format("Cannot open reader %d", posReader), e);
            }
        }
    }

    protected void nextReader() {
        if (posReader < listReaders.size()) {
            try {
                getActualReader(posReader).close();
            } catch (Exception e) {
                throw new IllegalStateException(String.format("Cannot close reader %d", posReader), e);
            }
            posReader++;
            if (posReader < listReaders.size()) {
                try {
                    getActualReader(posReader).open();
                } catch (Exception e) {
                    throw new IllegalStateException(String.format("Cannot open reader %d", posReader), e);
                }
            }
        }
    }

    @Override
    public Record readRecord() throws Exception {
        if (posReader < 0) {
            throw new IllegalStateException("MergeRecordReader does not open");
        }

        if (posReader < listReaders.size()) {
            Record record = getActualReader(posReader).readRecord();
            if (record != null) {
                return record;
            } else {
                nextReader();
                return readRecord();
            }
        } else {
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        if (posReader < listReaders.size()) {
            try {

                getActualReader(posReader).close();//close actual reader
            } catch (Exception e) {
                throw new IllegalStateException(String.format("Cannot close actual reader: %d(%s)", posReader, getActualReader(posReader).toString()), e);
            }
        }
        //put posReader in the end of list to represent the close state
        posReader = listReaders.size();
    }

    private RecordReader getActualReader(int posReader) {
        return listReaders.get(posReader);
    }
}
