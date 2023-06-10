package com.org.dream.job;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.org.dream.domain.entity.UptownEntity;
import com.org.dream.domain.vo.req.PageVO;
import com.org.dream.service.UptownService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The type Sync uptown job.
 */
@RequiredArgsConstructor
@Component
public class SyncUptownJob {
    private static Logger logger = LoggerFactory.getLogger(SyncUptownJob.class);

    private final UptownService uptownService;

    /**
     * Sync uptown job handler return t.
     * @return the return t
     */
    @XxlJob("syncUptownJobHandler")
    public ReturnT<String> syncUptownJobHandler() {
        logger.info("syncUptownJobHandler执行器开始执行,param：{}", XxlJobHelper.getJobParam());
        try {
            // 业务方法如果异常信息记录在数据库中xxl_job_log
            PageInfo<UptownEntity> pageInfo = uptownService.getUptown(PageVO.builder().pageIndex(1).pageSize(2).build());
            logger.info("查询小区结果:{}", JSON.toJSON(pageInfo.getList()));
            // 写日志到调度中心日志中
            XxlJobHelper.log("syncUptownJobHandler execute Success...");
            // 设置任务结果
            XxlJobHelper.handleSuccess("小区同步成功");
        } catch (Exception e) {
            //写日志到调度中心日志中
            XxlJobHelper.log("syncUptownJobHandler execute Fail...", e);
            // 设置任务结果
            XxlJobHelper.handleFail();
        }
        return ReturnT.SUCCESS;
    }
}
