package br.com.receitabatch.service;

import br.com.receitabatch.exception.BusinessException;
import br.com.receitabatch.service.base.BaseService;
import br.com.receitabatch.util.FileUtils;
import br.com.receitabatch.util.MessageUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class FileProcessService extends BaseService {

    @Value("${rec-dir.rec-erro}")
    private String pathFtpRecErro;

    @Transactional
    public List<String> fileProcess(File file) {

        try {
            log.info("Recuperando lista de dados no arquivo.");
            List<String> lines = FileUtils.readAllLines(file.getPath());

            if (lines == null || lines.isEmpty()) {
                log.info("Iniciando a movimentacao do arquivo");
                log.debug(MessageUtils.MOVENDO_ARQUIVO_PAR1, file.getAbsolutePath());
                throw new BusinessException(MessageUtils.MOVENDO_ARQUIVO_PAR1, file.getAbsolutePath());
            }

            log.info("Lista de dados no arquivo recuperada.");
            log.info("Quantidade de linhas recuperadas: {}", lines.size());

            log.debug(MessageUtils.ITERANDO_LISTA);

            return listFile(lines);

        } catch (IOException ex) {
            FileUtils.handleFileException(file, pathFtpRecErro + "/" + file.getName());
            throw new BusinessException(MessageUtils.ERRO_REALIZAR_OPERACAO, ex);
        }
    }

    private List<String> listFile(List<String> lines) {
        List<String> list = new ArrayList<>();
        for (String line : lines) {
            list.add(line);
        }
        return list;
    }
}
