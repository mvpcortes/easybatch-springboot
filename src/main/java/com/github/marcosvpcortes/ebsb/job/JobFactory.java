/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Factory of Beans. It would be complex get
 * {@link ApplicationContextJobBuilder} directly with @Autowired because the
 * bean cannot be reused. Because this we make this bean that have only a method
 * {@link #aNewJob()} to generate the {@link ApplicationContextJobBuilder} and
 * allow the programmer builder your job.
 *
 * @author marcosvpcortes
 */
@Service
@Scope("singleton")
public class JobFactory {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Create a new {@link ApplicationContextJobBuilder} to be used in Job
     * build.
     *
     * @return
     */
    @Autowired
    public ApplicationContextJobBuilder aNewJob() {
        return applicationContext.getBean(JobSpringBuilder.class);
    }
}
