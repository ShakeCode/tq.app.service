package com.org.dream.util;

import com.google.common.collect.Lists;
import com.org.dream.domain.dto.Person;
import com.org.dream.domain.dto.PersonVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

public class BeanUtil extends org.springframework.beans.BeanUtils {

    public BeanUtil() {
    }

    public static Map<String, Object> beanToMap(Object object) {
        Map<String, Object> map = new HashMap<>(1);
        if (object == null) {
            return map;
        }
        BeanMap beanMap = BeanMap.create(object);
        beanMap.forEach((k, v) -> {
            map.put(k.toString(), v);
        });
        return map;
    }

    public static <T> T propertiesCopy(Object source, Class<T> clazz) {
        if (null == source) {
            return null;
        }
        try {
            T obj = clazz.newInstance();
            BeanUtils.copyProperties(source, obj);
            return obj;
        } catch (IllegalAccessException | InstantiationException var3) {
            throw new RuntimeException(var3);
        }

    }

    /**
     * list中对象的copy
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> collectionCopy(List<? extends Object> source, Class<T> clazz) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.EMPTY_LIST;
        }
        return source.stream().map(data -> propertiesCopy(data, clazz)).collect(Collectors.toList());
    }

    /**
     * 将对象转换为map
     * @param obj
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap();
        if (obj == null) {
            return map;
        } else {
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            try {
                Field[] var4 = fields;
                int var5 = fields.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    Field field = var4[var6];
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }

                return map;
            } catch (Exception var8) {
                throw new RuntimeException(var8);
            }
        }
    }


    /**
     * 将map转换为对象,必须保证属性名称相同
     * @return
     */
    public static Object map2Object(Map<Object, Object> map, Class<?> clzz) {
        try {
            Object target = clzz.newInstance();
            if (CollectionUtils.isEmpty(map)) {
                return target;
            }
            Field[] fields = clzz.getDeclaredFields();
            if (!CollectionUtils.isEmpty(Arrays.asList(fields))) {
                Arrays.stream(fields).filter((Field field) -> map.containsKey(field.getName())).forEach(var -> {
                    //获取属性的修饰符
                    int modifiers = var.getModifiers();
                    if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                        //在lambada中结束本次循环是用return,它不支持continue和break
                        return;
                    }
                    //设置权限
                    var.setAccessible(true);
                    try {
                        var.set(target, map.get(var.getName()));
                    } catch (IllegalAccessException e) {
                        //属性类型不对,非法操作,跳过本次循环,直接进入下一次循环
                        throw new RuntimeException(e);
                    }
                });
            }
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(12);
        person.setName("李军");
        Map<String, Object> map = beanToMap(person);
        System.out.println(map);

        List<PersonVO> list = new ArrayList<>();
        // Collections.copy(Lists.newArrayList(person), list);
        // 空列表
        // System.out.println(list);

        List<PersonVO> resList = BeanUtil.collectionCopy(Lists.newArrayList(person), PersonVO.class);
        System.out.println(resList);
    }


}