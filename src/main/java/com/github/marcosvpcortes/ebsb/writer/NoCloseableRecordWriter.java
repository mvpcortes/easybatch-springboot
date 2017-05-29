/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.writer;

import org.easybatch.core.record.Batch;
import org.easybatch.core.writer.RecordWriter;

/**
 *
 * @author marcosvpcortes
 */
public class NoCloseableRecordWriter implements RecordWriter{

    private final RecordWriter recordWriter;
    
    public NoCloseableRecordWriter(RecordWriter recordWriter){
        this.recordWriter = recordWriter;
    }
    @Override
    public void open() throws Exception {
        this.recordWriter.open();
    }

    @Override
    public void writeRecords(Batch batch) throws Exception {
        this.recordWriter.writeRecords(batch);
    }

    @Override
    public void close() throws Exception {
        //no close
    }
    
}
