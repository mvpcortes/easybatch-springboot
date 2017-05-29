package com.github.marcosvpcortes.ebsb;

import com.github.marcosvpcortes.ebsb.job.JobSpringBuilder;
import com.github.marcosvpcortes.ebsb.processor.IdentityRecordProcessor;
import java.io.StringWriter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EasybatchSpringBootTestConfiguration.class})
public class EasybatchSpringBootWrapperApplicationTest {

    @Autowired
    private JobSpringBuilder springJobBuilder;
    
    @Test
    public void contextLoads() {
    }

    @Ignore
    @Test
    public void when_build_job_then_use_the_job_scope_context() {
        StringWriter stringWriter = new StringWriter();
        springJobBuilder
                .reader("string1RecordReader")
                .processor(IdentityRecordProcessor.class)
                .writer("string1RecordWriter", stringWriter)
                .batchSize(1)
                .build()
                .call();
                
    }

}
