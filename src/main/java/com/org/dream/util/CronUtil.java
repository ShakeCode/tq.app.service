package com.org.dream.util;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;

/**
 * 功能描述 时间工具类
 */
public class CronUtil {
    /**
     * 获取cron表达式的间隔时间
     * @param cron cron表达式字符串
     * @return 间隔时间（s）
     */
    public static int getIntervalTime(String cron) {
        CronSequenceGenerator generator = new CronSequenceGenerator(cron);
        Date next = generator.next(new Date());
        Date nextNext = generator.next(next);
        long interval = nextNext.getTime() - next.getTime();
        return (int) interval / 1000;
    }
}
