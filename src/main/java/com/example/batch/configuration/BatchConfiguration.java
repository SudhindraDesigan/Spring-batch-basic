package com.example.batch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.batch.entity.Numbers;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuildFac;
	
	@Autowired
	public StepBuilderFactory stepbuildFac;
	
	@Bean
	public FlatFileItemReader<Numbers> 	reader(){
		
		
		return new FlatFileItemReaderBuilder<Numbers>().name("NumberReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names(new String[] {"number1","number2"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Numbers>(){{
					setTargetType(Numbers.class);
				}}).build();
		
	}
	 @Bean
	    public NumberItemProcessor processor() {
	        return new NumberItemProcessor();
	    }
	 @Bean
	    public JdbcBatchItemWriter<Numbers> writer(DataSource dataSource) {
	        return new JdbcBatchItemWriterBuilder<Numbers>()
	            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	            .sql("INSERT INTO numbers (number1, number2) VALUES (:number1, :number2)")
	            .dataSource(dataSource)
	            .build();
	    }
	  @Bean
	    public Job importUserJob(JobCompleteNotifyListener listener, Step step1) {
	        return jobBuildFac.get("importUserJob")
	            .incrementer(new RunIdIncrementer())
	            .listener(listener)
	            .flow(step1)
	            .end()
	            .build();
	    }

	    @Bean
	    public Step step1(JdbcBatchItemWriter<Numbers> writer) {
	        return stepbuildFac.get("step1")
	            .<Numbers, Numbers> chunk(10)
	            .reader(reader())
	            .processor(processor())
	            .writer(writer)
	            .build();
	    }
}
