package com.org.dream.controller;

import com.org.dream.domain.vo.req.weixin.WeiXinSendMsgParam;
import com.org.dream.domain.vo.rsp.ResultVO;
import com.org.dream.domain.vo.rsp.weixin.AccessTokenResponse;
import com.org.dream.service.WeiXinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Wei xin controller.
 */
@RefreshScope
@RequiredArgsConstructor
@RequestMapping("/v1/weixin/")
@RestController
@Api(value = "企业微信应用消息推送", tags = "企业微信应用消息推送")
public class WeiXinController {

    private final WeiXinService weiXinService;

    /**
     * Gets token.
     * @param corpId     the corp id
     * @param corpSecret the corp secret
     * @return the token
     */
    @ApiOperation(value = "获取微信应用token")
    @GetMapping("/access-token/get")
    public ResultVO<AccessTokenResponse> getToken(
            @ApiParam(value = "企业ID", name = "corpId")
            @RequestParam(value = "corpId", required = false, defaultValue = "ww27117c0555d72370") String corpId,
            @ApiParam(value = "应用密钥", name = "corpSecret")
            @RequestParam(value = "corpSecret", required = false, defaultValue = "dFb8Nv8GTLf74ozsakPedvWEPEaiFWIfb5mGE31xsRs") String corpSecret) {
        return ResultVO.successData(weiXinService.getToken(corpId, corpSecret));
    }

    /**
     * Send app msg result vo.
     * @param weiXinSendMsgParam the wei xin send msg param
     * @return the result vo
     */
    @ApiOperation(value = "发送微信应用消息")
    @PostMapping("/msg/send")
    public ResultVO<Boolean> sendAppMsg(@RequestBody WeiXinSendMsgParam weiXinSendMsgParam) {
        return ResultVO.successData(weiXinService.sendAppMsg(weiXinSendMsgParam));
    }
}