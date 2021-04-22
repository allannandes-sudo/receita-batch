package br.com.receitabatch.config;


import br.com.receitabatch.job.ReceitaJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobFactoryConfig {
	
	@Bean
	public ReceitaJob receitaJob() {
		return new ReceitaJob();

	}

}
