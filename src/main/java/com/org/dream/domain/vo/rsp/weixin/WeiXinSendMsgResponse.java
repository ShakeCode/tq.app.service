package com.org.dream.domain.vo.rsp.weixin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Wei xin send msg response.
 */
@ApiModel(value = "WeiXinSendMsgResponse", description = "微信发送消息响应结果")
@NoArgsConstructor
@Data
public class WeiXinSendMsgResponse {
    @ApiModelProperty("响应码")
    private Integer errcode;

    @ApiModelProperty("响应码的解释")
    private String errmsg;

    @ApiModelProperty("消息ID,用于撤回消息")
    private String msgid;
}
