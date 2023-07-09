package com.org.dream.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * The type Wei xin common config.
 */
@RefreshScope
@ApiModel(value = "WeiXinCommonConfig", description = "企业微信应用配置参数信息")
@Component
@Data
@ConfigurationProperties(prefix = "wei-xin")
public class WeiXinCommonConfig {
    @Value("${wei-xin.token.url:https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}}")
    private String tokenUrl;

    @Value("${wei-xin.send-msg.url:https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={0}}")
    private String sendMsgUrl;

    @ApiModelProperty("应用ID")
    private String appId = "ww27117c0555d72370";

    @ApiModelProperty("应用密钥")
    private String appSecret = "dFb8Nv8GTLf74ozsakPedvWEPEaiFWIfb5mGE31xsRs";
}
