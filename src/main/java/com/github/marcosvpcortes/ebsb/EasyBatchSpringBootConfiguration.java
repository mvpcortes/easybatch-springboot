/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb;

import com.github.marcosvpcortes.ebsb.job.JobSpring;
import com.github.marcosvpcortes.ebsb.job.scope.JobScope;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Configure the EasyBatch SpringBoot.
 *
 * @author marcosvpcortes
 */
@Configuration
@ComponentScan(basePackageClasses = {JobSpring.class, JobScope.class})
public class EasyBatchSpringBootConfiguration {

    /**
     * Build the jobscope manager
     *
     * @return
     */
    @Bean
    @Scope("singleton")
    JobScope jobScope() {
        return new JobScope();
    }

    /**
     * build the configurator of new scope
     *
     * @see
     * https://stackoverflow.com/questions/15435860/spring-javaconfig-beans-custom-scopes-and-annotations
     * @return
     */
    @Bean
    public CustomScopeConfigurer customScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        Map<String, Object> mapJobScope = new HashMap<>();
        mapJobScope.put("job", jobScope());
        configurer.setScopes(mapJobScope);
        return configurer;
    }

}
