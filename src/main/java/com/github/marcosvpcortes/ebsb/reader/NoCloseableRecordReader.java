/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.reader;

import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.Record;

/**
 *
 * @author marcosvpcortes
 */
public class NoCloseableRecordReader implements RecordReader{

    private final RecordReader recordReader;
    
    public NoCloseableRecordReader(RecordReader rr){
        this.recordReader = rr;
    }
    @Override
    public void open() throws Exception {
        this.recordReader.open();
    }

    @Override
    public Record readRecord() throws Exception {
        return this.recordReader.readRecord();
    }

    @Override
    public void close() throws Exception {
        //not close
    }
    
}
