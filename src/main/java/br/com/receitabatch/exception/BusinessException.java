package br.com.receitabatch.exception;


import br.com.receitabatch.message.MessageManager;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Log4j2
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String message;
    private HttpStatus httpStatus;

    public BusinessException(HttpStatus http, String message, Throwable thrwbl) {
        super(message, thrwbl);
        BusinessException.log.error(thrwbl.getMessage(), thrwbl);
        this.message = message;
        this.httpStatus = http;
    }

    public BusinessException(HttpStatus http, String message, Object... args) {
        super(String.format(message, args));
        this.message = String.format(message, args);
        this.httpStatus = http;
    }

    public BusinessException(String message) {
        super(message);
        this.message = MessageManager.getMessage(message);
        log.error(this.message);
    }

    public BusinessException(String string, Throwable thrwbl) {
        super(string, thrwbl);
        this.message = MessageManager.getMessage(string);
        log.error(this.message);
        String error = thrwbl.toString();
        log.error(error);

    }

    public BusinessException(String key, Object... args) {
        super(MessageManager.getMessage(key, args));
        this.message = MessageManager.getMessage(key, args);
        log.error(this.message);

    }

    public static IntermediateContext assertThat(boolean condition) {
        return new IntermediateContext(condition);
    }

    public static IntermediateContext assertNotNull(Object object) {
        return new IntermediateContext(object != null);
    }

    public static IntermediateContext assertNull(Object object) {
        return new IntermediateContext(object == null);
    }

    public static IntermediateContext assertNotEmpty(String string) {
        return new IntermediateContext(string != null && !string.isEmpty());
    }

    public static <T> IntermediateContext assertNotEmpty(Collection<T> collection) {
        return new IntermediateContext(collection != null && !collection.isEmpty());
    }

    public static class IntermediateContext {

        private final boolean assertIsFalse;

        public IntermediateContext(boolean condition) {
            this.assertIsFalse = !condition;
        }

        public void orRegistrer(String mensage, Object... parametros) {
            if (assertIsFalse) {
                throw new BusinessException(mensage, parametros);
            }
        }
    }

}
