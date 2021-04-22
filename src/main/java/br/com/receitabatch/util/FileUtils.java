package br.com.receitabatch.util;

import br.com.receitabatch.customclasses.CustomFilenameFilter;
import br.com.receitabatch.exception.BusinessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;


@Log4j2
public final class FileUtils {

    private FileUtils() {
        throw new BusinessException("Classe utilitaria");
    }

    public static File[] findFile(String path, String pattern) {

        log.info("Buscando diretorio {} ", path);
        File diretorio = new File(FilenameUtils.getFullPath(path));
        if (!diretorio.exists()) {
            throw new BusinessException(String.format(MessageUtils.CAMINHO_NAO_ENCONTRADO_PAR1, path));
        }
        log.info("Buscando arquivos com o padrao {} no diretorio {} ", pattern, path);
        File[] arquivos = diretorio.listFiles(new CustomFilenameFilter(pattern));
        log.debug(MessageUtils.ARQUIVOS_ENCONTRADOS_PAR1, arquivos.length);
        return arquivos;
    }

    public static File getNewFile(String fullPath, String fileName) {
        log.info("Instanciando um novo arquivo {} no diretorio {}", fileName, fullPath);
        return new File(FilenameUtils.getFullPath(fullPath), FilenameUtils.getName(fileName));
    }


    public static void moveAndReplaceFile(String source, String target) throws IOException {
        log.info("Iniciando a movimentacao do arquivo");
        log.info("Movendo o arquivo {} para o diretorio {}.", source, target);
        log.warn("O arquivo no diretorio destino sera sobrescrito");
        Files.move(Paths.get(source), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
        log.info("Arquivo {} movido para o diretorio {}", source, target);
    }

    public static List<String> readAllLines(String path) throws IOException {
        log.info("Lendo todas as linhas do arquivo {}", path);
        return Files.readAllLines(Paths.get(path));
    }


    public static void handleFileException(File file, String target) {

        try {
            log.error(MessageUtils.ERRO_PROCESSAR_ARQUIVO_PAR1, file.getAbsolutePath());
            FileUtils.moveAndReplaceFile(file.getAbsolutePath(),target);
        } catch (IOException e) {
            log.error(MessageUtils.ERRO_AO_TENTAR_MOVER_ARQUIVO_PAR1, file.getAbsolutePath());
            throw new BusinessException ("Erro ao mover o arquivo",e);
        }
    }

}