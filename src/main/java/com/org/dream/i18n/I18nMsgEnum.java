package com.org.dream.i18n;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * The enum 18 n msg enum.
 */
@Getter
@AllArgsConstructor
public enum I18nMsgEnum {
    /**
     * The No data permission.
     */
    NO_DATA_PERMISSION("403001", "no_data_permission");

    private String code;

    private String msgApi;
}
