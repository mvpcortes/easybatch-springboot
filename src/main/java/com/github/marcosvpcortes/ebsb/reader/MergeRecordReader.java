/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marcos
 */
public class MergeRecordReader implements RecordReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MergeRecordReader.class);
    private final List<RecordReader> listReaders;
    private int posReader;

    public MergeRecordReader(RecordReader... rrs) {
        this(Arrays.asList(rrs));
    }

    public MergeRecordReader(List<RecordReader> rrs) {
        listReaders = rrs;
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
        try {
            if (posReader < listReaders.size()) {
                getActualReader(posReader).close();
                posReader++;
                if (posReader < listReaders.size()) {
                    getActualReader(posReader).open();
                }
            }

        } catch (Exception ex) {
            throw new IllegalStateException(String.format("Cannot open next reader %d", posReader), ex);
        }
    }

    @Override
    public Record readRecord() throws Exception {
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
        int i = 0;
        for (RecordReader rr : listReaders) {

            try {
                rr.close();
            } catch (Exception e) {
                throw new IOException(String.format("Cannot close reader %d", i), e);
            }
            i++;
        }
    }

    private RecordReader getActualReader(int posReader) {
        return listReaders.get(posReader);
    }
}
