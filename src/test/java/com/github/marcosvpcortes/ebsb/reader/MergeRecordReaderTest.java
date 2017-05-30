/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.reader;

import java.util.List;
import static java.util.stream.Collectors.toList;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.reader.StringRecordReader;
import org.easybatch.core.record.Record;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 *
 * @author marcos
 */
public class MergeRecordReaderTest extends MergeRecordReader {

    private RecordReader reader1 = new StringRecordReader("tem\nduas\nlinhas");

    private RecordReader reader2 = new StringRecordReader("1\n2\n3\n4");

    private final StringRecordReader reader3 = new StringRecordReader("I\nam\nthird\nor\nnot");

    @org.junit.Rule
    public final ExpectedException thrown = ExpectedException.none();

    public void when_create_mergerecordreader_with_not_readers_then_return_null() throws Exception {
        MergeRecordReader mr = new MergeRecordReader();

        assertThat(mr.readRecord(), is(nullValue()));
    }

    @Test
    public void quando_tres_readers_entao_lelos_de_forma_correta() throws Exception {
        MergeRecordReader mr = new MergeRecordReader(reader1, reader2, reader3);
        mr.open();
        List<String> list = RecordReaderToStream
                .toStream(mr)
                .map(Record::getPayload)
                .map(Object::toString)
                .collect(toList());
        mr.close();
        assertThat(list.toString(), is(equalTo("[tem, duas, linhas, 1, 2, 3, 4, I, am, third, or, not]")));
    }

    @Test
    public void quando_tenta_pegar_readValue_gerando_falha_open_entao_IllegalStateException() throws Exception {

        reader1 = new StringRecordReader("");
        reader2 = mock(RecordReader.class);
        doThrow(new Exception("teste", null)).when(reader2).open();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cannot open reader 1");

        MergeRecordReader mr = new MergeRecordReader(reader1, reader2, reader3);
        mr.open();
        mr.readRecord();
    }

    @Test
    public void quando_call_readRecord_but_fail_on_close_then_IllegalStateException() throws Exception {

        reader1 = mock(RecordReader.class);
        doThrow(new Exception("test", null)).when(reader1).close();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cannot close reader 0");

        MergeRecordReader mr = new MergeRecordReader(reader1, reader2, reader3);
        mr.open();
        mr.readRecord();
    }

    @Test
    public void quando_tenta_cachear_gerando_falha_no_open_entao_IllegalStateException() throws Exception {

        reader1 = mock(RecordReader.class);
        doThrow(new Exception("teste", null)).when(reader1).open();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cannot open reader 0");

        MergeRecordReader mr = new MergeRecordReader(reader1, reader2, reader3);
        mr.open();
    }

    @Test
    public void quando_readRecord_sem_actualReader_entao_retorna_nulo() throws Exception {
        MergeRecordReader mr = new MergeRecordReader(reader1);
        mr.open();
        assertThat(((String) mr.readRecord().getPayload()), is(equalTo("tem")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("duas")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("linhas")));
        assertThat(mr.readRecord(), is(nullValue()));
    }

    @Test
    public void quando_readRecord_sem_chamar_hasNextRecord_entao_passa_para_proximo_reader_adequadamente() throws Exception {
        MergeRecordReader mr = new MergeRecordReader(reader1, reader2);
        mr.open();
        assertThat(mr.readRecord().getPayload(), is(equalTo("tem")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("duas")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("linhas")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("1")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("2")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("3")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("4")));
        assertThat(mr.readRecord(), is(nullValue()));
    }

    @Test
    public void quando_reader_e_vazio_entao_pula_para_proximo() throws Exception {
        MergeRecordReader mr = new MergeRecordReader(reader1, new StringRecordReader(""), reader2);
        mr.open();
        assertThat(mr.readRecord().getPayload(), is(equalTo("tem")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("duas")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("linhas")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("1")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("2")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("3")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("4")));
        assertThat(mr.readRecord(), is(nullValue()));
    }

    @Test
    public void quando_reader_ultimo_e_vazio_entao_fecha_reader() throws Exception {
        MergeRecordReader mr = new MergeRecordReader(reader1, new StringRecordReader(""));
        mr.open();
        assertThat(mr.readRecord().getPayload(), is(equalTo("tem")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("duas")));
        assertThat(mr.readRecord().getPayload(), is(equalTo("linhas")));
        assertThat(mr.readRecord(), is(nullValue()));
    }

    @Test
    public void when_recordreader_nextRecord_but_not_open_then_throw_exception() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("MergeRecordReader does not open");
        MergeRecordReader mr = new MergeRecordReader(reader1, reader2);
        mr.readRecord();
    }

    @Test
    public void when_call_readRecord_but_fail_on_close_last_reader_then_IllegalStateException() throws Exception {

        reader2 = spy(reader2);
        doThrow(new RuntimeException("test", null)).when(reader2).close();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cannot close reader 1");

        MergeRecordReader mr = new MergeRecordReader(reader1, reader2);
        mr.open();
        //reader1
        Record x = mr.readRecord();
        x = mr.readRecord();
        x = mr.readRecord();

        //reader2
        x = mr.readRecord();
        x = mr.readRecord();
        x = mr.readRecord();
        x = mr.readRecord();

        assertThat(mr.readRecord(), is(nullValue()));

    }

    @Test
    public void when_call_close_but_fail_on_close_of_reader1_then_IllegalStateException() throws Exception {

        reader1 = spy(reader1);
        doThrow(new RuntimeException("test", null)).when(reader1).close();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cannot close actual reader: 0");

        MergeRecordReader mr = new MergeRecordReader(reader1, reader2);
        mr.open();
        //reader1
        Record x = mr.readRecord();
        
        mr.close();

    }

}
