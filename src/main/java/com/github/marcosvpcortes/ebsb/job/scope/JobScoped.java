/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job.scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Scope;

/**
 *
 * Define if a bean has scope in the current building/running job
 * @see https://stackoverflow.com/questions/15435860/spring-javaconfig-beans-custom-scopes-and-annotations
 * @author marcosvpcortes
 */
@Scope("job")
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JobScoped {
    
}
