package br.com.receitabatch.job;

import br.com.receitabatch.exception.BusinessException;
import br.com.receitabatch.job.base.BaseJob;
import br.com.receitabatch.service.FileProcessService;
import br.com.receitabatch.service.GenerateFileService;
import br.com.receitabatch.service.ReceitaService;
import br.com.receitabatch.util.FileUtils;
import br.com.receitabatch.util.MessageUtils;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Log4j2
public class ReceitaJob extends BaseJob {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private FileProcessService fileProcessService;

    @Autowired
    private GenerateFileService generateFileService;

    @Value("${receita.sec.dependencias.name.file-in}")
    private String name;

    public ReceitaJob() {
    }

    @Override
    public void run(String... args) {
        LocalDate referenceDate = LocalDate.now();
        try {
            if (args.length > 1) {

                if (Arrays.asList(args).get(1).equals(name)) {
                    log.info("Data de referencia: {}", referenceDate);
                    log.info("Recuperando o arquivo com as informacoes.");
                    final File[] files = FileUtils.findFile(getPathFtpRecIn(), name);
                    log.debug(MessageUtils.ARQUIVOS_ENCONTRADOS_PAR1, files.length);
                    for (File file : files) {
                        List<String> lists = fileProcess(file);
                        List<String> listCvs = new ArrayList<String>();
                        listCvs.add(lists.get(0).concat(";resultado"));
                        String[] fileCsv;
                        for (String list : lists) {
                            if (!list.equals(lists.get(0))) {
                                fileCsv = list.split(";");
                              Boolean result =  receitaService.atualizarConta(fileCsv[0], fileCsv[1].replace("-",""), Double.parseDouble(fileCsv[2].replace(",", ".")), fileCsv[3]);
                                listCvs.add(list.concat(";".concat(result.toString())));
                            }
                        }
                        log.info(listCvs);
                        generateFileService.generateFile(listCvs,file);
                    }
                } else {
                    log.error("Nome de arquivo invalido");
                }
            }
        } catch (BusinessException | InterruptedException | IOException e) {
            log.info("Ocorreu um erro ao processar", e);
            throw new BusinessException("Erro no processamento.", e);
        }
    }

    private List<String> fileProcess(File file) {
        try {
            return fileProcessService.fileProcess(file);
        } catch (BusinessException ex) {
            log.info("Erro no arquivo processado", ex);
            handleFileException(file, ex);
            throw new BusinessException("Erro no processamento.", ex);
        }

    }

}
