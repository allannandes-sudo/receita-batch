package br.com.receitabatch.enumeration;

import br.com.receitabatch.exception.BusinessException;
import br.com.receitabatch.job.ReceitaJob;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum ProcessEnum {

    PROCESSAR_ARQUIVO_B1("01", ReceitaJob.class);

    private String codigo;

    private Class classe;

    private ProcessEnum(String codigo, Class classe) {
        this.codigo = codigo;
        this.classe = classe;
    }

    public static ProcessEnum buscarProcessoPorCodigo(String codigo) {

        final Optional<ProcessEnum> process = Arrays.asList(ProcessEnum.values()).stream()
                .filter(p -> p.getCodigo().equals(codigo)).findFirst();

        if (!process.isPresent()) {
            throw new BusinessException("Processo Inexistente");
        }

        return process.get();
    }
}
