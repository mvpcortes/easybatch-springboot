/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job;

import com.github.marcosvpcortes.ebsb.job.scope.JobScope;
import com.github.marcosvpcortes.ebsb.job.scope.JobScopeContainer;
import com.github.marcosvpcortes.ebsb.job.scope.JobScopeContainerImpl;
import java.util.function.Function;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobReport;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of read-process-write job pattern based on spring beans. This
 * job wrapper the {@link org.easybatch.core.job.Job}
 * 
 * The JobSpring register/unregister itself in the {@link JobScope} when 
 * {@link #call()} is called.
 * @author marcosvpcortes
 */
@Component
@Scope("prototype")
public class JobSpring implements Job {

    /**
     * Wrapped instance of {@link org.easybatch.core.job.Job}
     */
    private Job job;

    /**
     * Container used to store the spring beans in the job scope.
     */
    private final JobScopeContainer jobScopeContainer = new JobScopeContainerImpl();

    /**
     * Reference to JobScope manager.
     */
    private final JobScope jobScope;

    /**
     * Reference to ApplicationContext of application.
     */
    private final ApplicationContext applicationContext;

    /**
     * Create a new JobSpring.
     *
     * @param jobScope reference to JobScope
     * @param applicationContext reference to ApplicationContext
     */
    public JobSpring(JobScope jobScope, ApplicationContext applicationContext) {
        this.jobScope = jobScope;
        this.applicationContext = applicationContext;
    }

    /**
     * Get a bean in the context of this job using a class.
     *
     * @param <T> the type of the bean
     * @param clazzBean the class of T of the bean
     * @param args the constructor arguments to create the bean
     * @return
     */
    public <T> T getBeanInMyContext(Class<T> clazzBean, Object... args) {
        try {
            this.jobScope.pushJob(this);
            return applicationContext.getBean(clazzBean, args);
        } finally {
            this.jobScope.popJob();
        }
    }

    /**
     * Get a bean in the context of this job using its name.
     *
     * @param <T> the type of the bean
     * @param name the bean's name
     * @param clazzBean the class of the bean
     * @param args the constructor arguments to create the bean
     * @return
     */
    public <T> T getBeanInMyContext(String name, Class<T> clazzBean, Object... args) {
        try {
            this.jobScope.pushJob(this);
            Object o = applicationContext.getBean(name, args);
            if (!clazzBean.isInstance(o)) {
                throw new BeanNotOfRequiredTypeException(name, clazzBean, o.getClass());
            }
            return (T) o;
        } finally {
            this.jobScope.popJob();
        }
    }

    /**
     * Wrapper a Job.
     *
     * @param job the original job.
     * @return a JobSpring wrappering the original job.
     */
    protected JobSpring wrapperJob(Job job) {
        this.job = job;
        return this;
    }

    @Override
    public String getName() {
        return job.getName();
    }

    @Override
    public JobReport call() {
        try {
            jobScope.pushJob(this);
            return this.job.call();
        } finally {
            jobScope.popJob();
            jobScopeContainer.close();
        }
    }

    /**
     * If the specified bean name (nameBean) is not already associated with a
     * bean, attempts to compute its value using the given mapping function and
     * enters it into the container unless null.
     *
     * @param nameBean the bean's name
     * @param funcAbsent the function that create the bean
     * @return the bean associated with nameBean
     */
    public Object computeBeanIfAbsent(String nameBean, Function<String, Object> funcAbsent) {
        return jobScopeContainer.computeIfAbsent(nameBean, funcAbsent);
    }

    /**
     * Register a callback to be executed on destruction of job scope container.
     *
     * @param strNameBean the bean's name
     * @param mappingFunction the function to compute a value
     */
    public void registerDestructionCallback(String strNameBean, Runnable mappingFunction) {
        jobScopeContainer.registerDestructionCallback(strNameBean, mappingFunction);
    }

    /**
     * Remove the bean from scope container
     *
     * @param strNameBean the bean's name
     * @return the object removed
     */
    public Object remove(String strNameBean) {
        return jobScopeContainer.remove(strNameBean);
    }
}
