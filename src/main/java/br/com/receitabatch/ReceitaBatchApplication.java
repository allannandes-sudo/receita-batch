package br.com.receitabatch;

import br.com.receitabatch.enumeration.ProcessEnum;
import br.com.receitabatch.job.base.BaseJob;
import java.util.Locale;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@EntityScan(basePackages = { "br.com.receitabatch"}, basePackageClasses = {
		ReceitaBatchApplication.class, Jsr310JpaConverters.class })
@Log4j2
@SpringBootApplication
public class ReceitaBatchApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(ReceitaBatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (args.length == 0) {
			log.error("Parametros nao informados");
		}

		final BaseJob processo = getInstace(args);
		processo.run(args);

		log.info("Processamento executado com sucesso");
		SpringApplication.exit(appContext);
	}

	public BaseJob getInstace(String... args) {


		final ProcessEnum tipoProcesso = ProcessEnum.buscarProcessoPorCodigo(args[0]);

		log.info("JOB: {} - {}", tipoProcesso.getCodigo(), tipoProcesso.getClasse().getSimpleName());

		return (BaseJob) appContext.getBean(tipoProcesso.getClasse().getSimpleName().substring(0, 1)
				.toLowerCase(new Locale("pt", "BR")).concat(tipoProcesso.getClasse().getSimpleName().substring(1)));

	}

}
