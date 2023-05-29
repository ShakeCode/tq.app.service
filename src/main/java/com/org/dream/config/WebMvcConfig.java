package com.org.dream.config;

import com.fasterxml.classmate.util.ClassKey;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.org.dream.config.serializer.TypeEnumDeserializer;
import com.org.dream.enums.BaseEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                .allowedHeaders("Content-Type", "X-Request-With", "Access-Control-Request-Method", "Access-Control-Request-Headers", "token")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 跨域允许时间
                .maxAge(3600)
                // 是否允许证书（cookies）
                .allowCredentials(true);

    }

    /**
     * 使用阿里 fastjson 作为 JSON MessageConverter 实体返回null的时候可以变成空字符串等等
     * @param converters
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除原来的转换器
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while(iterator.hasNext()){
            HttpMessageConverter<?> converter = iterator.next();
            if(converter instanceof MappingJackson2HttpMessageConverter){
                iterator.remove();
            }
        }
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                // 保留 Map 空的字段
                SerializerFeature.WriteMapNullValue,
                // 将 String 类型的 null 转成""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将 Number 类型的 null 转成 0
//                SerializerFeature.WriteNullNumberAsZero,
                // 将 List 类型的 null 转成 []
                SerializerFeature.WriteNullListAsEmpty,
                // 将 Boolean 类型的 null 转成 false
//                SerializerFeature.WriteNullBooleanAsFalse,
                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect,
                //枚举类返回前端使用toString()方法的值
                SerializerFeature.WriteEnumUsingToString);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在 Controller 上的 @RequestMapping 中加了个属性 produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(converter);
    }*/


   /* @Bean
    public Jackson2ObjectMapperBuilderCustomizer enumCustomizer(){
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(BaseEnum.class, new JsonSerializer<BaseEnum>() {
            @Override
            public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeNumberField("code",value.getCode());
                gen.writeStringField("description",value.getDescription());
                gen.writeEndObject();
            }
        });
    }*/
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(this.objectMapperForWebConvert());
        converters.add(0, stringHttpMessageConverter);
        converters.add(0, converter);
    }


    public ObjectMapper objectMapperForWebConvert() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule sm = new SimpleModule();
        // 自定义查找规则
        sm.setDeserializers(new SimpleDeserializers() {
            @Override
            public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config,
                                                            BeanDescription beanDesc) throws JsonMappingException {
                JsonDeserializer<?> enumDeserializer = super.findEnumDeserializer(type, config, beanDesc);
                if (enumDeserializer != null) {
                    return enumDeserializer;
                }
                // 遍历枚举实现的接口, 查找反序列化器
                for (Class<?> typeInterface : type.getInterfaces()) {
                    enumDeserializer = this._classMappings.get(new ClassKey(typeInterface));
                    if (enumDeserializer != null) {
                        return enumDeserializer;
                    }
                }
                return null;
            }
        });
        sm.addDeserializer(BaseEnum.class, new TypeEnumDeserializer());
      /*  sm.addSerializer(BaseEnum.class, new JsonSerializer<BaseEnum>() {
            @Override
            public void serialize(BaseEnum baseEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("code", baseEnum.getCode());
                jsonGenerator.writeStringField("description", baseEnum.getDescription());
                jsonGenerator.writeEndObject();
                *//*
                * "status": {
                      "code": 1,
                      "description": "有效"
                    },
                *//*
            }
            
        });*/
        om.registerModule(sm);
        return om;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加防止http://127.0.0.1:12090/doc.html 找不到映射404
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}
