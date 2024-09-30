package com.dexbackend.dexbackend.pageanalyzer.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * The Class AsyncConfiguration.
 */
@Configuration
@EnableAsync
public class AsyncConfiguration 
{
    
    /** The Constant ASYNCH_THREAD. */
    private static final String ASYNCH_THREAD = "AsynchThread-";

	/**
	 * Async executor.
	 *
	 * @return the executor
	 */
	@Bean(name = "asyncExecutor")
    public Executor asyncExecutor() 
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix(ASYNCH_THREAD);
        executor.initialize();
        return executor;
    }
}