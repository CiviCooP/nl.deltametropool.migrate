package nl.deltametropool.migrate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("nl.deltametropool.migrate")
@EnableAutoConfiguration
@EnableBatchProcessing
public class RunIt {
	

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(RunIt.class, args);
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job job = (Job) ctx.getBean("loadMetropool");
		Map <String,JobParameter> parMap = new HashMap <String,JobParameter>();
	    parMap.put("timestamp", new JobParameter(new Date()));
	    JobParameters pars = new JobParameters(parMap);
	    jobLauncher.run(job, pars);
		ctx.close();
	}

}
