package com.org.dream.config;

import com.org.dream.constant.Constant;
import com.org.dream.exception.DAServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * The type App locale resolver.
 */
public class AppLocaleResolver implements LocaleResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLocaleResolver.class);

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = StringUtils.isEmpty(request.getHeader(Constant.LANG)) ? "zh-CN" : request.getHeader(Constant.LANG);
        LOGGER.info("resolveLocale lang:{}", language);
        Locale locale = Locale.getDefault();
        if (StringUtils.isNotBlank(language)) {
            if (language.indexOf("-") > 0) {
                String[] split = language.split("-");
                locale = new Locale(split[0], split[1]);
            } else if (language.indexOf("_") > 0) {
                String[] split = language.split("_");
                // 国家-地区
                locale = new Locale(split[0], split[1]);
            } else {
                throw new DAServiceException("un-support lang flag");
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
    }
}