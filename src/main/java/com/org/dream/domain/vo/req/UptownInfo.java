package com.org.dream.domain.vo.req;

import com.org.dream.domain.entity.UptownEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class UptownInfo extends UptownEntity {

    public static void main(String[] args) {
        System.out.println(new UptownInfo().setId(1).setCreateBy("system").setCreateDate(new Date()));
    }
}
