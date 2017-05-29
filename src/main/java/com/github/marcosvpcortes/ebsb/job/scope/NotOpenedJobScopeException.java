/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.marcosvpcortes.ebsb.job.scope;

/**
 * Exception throwed when cannot create a Job Scope context because there is not 
 * a JobSpring building or there is not a JobSpring running.
 * @author marcosvpcortes
 */
public class NotOpenedJobScopeException extends RuntimeException {

    public NotOpenedJobScopeException() {
        super("Cannot use JobScope because there is not builder running or a JobSpring running");
    }
    
}
