/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.processor;

import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.Record;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcosvpcortes
 */
@Service
@Scope("easybatch-job")
public class IdentityRecordProcessor implements RecordProcessor<Record, Record> {

    @Override
    public Record processRecord(Record record) throws Exception {
        return record;
    }
    
}
