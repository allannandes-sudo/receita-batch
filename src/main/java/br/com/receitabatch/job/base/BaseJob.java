package br.com.receitabatch.job.base;

import br.com.receitabatch.base.BaseBatch;
import br.com.receitabatch.exception.BusinessException;
import br.com.receitabatch.util.FileUtils;
import br.com.receitabatch.util.MessageUtils;
import java.io.File;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BaseJob extends BaseBatch {

    public abstract void run(String... args);

    protected void handleFileException(File file, Exception ex) {
        try {
            log.error(MessageUtils.ERRO_PROCESSAR_ARQUIVO_PAR1, file.getAbsolutePath());
            log.error(ex.getMessage());
            FileUtils.moveAndReplaceFile(file.getAbsolutePath(),
                    getPathFtpRecErro() + file.getName());
        } catch (IOException e) {
            log.error(MessageUtils.ERRO_AO_TENTAR_MOVER_ARQUIVO_PAR1, file.getAbsolutePath());
            throw new BusinessException("Erro ao mover o arquivo",e);
        }
    }
}

