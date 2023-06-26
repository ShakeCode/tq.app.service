package com.org.dream.i18n;

import org.apache.commons.lang3.StringUtils;

/**
 * The enum Language enum.
 */
public enum LanguageEnum {
    /**
     * Language en us language enum.
     */
    LANGUAGE_EN_US("en-US"),
    /**
     * Language zh cn language enum.
     */
    LANGUAGE_ZH_CN("zh-CN");

    private String language;

    LanguageEnum(String language) {
        this.language = language;
    }

    /**
     * Gets language type.
     * @param language the language
     * @return the language type
     */
    public static String getLanguageType(String language) {
        if (StringUtils.isEmpty(language)) {
            return LANGUAGE_ZH_CN.language;
        }
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            if (languageEnum.language.equalsIgnoreCase(language)) {
                return languageEnum.language;
            }
        }
        return LANGUAGE_ZH_CN.language;
    }

    /**
     * Gets lan.
     * @return the lan
     */
    public String getLan() {
        return language;
    }
}
