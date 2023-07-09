package com.org.dream.domain.vo.req.weixin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Wei xin send msg param.
 */
@Builder
@ApiModel(value = "WeiXinSendMsgParam", description = "发送微信文本消息参数实体")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeiXinSendMsgParam {
    @ApiModelProperty(value = "成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送", example = "UserID1|UserID2|UserID3")
    private String touser;

    @Builder.Default
    @ApiModelProperty(value = "消息类型", example = "text")
    private String msgtype = "text";

    @Builder.Default
    @ApiModelProperty("企业应用的id")
    private Integer agentid = 1000004;

    @ApiModelProperty("文本消息")
    private TextDTO text;

    @ApiModelProperty("表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0")
    private Integer safe = 0;

    /**
     * The type Text dto.
     */
    @NoArgsConstructor
    @Data
    public static class TextDTO {
        @ApiModelProperty(value = "文本内容", example = "消息内容，最长不超过2048个字节，超过将截断（支持id转译）")
        private String content;
    }
}
