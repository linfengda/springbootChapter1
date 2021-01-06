package com.linfengda.sb.support.serializable.selector;

import com.linfengda.sb.support.serializable.annotation.EnableJsonFieldSerializer;
import com.linfengda.sb.support.serializable.annotation.SerializeType;
import com.linfengda.sb.support.serializable.config.JsonFieldSerializerConvectorConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 引入配置类
 *
 * @author: linfengda
 * @date: 2020-07-26 22:36
 */
@Configuration
public class JsonFieldSerializerConfigSelector extends SerializerTypeImportSelector<EnableJsonFieldSerializer> {

    @Override
    protected String[] selectImports(SerializeType serializeType) {
        switch (serializeType) {
            case FAST_JSON:
                return new String[] {JsonFieldSerializerConvectorConfig.class.getName()};
            case JACKSON:
                return new String[0];
            default:
                return null;
        }
    }
}
