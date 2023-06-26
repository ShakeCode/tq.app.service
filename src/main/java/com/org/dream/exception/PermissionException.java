package com.org.dream.exception;


import com.org.dream.i18n.I18nMsgEnum;
import com.org.dream.i18n.I18nUtil;

/**
 * The type Permission exception.
 */
public class PermissionException extends RuntimeException {
    private String message;

    /**
     * Instantiates a new Permission exception.
     * @param message the message
     */
    public PermissionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Permission exception.
     * @param errorMsgEnum the error msg enum
     */
    public PermissionException(final I18nMsgEnum errorMsgEnum) {
        super(I18nUtil.getMessage(errorMsgEnum));
        this.message = I18nUtil.getMessage(errorMsgEnum);
    }
}
