package com.org.dream.domain.vo.rsp.weixin;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Access token response.
 */
@ApiModel(value = "AccessTokenResponse", description = "微信令牌响应结果")
@NoArgsConstructor
@Data
public class AccessTokenResponse {
    @ApiModelProperty("响应码")
    private Integer errcode;

    @ApiModelProperty("响应码的解释")
    private String errmsg;

    @JsonAlias("access_token")
    // @JsonProperty("access_token")
    @ApiModelProperty(value = "token", example = "QOkCn4PIPeRIWD-791m68KgH7YZRGYsbr96J1P8pCCP7z80LyyzfSjWJ_E1GI3tOnNvcBUnT_XWp6qhMrpwJjGzWozOLzKg0YoswwA4ZpYP9qY7o0nn_rozLdpuKc9Yeo6XiQWAak-nf-zX9JQoSI8iN0D3t30AF35wYfUYw2iMX6lu-XTNnVfMQzTkTlfpXHm2G7Qp5EsZbDHP7FL8BGQ")
    private String accessToken;

    @JsonAlias("expires_in")
    // @JsonProperty("expires_in")
    @ApiModelProperty(value = "过期时间(秒)", example = "7200")
    private Integer expiresIn;
}
