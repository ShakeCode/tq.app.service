package com.org.dream.service;

import com.org.dream.domain.vo.req.weixin.WeiXinSendMsgParam;
import com.org.dream.domain.vo.rsp.weixin.AccessTokenResponse;

/**
 * The interface Wei xin service.
 */
public interface WeiXinService {
    /**
     * Gets token.获取访问令牌
     * @param corpId     the corp id     企业ID
     * @param corpSecret the corp secret   应用密钥
     * @return the token
     */
    AccessTokenResponse getToken(String corpId, String corpSecret);

    /**
     * Send app msg. 发送文本消息
     * @param weiXinSendMsgParam the wei xin send msg param
     * @return the boolean
     */
    Boolean sendAppMsg(WeiXinSendMsgParam weiXinSendMsgParam);
}
