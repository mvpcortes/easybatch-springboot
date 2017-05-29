/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job;

import org.easybatch.core.filter.RecordFilter;
import org.easybatch.core.job.Job;
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

/**
 * Interface of a jobBuilder that deal with spring beans. The methods are two
 * types, the method(nameBean, args) searches beans by its name. The
 * method(clazzBean, args) searches beans by a type. Others methods are replicas
 * of {@link org.easybatch.core.job.JobBuilder} methods with delegate function.
 * Methods replicas that put beans null ({@link #reader(org.easybatch.core.reader.RecordReader)},
 * {@link #writer(org.easybatch.core.writer.RecordWriter)}, etc) will not
 * managed by the job scope.
 *
 * @author marcosvpcortes
 * @see
 * <a href="http://www.easybatch.org/v5.0.0/api/org/easybatch/core/job/JobBuilder.html">
 * JobBuilder</a>
 */
public interface ApplicationContextJobBuilder {

    /**
     * Register a RecordReader bean by its name.
     *
     * @param nameBean the spring bean name of type RecordReader.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder reader(String nameBean, Object... args);

    /**
     * Register a RecordFilter bean by its name.
     *
     * @param nameBean the spring bean name of type RecordFilter.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder filter(String nameBean, Object... args);

    /**
     * Register a RecordMapper bean by its name.
     *
     * @param nameBean the spring bean name of type RecordMapper.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder mapper(String nameBean, Object... args);

    /**
     * Register a RecordValidator bean by its name.
     *
     * @param nameBean the spring bean name of type RecordValidator.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder validator(String nameBean, Object... args);

    /**
     * Register a RecordProcessor bean by its name.
     *
     * @param nameBean the spring bean name of type RecordProcessor.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder processor(String nameBean, Object... args);

    /**
     * Register a RecordMarshaller bean by its name.
     *
     * @param nameBean the spring bean name of type RecordMarshaller.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder marshaller(String nameBean, Object... args);

    /**
     * Register a RecordWriter bean by its name.
     *
     * @param nameBean the spring bean name of type RecordWriter.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder writer(String nameBean, Object... args);

    /**
     * Register a JobListener bean by its name.
     *
     * @param nameBean the spring bean name of type JobListener.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder jobListener(String nameBean, Object... args);

    /**
     * Register a BatchListener bean by its name.
     *
     * @param nameBean the spring bean name of type BatchListener.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder batchListener(String nameBean, Object... args);

    /**
     * Register a ReaderListener bean by its name.
     *
     * @param nameBean the spring bean name of type ReaderListener.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder readerListener(String nameBean, Object... args);

    /**
     * Register a PipelineListener bean by its name.
     *
     * @param nameBean the spring bean name of type PipelineListener.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder pipelineListener(String nameBean, Object... args);

    /**
     * Register a WriterListener bean by its name.
     *
     * @param nameBean the spring bean name of type WriterListener.
     * @param args arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder writerListener(String nameBean, Object... args);

    /**
     * Register a RecordReader bean by its class.
     *
     * @param clazzBean the class of spring bean that extends RecordReader.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder reader(Class<? extends RecordReader> clazzBean, Object... args);

    /**
     * Register a RecordFilter bean by its class.
     *
     * @param clazzBean the class of spring bean that extends RecordFilter.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder filter(Class<? extends RecordFilter> clazzBean, Object... args);

    /**
     * Register a RecordMapper bean by its class.
     *
     * @param clazzBean the class of spring bean that extends RecordMapper.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder mapper(Class<? extends RecordMapper> clazzBean, Object... args);

    /**
     * Register a RecordValidator bean by its class.
     *
     * @param clazzBean the class of spring bean that extends RecordValidator.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder validator(Class<? extends RecordValidator> clazzBean, Object... args);

    /**
     * Register a RecordProcessor bean by its class.
     *
     * @param clazzBean the class of spring bean that extends RecordProcessor.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder processor(Class<? extends RecordProcessor> clazzBean, Object... args);

    /**
     * Register a RecordMarshaller bean by its class.
     *
     * @param clazzBean the class of spring bean that extends RecordMarshaller.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder marshaller(Class<? extends RecordMarshaller> clazzBean, Object... args);

    /**
     * Register a RecordWriter bean by its class.
     *
     * @param clazzBean the class of spring bean that extends RecordWriter.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder writer(Class<? extends RecordWriter> clazzBean, Object... args);

    /**
     * Register a JobListener bean by its class.
     *
     * @param clazzBean the class of spring bean that extends JobListener.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder jobListener(Class<? extends JobListener> clazzBean, Object... args);

    /**
     * Register a BatchListener bean by its class.
     *
     * @param clazzBean the class of spring bean that extends BatchListener.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder batchListener(Class<? extends BatchListener> clazzBean, Object... args);

    /**
     * Register a ReaderListener bean by its class.
     *
     * @param clazzBean the class of spring bean that extends ReaderListener.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder readerListener(Class<? extends RecordReaderListener> clazzBean, Object... args);

    /**
     * Register a PipelineListener bean by its class.
     *
     * @param clazzBean the class of spring bean that extends PipelineListener.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder pipelineListener(Class<? extends PipelineListener> clazzBean, Object... args);

    /**
     * Register a WriterListener bean by its class.
     *
     * @param clazzBean the class of spring bean that extends WriterListener.
     * @param args the arguments to construct the bean.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder writerListener(Class<? extends RecordWriterListener> clazzBean, Object... args);

    /**
     * Set the job name. This method deletage to
     * {@link org.easybatch.core.job.JobBuilder#named(String name)}
     *
     * @param name the job name
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder named(String name);

    /**
     * Set the job name. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#reader(RecordReader recordReader)}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordReader the recordReader
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder reader(RecordReader recordReader);

    /**
     * Register a record reader. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#filter}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordFilter the record filter to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder filter(RecordFilter recordFilter);

    /**
     * Register a record mapper. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#mapper}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordMapper the record mapper to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder mapper(RecordMapper recordMapper);

    /**
     * Register a record validator. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#validator}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordValidator the record validator to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder validator(RecordValidator recordValidator);

    /**
     * Register a record processor. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#processor}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordProcessor the record processor to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder processor(RecordProcessor recordProcessor);

    /**
     * Register a record mashaller. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#marshaller}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordMarshaller the record processor to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder marshaller(RecordMarshaller recordMarshaller);

    /**
     * Register a record mashaller. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#marshaller}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordWriter the record marshaller to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder writer(RecordWriter recordWriter);

    /**
     * Set a threshold for errors. The job will be aborted if the threshold is
     * exceeded.
     *
     * @param errorThreshold the error threshold.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder errorThreshold(long errorThreshold);

    /**
     * Activate JMX monitoring.
     *
     * @param jmx true to enable jmx monitoring.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder enableJmx(boolean jmx);

    /**
     * Set the batch size.
     *
     * @param batchSize the batch size
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder batchSize(int batchSize);

    /**
     * Register a job listener. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#jobListener}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param jobListener the job listener to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder jobListener(JobListener jobListener);

    /**
     * Register a batch listener. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#batchListener}
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param batchListener the batch listener to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder batchListener(BatchListener batchListener);

    /**
     * Register a reader listener. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#readerListener(org.easybatch.core.listener.RecordReaderListener) }
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordReaderListener the reader listener to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder readerListener(RecordReaderListener recordReaderListener);

    /**
     * Register a pipeline listener. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#pipelineListener(org.easybatch.core.listener.PipelineListener) }
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param pipelineListener the pipeline listener to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder pipelineListener(PipelineListener pipelineListener);

    /**
     * Register a writer listener. This method delegates to
     * {@link org.easybatch.core.job.JobBuilder#writerListener(org.easybatch.core.listener.RecordWriterListener) }
     * <b>and not manager the object like a spring bean.</b>
     *
     * @param recordWriterListener the record writer listener to register.
     * @return the same job builder (builder pattern)
     */
    public ApplicationContextJobBuilder writerListener(RecordWriterListener recordWriterListener);

    /**
     * Build a batch job instance. It opens the job context to this job in the
     * spring. It allows the beans to reference the created beans during the job
     * creation. The beans will be destroyed when the job finish.
     *
     * @return a batch job instance managed by spring.
     */
    public Job build();
}
