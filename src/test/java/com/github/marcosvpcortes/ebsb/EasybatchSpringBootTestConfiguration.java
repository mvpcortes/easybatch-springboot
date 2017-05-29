/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb;

import com.github.marcosvpcortes.ebsb.processor.IdentityRecordProcessor;
import java.io.StringWriter;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.reader.StringRecordReader;
import org.easybatch.core.writer.RecordWriter;
import org.easybatch.core.writer.StringRecordWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author marcosvpcortes
 */
@Configuration
@Import({EasyBatchSpringBootConfiguration.class})
@ComponentScan(basePackageClasses = {IdentityRecordProcessor.class})
public class EasybatchSpringBootTestConfiguration {

    @Bean
    @Scope("easybatch-job")
    public RecordWriter string1RecordWriter(StringWriter stringWriter) {
        return new StringRecordWriter(stringWriter);
    }

    @Bean
    @Scope("easybatch-job")
    public RecordReader string1RecordReader() {
        return new StringRecordReader("a b c d e");
    }
}
