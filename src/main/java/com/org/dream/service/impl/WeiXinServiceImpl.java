package com.org.dream.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.org.dream.config.WeiXinCommonConfig;
import com.org.dream.domain.vo.req.weixin.WeiXinSendMsgParam;
import com.org.dream.domain.vo.rsp.weixin.AccessTokenResponse;
import com.org.dream.domain.vo.rsp.weixin.WeiXinSendMsgResponse;
import com.org.dream.exception.ServiceException;
import com.org.dream.service.WeiXinService;
import com.org.dream.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * The type Wei xin service.
 */
@RequiredArgsConstructor
@Service
public class WeiXinServiceImpl implements WeiXinService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinServiceImpl.class);

    private final WeiXinCommonConfig weiXinCommonConfig;

    @Override
    public AccessTokenResponse getToken(String corpId, String corpSecret) {
        LOGGER.info("get token, corpId: {}, corpSecret: {}", corpId, corpSecret);
        String tokenUrl = MessageFormat.format(weiXinCommonConfig.getTokenUrl(), corpId, corpSecret);
        // ResponseEntity<AccessTokenResponse> response = RestTemplateUtil.get(tokenUrl, AccessTokenResponse.class);
        // ResponseEntity<String> response = RestTemplateUtil.get(tokenUrl, String.class);
        String response = null;
        try {
            // AccessTokenResponse accessTokenResponse = RestTemplateUtil.getForObject(tokenUrl, AccessTokenResponse.class);
            response = RestTemplateUtil.getForObject(tokenUrl, String.class);
            AccessTokenResponse accessTokenResponse = JSONObject.parseObject(response, AccessTokenResponse.class);
            if (Objects.isNull(accessTokenResponse) || accessTokenResponse.getErrcode() != 0) {
                LOGGER.error("获取企业微信token异常, response:{}", response);
                throw new ServiceException("获取企业微信token异常");
            }
            return accessTokenResponse;
        } catch (Exception e) {
            LOGGER.error("获取企业微信token失败, response body:{}", response);
            throw new ServiceException("获取企业微信token失败");
        }
    }

    @Override
    public Boolean sendAppMsg(WeiXinSendMsgParam weiXinSendMsgParam) {
        LOGGER.info("send wei-xin App Msg, weiXinSendMsgParam: {}", JSON.toJSONString(weiXinSendMsgParam));
        AccessTokenResponse accessTokenResponse = this.getToken(weiXinCommonConfig.getAppId(), weiXinCommonConfig.getAppSecret());
        LOGGER.info("get wei-xin access token:{}", JSON.toJSONString(accessTokenResponse));
        String sendMsgUrl = MessageFormat.format(weiXinCommonConfig.getSendMsgUrl(), accessTokenResponse.getAccessToken());
        LOGGER.info("get wei-xin sendMsgUrl:{}", sendMsgUrl);
        String result = "";
        try {
            result = RestTemplateUtil.postForObject(sendMsgUrl, weiXinSendMsgParam, String.class);
            WeiXinSendMsgResponse weiXinSendMsgResponse = JSONObject.parseObject(result, WeiXinSendMsgResponse.class);
            /*ResponseEntity<String> response = RestTemplateUtil.post(sendMsgUrl, weiXinSendMsgParam, String.class);
            JSONObject sendResult = JSONObject.parseObject(response.getBody());*/
            if (Objects.isNull(weiXinSendMsgResponse) || weiXinSendMsgResponse.getErrcode() != 0) {
                LOGGER.error("发送企业微信应用信息异常, response body:{}", result);
                throw new ServiceException("发送企业微信应用信息异常");
            }
        } catch (RestClientException | JSONException e) {
            LOGGER.error("发送企业微信应用信息失败, response body:{}", result);
            throw new ServiceException("发送企业微信应用信息失败");
        }
        LOGGER.info("send wei-xin message success");
        return Boolean.TRUE;
    }
}
