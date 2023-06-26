package com.org.dream.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * The type 18 n utils.
 */
@Component
@Slf4j
public class I18nUtils {

    private static MessageSource messageSource;

    /**
     * Sets message source.
     * @param messageSource the message source
     */
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        I18nUtils.messageSource = messageSource;
    }

    /**
     * 18 n string.
     * @param code the code
     * @return the string
     */
    public static String i18n(String code) {
        return messageSource.getMessage(code, new Object[]{}, LocaleContextHolder.getLocale());
    }

    /**
     * 18 n string.
     * @param code the code
     * @param args the args
     * @return the string
     */
    public static String i18n(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * 18 n or default string.
     * @param code           the code
     * @param defaultMessage the default message
     * @param args           the args
     * @return the string
     */
    public static String i18nOrDefault(String code, String defaultMessage, Object... args) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * Try i 18 n string.
     * @param source the source
     * @param args   the args
     * @return the string
     */
    @NonNull
    public static String tryI18n(@NonNull String source, @NonNull Object... args) {
        String res;
        try {
            res = i18n(source, args);
        } catch (Exception ignored) {
            res = source;
        }
        return res;
    }
}
