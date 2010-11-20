package com.loja.jobs;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalCron;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.log.Log;

@Name("processor")
@AutoCreate
@Scope(ScopeType.APPLICATION)
public class ScheduleProcessor {

    @Logger
    private Log log;

    @Asynchronous
    @Transactional
    public QuartzTriggerHandle createQuartzTestTimer(
                @Expiration Date when, @IntervalCron String interval) {

    	String date = new Date().toString();
    	log.info( "Quartz Test: " + date );
    	System.out.println("Quartz Test: " + date );
    	return null;
    }
}
