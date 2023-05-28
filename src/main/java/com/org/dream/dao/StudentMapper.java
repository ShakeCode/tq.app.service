package com.org.dream.dao;

import com.org.dream.domain.dto.PersonVO;
import com.org.dream.domain.pojo.PersonPO;
import com.org.dream.domain.pojo.SchoolPO;
import com.org.dream.domain.pojo.StudentPO;
import com.org.dream.domain.vo.rsp.SchoolStudentVO;
import com.org.dream.domain.vo.rsp.StudentVO;
import com.org.dream.enums.GenderEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "studentName"),
            @Mapping(source = "age", target = "studentAge")
    })
    StudentVO po2Vo(StudentPO studentPO);

    List<StudentVO> poList2VoList(List<StudentPO> studentPO);

    @Mappings({
            @Mapping(source = "schoolPO.name", target = "schoolName"),
            @Mapping(source = "studentPO.name", target = "studentName")
    })
    SchoolStudentVO mergeVo(SchoolPO schoolPO, StudentPO studentPO);

    @Mappings({
            @Mapping(source = "gender", target = "gender", qualifiedByName = "convertGender"),
    })
    PersonPO vo2Po(PersonVO personVO);

    @Named("convertGender")
    default Integer convertTargetType(GenderEnum genderEnum) {
        return genderEnum.getValue();
    }

    static void main(String[] args) {
        // po2Object();
        // poList2ObjList();
        // mutilItemMergeObject();
        po2ConvertObj();
    }

    static void po2ConvertObj() {
        PersonVO personVO = PersonVO.builder().gender(GenderEnum.MALE).build();
        PersonPO personPO = StudentMapper.INSTANCE.vo2Po(personVO);
        System.out.println(personPO);
    }

    static void mutilItemMergeObject() {
        SchoolPO schoolPO = SchoolPO.builder().name("学校名字").build();
        StudentPO studentPO = StudentPO.builder().name("学生名字").build();
        SchoolStudentVO schoolStudentVO = StudentMapper.INSTANCE.mergeVo(schoolPO, studentPO);
        System.out.println(schoolStudentVO);
    }

    static void poList2ObjList() {
        List<StudentPO> studentPOList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            StudentPO studentPO = StudentPO.builder().id(i).name(String.valueOf(i)).age(i).build();
            studentPOList.add(studentPO);
        }
        List<StudentVO> studentVOList = StudentMapper.INSTANCE.poList2VoList(studentPOList);
        System.out.println(studentVOList);
    }

    static void po2Object() {
        StudentPO studentPO = StudentPO.builder().id(10).name("test")
                .age(24).className("教室名").build();
        StudentVO studentVO = StudentMapper.INSTANCE.po2Vo(studentPO);
        System.out.println(studentVO);
    }

}
