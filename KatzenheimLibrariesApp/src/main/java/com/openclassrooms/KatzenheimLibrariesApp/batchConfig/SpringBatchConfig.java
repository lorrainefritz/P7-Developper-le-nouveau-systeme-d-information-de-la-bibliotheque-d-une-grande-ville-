package com.openclassrooms.KatzenheimLibrariesApp.batchConfig;

import javax.transaction.Transactional;

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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
import com.openclassrooms.KatzenheimLibrariesApp.service.BatchProcessingService;
import com.openclassrooms.KatzenheimLibrariesApp.service.BorrowService;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchConfig {

	@Autowired BatchProcessingService batchProcessingService ;
	
	
 
  @Scheduled(cron="0 24 20 * * *")
  @Transactional
	public Job job() {		
	  batchProcessingService.batchProcessing();
	return null;
		
	}
	
}
	
	

