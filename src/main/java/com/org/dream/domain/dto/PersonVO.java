package com.org.dream.domain.dto;

import com.org.dream.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonVO {
    private String name;

    private String address;

    private int age;

    private GenderEnum gender;
}
