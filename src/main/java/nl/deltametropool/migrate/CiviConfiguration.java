package nl.deltametropool.migrate;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


@Configuration
public class CiviConfiguration {
	
	@Autowired CiviBatchProperties civiBatchProperties;
	@Autowired private JobBuilderFactory  jobBuilderFactory; 
	@Autowired private StepBuilderFactory stepBuilderFactory;
	
	@Autowired  OrgProcessor orgProcessor;
	@Autowired  OrgVisitAddressProcessor orgVisitAddressProcessor;
	@Autowired  OrgPostAddressProcessor orgPostAddressProcessor ;
	@Autowired  OrgEmailProcessor orgEmailProcessor;
	@Autowired  OrgTel1Processor  orgTel1Processor;
	@Autowired  OrgTel2Processor  orgTel2Processor;
	
	@Autowired PersProcessor persProcessor;
	@Autowired PersEmailProcessor persEmailProcessor;
	@Autowired PersTel1Processor persTel1Processor;
	@Autowired PersTel2Processor persTel2Processor;
	@Autowired PersTel3Processor persTel3Processor;
	@Autowired WebsiteProcessor  websiteProcessor;
	@Autowired PersPriveAddressProcessor persPriveAddressProcessor;
	@Autowired CommentProcessor commentProcessor;
	@Autowired TitleProcessor titleProcessor;
	
	@Autowired AfdelingProcessor afdelingProcessor;
	
	@Autowired OrgHierarchyProcessor orgHierarchyProcessor;
	@Autowired PrivatePicksProcessor privatePicksProcessor;
	@Autowired ActiveEmailProcessor  activeEmailProcessor;
	@Autowired ActivePhoneProcessor  activePhoneProcessor;
	@Autowired SecondActiveEmailProcessor secondActiveEmailProcessor;
	@Autowired EmployerProcessor     employerProcessor;
	
	@Autowired PersHierarchyProcessor persHierarchyProcessor;
	@Autowired RelationShipCommentProcessor relationShipCommentProcessor;
	@Autowired FirstActivityProcessor firstActivityProcessor;
	
	@Autowired ExtendSetTask         extendTasklet;
	
	
	@Bean public DataSource dataSource()
	{
		SingleConnectionDataSource  dataSource = new SingleConnectionDataSource ();		
		dataSource.setUrl(civiBatchProperties.getDbUrl());
		dataSource.setUsername(civiBatchProperties.dbUser);
		dataSource.setPassword(civiBatchProperties.dbPassword);		
		dataSource.setSuppressClose(true);
		return dataSource;						
	}
	
