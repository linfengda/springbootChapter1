package com.linfengda.sb.support.serializable.annotation;

import com.linfengda.sb.support.serializable.selector.JsonFieldSerializerConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description: 开启字段json转换注解
 * @author: linfengda
 * @date: 2020-07-26 22:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({JsonFieldSerializerConfigSelector.class})
public @interface EnableJsonFieldSerializer {

    /**
     * 指定当前项目使用的序列化方式{@link org.springframework.boot.autoconfigure.http.HttpMessageConverters}
     * @return
     */
    SerializeType serializeType() default SerializeType.FAST_JSON;
}
