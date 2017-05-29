/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job.scope;

import java.util.function.Function;

/**
 * Interface container of beans in the Job Scope Context.
 * @author marcosvpcortes
 */
public interface JobScopeContainer{

    Object computeIfAbsent(String nameBean, Function<String, Object> funcAbsent);

    public void registerDestructionCallback(String strNameBean, Runnable r);

    public Object remove(String strNameBean);
    
    public void close();

}
