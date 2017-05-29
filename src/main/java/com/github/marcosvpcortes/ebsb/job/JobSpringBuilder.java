/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job;

import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import org.easybatch.core.filter.RecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.listener.BatchListener;
import org.easybatch.core.listener.JobListener;
import org.easybatch.core.listener.PipelineListener;
import org.easybatch.core.listener.RecordReaderListener;
import org.easybatch.core.listener.RecordWriterListener;
import org.easybatch.core.mapper.RecordMapper;
import org.easybatch.core.marshaller.RecordMarshaller;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.validator.RecordValidator;
import org.easybatch.core.writer.RecordWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * This bean implements the {@link ApplicationContextJobBuilder}.
 *
 * @author marcosvpcortes
 */
@Component
@Scope("prototype")
public class JobSpringBuilder implements ApplicationContextJobBuilder {

    /**
     * Instance of original {@link org.easybatch.core.job.JobBuilder}
     */
    private JobBuilder jobBuilder;

    /**
     * A factory of job builder. It is used to decouple this class and the way
     * to construct a job builder.
     */
    private Supplier<JobBuilder> jobBuilderFactory = () -> JobBuilder.aNewJob();

    /**
     * Instance of JobSpring.
     */
    private final JobSpring jobSpring;

    /**
     * Construct the JobSpringBuilder.
     *
     * @param jobSpring Instance of JobBuilder that wrapper the original
     * {@link org.easybatch.core.job.Job}
     */
    public JobSpringBuilder(JobSpring jobSpring) {
        this.jobSpring = jobSpring;
    }

    /**
     * Init the JobSpringBuilder
     */
    @PostConstruct
    public void init() {
        jobBuilder = jobBuilderFactory.get();
    }

    /**
     * Change the jobBuilderFactory
     *
     * @param jobBuilderBuilder the new JobBuilder Factory
     */
    public void setJobBuilderBuilder(Supplier<JobBuilder> jobBuilderBuilder) {
        this.jobBuilderFactory = jobBuilderBuilder;
    }

    @Override
    public ApplicationContextJobBuilder reader(String nameBean, Object... args) {
        jobBuilder.reader(jobSpring.getBeanInMyContext(nameBean, RecordReader.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder filter(String nameBean, Object... args) {
        jobBuilder.filter(jobSpring.getBeanInMyContext(nameBean, RecordFilter.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder mapper(String nameBean, Object... args) {
        jobBuilder.mapper(jobSpring.getBeanInMyContext(nameBean, RecordMapper.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder validator(String nameBean, Object... args) {
        jobBuilder.validator(jobSpring.getBeanInMyContext(nameBean, RecordValidator.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder processor(String nameBean, Object... args) {
        jobBuilder.processor(jobSpring.getBeanInMyContext(nameBean, RecordProcessor.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder marshaller(String nameBean, Object... args) {
        jobBuilder.marshaller(jobSpring.getBeanInMyContext(nameBean, RecordMarshaller.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder writer(String nameBean, Object... args) {
        jobBuilder.writer(jobSpring.getBeanInMyContext(nameBean, RecordWriter.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder jobListener(String nameBean, Object... args) {
        jobBuilder.jobListener(jobSpring.getBeanInMyContext(nameBean, JobListener.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder batchListener(String nameBean, Object... args) {
        jobBuilder.batchListener(jobSpring.getBeanInMyContext(nameBean, BatchListener.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder readerListener(String nameBean, Object... args) {
        jobBuilder.readerListener(jobSpring.getBeanInMyContext(nameBean, RecordReaderListener.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder pipelineListener(String nameBean, Object... args) {
        jobBuilder.pipelineListener(jobSpring.getBeanInMyContext(nameBean, PipelineListener.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder writerListener(String nameBean, Object... args) {
        jobBuilder.writerListener(jobSpring.getBeanInMyContext(nameBean, RecordWriterListener.class, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder reader(Class<? extends RecordReader> clazzBean, Object... args) {
        jobBuilder.reader(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder filter(Class<? extends RecordFilter> clazzBean, Object... args) {
        jobBuilder.filter(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder mapper(Class<? extends RecordMapper> clazzBean, Object... args) {
        jobBuilder.mapper(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder validator(Class<? extends RecordValidator> clazzBean, Object... args) {
        jobBuilder.validator(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder processor(Class<? extends RecordProcessor> clazzBean, Object... args) {
        jobBuilder.processor(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder marshaller(Class<? extends RecordMarshaller> clazzBean, Object... args) {
        jobBuilder.marshaller(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder writer(Class<? extends RecordWriter> clazzBean, Object... args) {
        jobBuilder.writer(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder jobListener(Class<? extends JobListener> clazzBean, Object... args) {
        jobBuilder.jobListener(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder batchListener(Class<? extends BatchListener> clazzBean, Object... args) {
        jobBuilder.batchListener(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder readerListener(Class<? extends RecordReaderListener> clazzBean, Object... args) {
        jobBuilder.readerListener(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder pipelineListener(Class<? extends PipelineListener> clazzBean, Object... args) {
        jobBuilder.pipelineListener(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder writerListener(Class<? extends RecordWriterListener> clazzBean, Object... args) {
        jobBuilder.writerListener(jobSpring.getBeanInMyContext(clazzBean, args));
        return this;
    }

    @Override
    public ApplicationContextJobBuilder named(String name) {
        jobBuilder.named(name);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder reader(RecordReader recordReader) {
        jobBuilder.reader(recordReader);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder filter(RecordFilter recordFilter) {
        jobBuilder.filter(recordFilter);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder mapper(RecordMapper recordMapper) {
        jobBuilder.mapper(recordMapper);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder validator(RecordValidator recordValidator) {
        jobBuilder.validator(recordValidator);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder processor(RecordProcessor recordProcessor) {
        jobBuilder.processor(recordProcessor);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder marshaller(RecordMarshaller recordMarshaller) {
        jobBuilder.marshaller(recordMarshaller);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder writer(RecordWriter recordWriter) {
        jobBuilder.writer(recordWriter);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder errorThreshold(long errorThreshold) {
        jobBuilder.errorThreshold(errorThreshold);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder enableJmx(boolean jmx) {
        jobBuilder.enableJmx(jmx);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder batchSize(int batchSize) {
        jobBuilder.batchSize(batchSize);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder jobListener(JobListener jobListener) {
        jobBuilder.jobListener(jobListener);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder batchListener(BatchListener batchListener) {
        jobBuilder.batchListener(batchListener);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder readerListener(RecordReaderListener recordReaderListener) {
        jobBuilder.readerListener(recordReaderListener);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder pipelineListener(PipelineListener pipelineListener) {
        jobBuilder.pipelineListener(pipelineListener);
        return this;
    }

    @Override
    public ApplicationContextJobBuilder writerListener(RecordWriterListener recordWriterListener) {
        jobBuilder.writerListener(recordWriterListener);
        return this;
    }

    @Override
    public Job build() {
        return jobSpring.wrapperJob(jobBuilder.build());
    }
}
