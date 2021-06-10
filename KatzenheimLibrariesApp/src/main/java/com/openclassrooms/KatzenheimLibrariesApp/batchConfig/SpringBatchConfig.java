package com.openclassrooms.KatzenheimLibrariesApp.batchConfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Autowired private JobBuilderFactory jobBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	@Autowired private ItemReader <LibraryUser> itemReader;
	@Autowired private ItemWriter <LibraryUser> itemWriter;
	@Autowired private ItemProcessor<LibraryUser,LibraryUser> itemProcessor;
	
	@Bean
	public Job job() {
		
		Step step = stepBuilderFactory.get("step-load-data")
				.<LibraryUser,LibraryUser> chunk(100)
				.reader(itemReader)
				.writer(itemWriter)
				.processor(itemProcessor)
				.build();
				;
				
	return jobBuilderFactory.get("ETL-Load")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
		
	}
	
}
