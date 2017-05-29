/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job.scope;

import com.github.marcosvpcortes.ebsb.job.JobSpring;
import com.github.marcosvpcortes.ebsb.job.JobSpringBuilder;
import java.util.Stack;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

/**
 * Scope that managers the scopes of {@link JobSpring}. Each JobSpring has its
 * own context. It is registered when the {@link JobSpring#call() } is called
 * and when the register methods of {@link JobSpringBuilder} are calleds.
 *
 * @author marcosvpcortes
 */
public class JobScope implements Scope {

    /**
     * Auxiliary class to manager the stack of opened JobSpring contexts.
     *
     * @param <T>
     */
    private class StackThreadLocal<T> extends NamedThreadLocal<Stack<T>> {

        public StackThreadLocal(String name) {
            super(name);
        }

        @Override
        protected Stack<T> initialValue() {
            return new Stack<>();
        }
    }

    /**
     * Instance of ThreadLocal managers the stack of JobSpring contexts.
     */
    private final ThreadLocal<Stack<JobSpring>> tlStack = new StackThreadLocal<>("JobScopeThreadLocal");

    /**
     * Push a springJob to register/open its context.
     *
     * @param springJob
     */
    public void pushJob(JobSpring springJob) {
        tlStack.get().push(springJob);
    }

    /**
     * Pop the current opening {@link SpringJob} context.
     */
    public void popJob() {
        tlStack.get().pop();
    }

    @Override
    public Object get(String strName, ObjectFactory<?> of) {
        JobSpring springJob = getCurrentSpringJob();
        return springJob.computeBeanIfAbsent(strName, (sn) -> of.getObject());
    }

    @Override
    public Object remove(String strNameBean) {
        JobSpring springJob = getCurrentSpringJob();
        return springJob.remove(strNameBean);
    }

    @Override
    public void registerDestructionCallback(String strNameBean, Runnable r) {

        JobSpring springJob = getCurrentSpringJob();
        springJob.registerDestructionCallback(strNameBean, r);
    }

    @Override
    public Object resolveContextualObject(String string) {
        return null;
    }

    @Override
    public String getConversationId() {
        return Integer.toString(System.identityHashCode(getCurrentSpringJob()));
    }

    private JobSpring getCurrentSpringJob() {
        Stack<JobSpring> stack = tlStack.get();
        if (stack.empty()) {
            throw new NotOpenedJobScopeException();
        }
        return stack.peek();

    }
}
