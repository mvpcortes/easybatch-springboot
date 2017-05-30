/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.reader;

import java.util.Iterator;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.reader.StringRecordReader;
import org.easybatch.core.record.Record;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

/**
 *
 * @author marcos
 */
public class RecordReaderIterableTest {

    private RecordReader recordReader = new StringRecordReader("1\n2\n3\n");

    @org.junit.Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void when_IterableRecordReader_iterates_normal_way_then_ok() {
        Iterable iterable = new RecordReaderIterable(recordReader);
        int i = 1;
        for (Object o : iterable) {
            Record<String> record = (Record<String>) o;
            assertThat(record.getPayload(), is(equalTo(Integer.toString(i))));
            i++;
        }
    }

    @Test
    public void when_IterableRecordReader_iterates_with_iterator_way_then_ok() {
        Iterable iterable = new RecordReaderIterable(recordReader);
        Iterator<Record> iterator = iterable.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Record record = iterator.next();
            assertThat(record.getPayload(), is(equalTo(Integer.toString(i))));
            i++;
        }

        assertThat(iterator.next(), is(nullValue()));
    }

    @Test
    public void when_IterableRecordReader_with_not_openclose_iterates_normal_way_then_ok() throws Exception {
        Iterable iterable = new RecordReaderIterable(recordReader, false);
        int i = 1;

        recordReader.open();
        for (Object o : iterable) {
            Record<String> record = (Record<String>) o;
            assertThat(record.getPayload(), is(equalTo(Integer.toString(i))));
            i++;
        }
        recordReader.close();
    }

    @Test
    public void when_fail_to_open_recordReader_then_throw_IllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cannot open recordReader");
        recordReader = spy(recordReader);
        doThrow(new RuntimeException("test")).when(recordReader).open();

        Iterable iterable = new RecordReaderIterable(recordReader);

        iterable.iterator().next();
    }

    @Test
    public void when_fail_to_second_readRecord_then_throw_IllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("RecordReader iteration error on readRecord");
        recordReader = spy(recordReader);
        doCallRealMethod()
                .doThrow(new RuntimeException("test"))
                .when(recordReader).readRecord();

        Iterable iterable = new RecordReaderIterable(recordReader);

        Iterator it = iterable.iterator();
        it.next();
        it.next();
    }

    @Test
    public void when_fail_to_first_readRecord_then_throw_IllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("RecordReader iteration error on readRecord(startup)");
        recordReader = spy(recordReader);
        doThrow(new RuntimeException("test"))
                .when(recordReader).readRecord();

        Iterable iterable = new RecordReaderIterable(recordReader);

        Iterator it = iterable.iterator();
        it.next();
    }

    @Test
    public void when_fail_to_close_recordReader_then_throw_IllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("RecordReader iteration error");
        recordReader = spy(recordReader);
        doThrow(new RuntimeException("test")).when(recordReader).close();

        Iterable<Record> iterable = new RecordReaderIterable(recordReader);

        Iterator<Record> iterator = iterable.iterator();

        assertThat(iterator.next().getPayload().toString(), is(equalTo("1")));
        assertThat(iterator.next().getPayload().toString(), is(equalTo("2")));
        assertThat(iterator.next().getPayload().toString(), is(equalTo("3")));
        iterator.next();//it will fail here.
    }
}
