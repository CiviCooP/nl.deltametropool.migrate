package nl.deltametropool.migrate;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExtendSetTask implements Tasklet {
	
	private final static Logger log = LoggerFactory.getLogger(ExtendSetTask.class.getName());
	
	@Autowired DataSource dataSource;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		boolean proceed= true;
		while(proceed){
			proceed = false; // stop directly unless a new record is added			
			if(jdbcTemplate.queryForObject(Utils.loadSQL("findParentOrg"),Integer.class)>0){
				proceed=true;
				log.info("Insert Parents");
				jdbcTemplate.execute(Utils.loadSQL("insertParentOrg"));
			}	
			if(jdbcTemplate.queryForObject(Utils.loadSQL("findEmployees"),Integer.class)>0){
				proceed=true;
				log.info("Insert Employees");
				jdbcTemplate.execute(Utils.loadSQL("insertEmployees"));
			}	
			if(jdbcTemplate.queryForObject(Utils.loadSQL("findEmployers"),Integer.class)>0){
				proceed=true;
				log.info("Insert Employers");
				jdbcTemplate.execute(Utils.loadSQL("insertEmployers"));
			}	
		}						
		return RepeatStatus.FINISHED;
	}

}
