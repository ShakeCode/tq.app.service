package com.org.dream.controller;

import com.org.dream.domain.vo.rsp.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Gets config.
     * @return the config
     */
    @ApiOperation(value = "实时热获取配置")
    @GetMapping("/config")
    public ResultVO<String> getConfig() {
        return ResultVO.success(appVersion);
    }
}