	@Bean ItemReader<String> orgReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectOrg"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> orgHierarchyReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectOrgHierarchy"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> persHierarchyReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectPersHierarchy"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> persReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectPers"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> afdelingReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectAfdeling"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> privatePicksReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("privatepicks"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> activeRelReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectActiveRel"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> titleReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectTitles"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	@Bean ItemReader<String> firstActivtyReader(DataSource dataSource) throws Exception{
		JdbcCursorItemReader <String> reader = new JdbcCursorItemReader <String>(); 
		reader.setDataSource(dataSource);
		reader.setSql(Utils.loadSQL("selectFirstActivity"));	
		reader.setRowMapper(new BatchRowMapper());
		return reader;
	}
	
	
	@Bean ItemWriter<String> logWriter(){
		FlatFileItemWriter <String> writer = new  FlatFileItemWriter <String>();
		writer.setResource(new FileSystemResource("metropool.log"));
		writer.setLineAggregator(new PassThroughLineAggregator <String> ());
		writer.setAppendAllowed(true);
		return writer;		
	}
	
	@Bean ItemProcessor<String,String> orgDetailProcessor(){
		List <ItemProcessor<String,String>>  delegates = new ArrayList  <ItemProcessor<String,String>>();
		delegates.add(orgProcessor);
		delegates.add(orgVisitAddressProcessor);
		delegates.add(orgPostAddressProcessor);
		delegates.add(orgEmailProcessor);
		delegates.add(orgTel1Processor);
		delegates.add(orgTel2Processor);
		delegates.add(websiteProcessor);
		delegates.add(commentProcessor);
		CompositeItemProcessor<String,String> processor = new CompositeItemProcessor<String,String>();
		processor.setDelegates(delegates);
		return processor;		
	}
	
	@Bean ItemProcessor<String,String> persDetailProcessor(){
		List <ItemProcessor<String,String>>  delegates = new ArrayList  <ItemProcessor<String,String>>();
		delegates.add(persProcessor);
		delegates.add(persEmailProcessor);
		delegates.add(persTel1Processor);
		delegates.add(persTel2Processor);
		delegates.add(persTel3Processor);
		delegates.add(websiteProcessor);
		delegates.add(persPriveAddressProcessor);
		delegates.add(commentProcessor);
		CompositeItemProcessor<String,String> processor = new CompositeItemProcessor<String,String>();
		processor.setDelegates(delegates);
		return processor;		
	}
	
	@Bean ItemProcessor<String,String> activeRelProcessor(){
		List <ItemProcessor<String,String>>  delegates = new ArrayList  <ItemProcessor<String,String>>();
	    delegates.add(activeEmailProcessor);
	    delegates.add(secondActiveEmailProcessor);
		delegates.add(activePhoneProcessor);
		delegates.add(employerProcessor);
		CompositeItemProcessor<String,String> processor = new CompositeItemProcessor<String,String>();
		processor.setDelegates(delegates);
		return processor;		
	}
	
	@Bean ItemProcessor<String,String> persHierarchyDetailProcessor(){
		List <ItemProcessor<String,String>>  delegates = new ArrayList  <ItemProcessor<String,String>>();
	    delegates.add(persHierarchyProcessor);
		delegates.add(relationShipCommentProcessor);		
		CompositeItemProcessor<String,String> processor = new CompositeItemProcessor<String,String>();
		processor.setDelegates(delegates);
		return processor;		
	}
	
	
	
	@Bean ItemProcessor<String,String> afdelingDetailProcessor(){
		List <ItemProcessor<String,String>>  delegates = new ArrayList  <ItemProcessor<String,String>>();
		delegates.add(afdelingProcessor);
		delegates.add(persEmailProcessor);
		delegates.add(persTel1Processor);
		delegates.add(persTel2Processor);
		delegates.add(persTel3Processor);
		delegates.add(persPriveAddressProcessor);
		delegates.add(commentProcessor);
		CompositeItemProcessor<String,String> processor = new CompositeItemProcessor<String,String>();
		processor.setDelegates(delegates);
		return processor;		
	}
	
	@Bean Step orgStep(ItemReader<String> orgReader
                      ,ItemWriter<String> logWriter
                      ,ItemProcessor<String,String> orgDetailProcessor){
            SimpleStepBuilder<String,String> stepBuilder = stepBuilderFactory.get("orgsss")
           .chunk(100);
            
          return stepBuilder.reader(orgReader)
          .processor(orgDetailProcessor)
          .writer(logWriter)
          .build();
	}
	
	@Bean Step persStep(ItemReader<String> persReader
			           ,ItemWriter<String> logWriter
			           ,ItemProcessor<String,String> persDetailProcessor) {
		SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("pers").chunk(10);

		return stepBuilder.reader(persReader).processor(persDetailProcessor).writer(logWriter).build();
	}
	
	@Bean Step afdelingStep(ItemReader<String> afdelingReader
	           ,ItemWriter<String> logWriter
	           ,ItemProcessor<String,String> afdelingDetailProcessor) {
               SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("afdeling").chunk(10);

              return stepBuilder.reader(afdelingReader).processor(afdelingDetailProcessor).writer(logWriter).build();
    }
	
	@Bean Step orgHierarchyStep(ItemReader<String> orgHierarchyReader
			                   ,ItemWriter<String> logWriter
			                   ,ItemProcessor<String,String> orgHierarchyProcessor){
		SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("orgHierarchy").chunk(10);
		return stepBuilder.reader(orgHierarchyReader)
				          .processor(orgHierarchyProcessor)
				          .writer(logWriter).build();
	}
	
	@Bean
	Step persHierarchyStep(ItemReader<String> persHierarchyReader, ItemWriter<String> logWriter,
			ItemProcessor<String, String> persHierarchyDetailProcessor) {
		SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("persHierarchyDetailProcessor").chunk(10);
		return stepBuilder.reader(persHierarchyReader).processor(persHierarchyDetailProcessor).writer(logWriter).build();
	}
	
	@Bean
	Step privatePicksStep(ItemReader<String> privatePicksReader, ItemWriter<String> logWriter,
			ItemProcessor<String, String> privatePicksProcessor) {
		SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("privatePicksStep").chunk(10);
		return stepBuilder.reader(privatePicksReader).processor(privatePicksProcessor).writer(logWriter).build();
	}
	
	@Bean
	Step activeRelStep(ItemReader<String> activeRelReader, ItemWriter<String> logWriter,
			ItemProcessor<String, String> activeRelProcessor) {
		SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("activeRelStep").chunk(10);
		return stepBuilder.reader(activeRelReader).processor(activeRelProcessor).writer(logWriter).build();
	}
	
	@Bean
	Step titleStep(ItemReader<String> titleReader, ItemWriter<String> logWriter,
			ItemProcessor<String, String> titleProcessor) {
		SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("titlesStep").chunk(10);
		return stepBuilder.reader(titleReader).processor(titleProcessor).writer(logWriter).build();
	}
	
	@Bean
	Step firstActivityStep(ItemReader<String> firstActivtyReader, ItemWriter<String> logWriter,
			ItemProcessor<String, String> firstActivityProcessor) {
		SimpleStepBuilder<String, String> stepBuilder = stepBuilderFactory.get("titlesStep").chunk(10);
		return stepBuilder.reader(firstActivtyReader).processor(firstActivityProcessor).writer(logWriter).build();
	}
          
    @Bean public Job loadMetropool(Step orgStep
    		                      ,Step persStep
    		                      ,Step afdelingStep
    		                      ,Step orgHierarchyStep
    		                      ,Step persHierarchyStep
    		                      ,Step privatePicksStep
    		                      ,Step activeRelStep
    		                      ,Step firstActivityStep){		
     		 return jobBuilderFactory.get("loadMetropool")
     				    .incrementer(new RunIdIncrementer())
     				    .start(afdelingStep)
     				    .next(persStep)
     				    .next(orgStep)
     				    .next(orgHierarchyStep)
     				    .next(persHierarchyStep)
     				    .next(privatePicksStep)
     				    .next(activeRelStep)
     				    .next(firstActivityStep)
     				    .build();		
    } 
    
	@Bean
	public Job testMetropool(Step activeRelStep) {
		return jobBuilderFactory.get("firstActivityStep").incrementer(new RunIdIncrementer())

				.start(activeRelStep).build();
	}
	
	@Bean
	public Job createSubSet(){
		
		return jobBuilderFactory.get("createSubset").incrementer(new RunIdIncrementer())
				.start(stepBuilderFactory.get("createDrools").tasklet(extendTasklet).build()).build();
		
	}


}
