
package com.org.dream.i18n;

import com.org.dream.exception.DAServiceException;
import com.org.dream.util.I18nUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The type 18 n util.
 */
@Slf4j
public class I18nUtil {
    private static final String PATH_PARENT = "classpath:i18n/";
    private static final String FILE_NAME = "locate_";
    private static final String SUFFIX = ".properties";
    private static MessageSourceAccessor accessor;
    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private I18nUtil() {
    }

    private static void initMessageSourceAccessor(String language) throws IOException {
        // 获取配置文件名
        Resource resource = resourcePatternResolver.getResource(PATH_PARENT + FILE_NAME + language + SUFFIX);
        String fileName = resource.getURL().toString();
        int lastIndex = fileName.lastIndexOf(".");
        String baseName = fileName.substring(0, lastIndex);

        // 读取配置文件
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource =
                new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename(baseName);
        reloadableResourceBundleMessageSource.setCacheSeconds(300);
        reloadableResourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        accessor = new MessageSourceAccessor(reloadableResourceBundleMessageSource);
    }

    /**
     * Gets message.
     * @param language          the language
     * @param messageApi        the message api
     * @param defaultMessageApi the default message api
     * @return the message
     */
    public static String getMessage(String language, String messageApi, String defaultMessageApi) {
        try {
            initMessageSourceAccessor(language);
        } catch (IOException e) {
            throw new DAServiceException("Fail to get I18N message");
        }
        return accessor.getMessage(messageApi, defaultMessageApi, LocaleContextHolder.getLocale());
    }

    /**
     * Gets message.
     * @param language   the language
     * @param messageApi the message api
     * @return the message
     */
    public static String getMessage(String language, String messageApi) {
        return getMessage(language, messageApi, messageApi);
    }

    /**
     * Gets message.
     * @param messageApi the message api
     * @return the message
     */
    public static String getMessage(String messageApi) {
        return getMessage(LanguageEnum.LANGUAGE_ZH_CN.getLan(), messageApi);
    }

    /**
     * Gets message.
     * @param errorMsgEnum the error msg enum
     * @return the message
     */
    public static String getMessage(I18nMsgEnum errorMsgEnum) {
        // HeaderInfo headerInfo = UserContextUtil.getHeaderInfo();
        String lang = LocaleContextHolder.getLocale().getLanguage() + "_" + LocaleContextHolder.getLocale().getCountry();
        log.info("get i18n msg:{}", I18nUtils.i18n(errorMsgEnum.getMsgApi()));
        return getMessage(lang, errorMsgEnum.getMsgApi());
    }

    /**
     * Gets message.
     * @param lang         the lang
     * @param errorMsgEnum the error msg enum
     * @return the message
     */
    public static String getMessage(String lang, I18nMsgEnum errorMsgEnum) {
        return getMessage(lang, errorMsgEnum.getMsgApi());
    }
}
