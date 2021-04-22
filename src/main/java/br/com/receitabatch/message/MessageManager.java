package br.com.receitabatch.message;


import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class MessageManager {

    private static final ResourceBundle resourceMessage = ResourceBundle
            .getBundle("message");

    private MessageManager() {
        // construtor vazio
    }

    public static String getMessage(String key, Object... args) {
        String text;

        try {
            text = resourceMessage.getString(key);
        } catch (ClassCastException | MissingResourceException ex) {
            log.error("Erro messege: ",ex);
            log.info("Erro messege: ",ex);
            text = key;
        }

        if ((args != null) && (args.length > 0)) {
            text = MessageFormat.format(text, args);
        }
        log.info(text);

        return text;
    }

}
