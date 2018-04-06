package com.example.batch.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompleteNotifyListener implements JobExecutionListener {

	
	private static final Logger log = LoggerFactory.getLogger(JobCompleteNotifyListener.class);

	private final JdbcTemplate jdbcTemplate;

@Autowired
	public JobCompleteNotifyListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		log.info("After Job:"+jobExecution.getStatus());;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
	log.info("Before Job:"+jobExecution.getStatus());
	}
	
	

	
}
