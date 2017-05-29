/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Container of beans in the Job Context.
 *
 * @author marcosvpcortes
 */
public class JobScopeContainerImpl implements JobScopeContainer {

    /**
     * Map with beans managered by the {@link JobSpring}.
     */
    Map<String, Object> mapContainer = new HashMap<>();

    /**
     * Map with bean's the destruction Callback.
     */
    Map<String, List<Runnable>> mapDestructionCallback = new HashMap<>();

    /**
     * If the specified bean name (nameBean) is not already associated with a
     * bean, attempts to compute its value using the given mapping function and
     * enters it into the container unless null.
     *
     * @param nameBean the bean's name
     * @param funcAbsent the function that create the bean
     * @return the bean associated with nameBean
     */
    @Override
    public Object computeIfAbsent(String nameBean, Function<String, Object> funcAbsent) {
        return mapContainer.computeIfAbsent(nameBean, funcAbsent);
    }

    /**
     * Register a callback to be executed on destruction of job scope container.
     *
     * @param strNameBean the bean's name
     * @param mappingFunction the function to compute a value
     */
    @Override
    public void registerDestructionCallback(String strNameBean, Runnable mappingFunction) {
        this.mapDestructionCallback.computeIfAbsent(strNameBean, (s) -> new ArrayList<>()).add(mappingFunction);
    }

    /**
     * Remove the bean from JobSpring scope container
     *
     * @param strNameBean the bean's name
     * @return the object removed
     */
    @Override
    public Object remove(String strNameBean) {
        mapDestructionCallback.remove(strNameBean);
        return mapContainer.remove(strNameBean);
    }

    /**
     * close the container, call the destruction callbacks and erase the
     * references to the managered beans.
     */
    @Override
    public void close() {
        mapContainer.clear();
        mapDestructionCallback
                .entrySet().stream()
                .map(entry -> entry.getValue())
                .flatMap(list -> list.stream())
                .forEach(Runnable::run);
        mapDestructionCallback.clear();

    }
}
