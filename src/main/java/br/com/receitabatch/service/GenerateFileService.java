package br.com.receitabatch.service;

import br.com.receitabatch.exception.BusinessException;
import br.com.receitabatch.service.base.BaseService;
import br.com.receitabatch.util.FileUtils;
import br.com.receitabatch.util.MessageUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class GenerateFileService extends BaseService {


    @Value("${receita.sec.dependencias.name.file-out}")
    private String nameOut;

    @Transactional
    public void generateFile(List<String> listCvs, File files) throws IOException {

        File file = FileUtils.getNewFile(getPathFtpRecOut(), nameOut);

        if (!file.getParentFile().exists()) {
            throw new BusinessException(String.format(MessageUtils.CAMINHO_NAO_ENCONTRADO_PAR1, file.getPath()));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {


            for (String rowData : listCvs) {
                writer.append(String.join(",", rowData));
                writer.append("\n");
            }

            writer.flush();
            writer.close();

        } catch (Exception e) {
            throw new BusinessException("Erro no processamento.", e);
        }
        log.info("Iniciando a movimentacao do arquivo BANCOS");
        log.debug(MessageUtils.MOVENDO_ARQUIVO_PAR1, files.getAbsolutePath());

        FileUtils.moveAndReplaceFile(files.getAbsolutePath(), getPathFtpRecProcess() + files.getName());
        log.debug(MessageUtils.ARQUIVO_MOVIDO);
    }
}

