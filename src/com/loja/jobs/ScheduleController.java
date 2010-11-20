package com.loja.jobs;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.log.Log;

@Name("controller")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@Startup
public class ScheduleController implements Serializable {

	private static final long serialVersionUID = 7609983147081676186L;

	@In
	ScheduleProcessor processor;

	@Logger
	Log log;

	@SuppressWarnings("unused")
	private QuartzTriggerHandle quartzTestTriggerHandle;
	@SuppressWarnings("unused")
	private static String CRON_INTERVAL = "0 * * * * ?";

	@Create
	public void scheduleTimer() {
//		quartzTestTriggerHandle = processor.createQuartzTestTimer(new Date(),CRON_INTERVAL);
	}
}