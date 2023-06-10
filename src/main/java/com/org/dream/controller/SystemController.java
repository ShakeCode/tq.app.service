package com.org.dream.controller;

import com.google.common.collect.Maps;
import com.org.dream.domain.vo.rsp.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * The type System controller.
 */
@RefreshScope
@RequiredArgsConstructor
@RequestMapping("/v1/system")
@RestController
@Api(value = "系统配置", tags = "系统配置")
public class SystemController {
    @Value("${appVersion:V1.0}")
    private String appVersion;

    @Value("${tenant-code:V1.0}")
    private String tenantCode;

    @Value("${spring.redis.password:111111}")
    private String redisPassword;

    /**
     * Gets config.
     * @return the config
     */
    @ApiOperation(value = "实时热获取配置")
    @GetMapping("/config")
    public ResultVO<HashMap<String, Object>> getConfig() {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("appVersion", appVersion);
        map.put("tenantCode", tenantCode);
        map.put("redisPassword", redisPassword);
        return ResultVO.successData(map);
    }
}
