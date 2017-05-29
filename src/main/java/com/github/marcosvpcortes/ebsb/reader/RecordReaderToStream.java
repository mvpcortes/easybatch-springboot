/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.reader;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.Record;

/**
 *
 * @author marcos
 */
public class RecordReaderToStream {
    
    public static <T extends Record> Stream<T> toStream(RecordReader recordReader){
        Iterable<T> iterable = new IterableRecordReader<>(recordReader);
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
