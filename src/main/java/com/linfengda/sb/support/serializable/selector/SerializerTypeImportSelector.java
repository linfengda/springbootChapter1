package com.linfengda.sb.support.serializable.selector;

import com.linfengda.sb.support.serializable.annotation.EnableJsonFieldSerializer;
import com.linfengda.sb.support.serializable.annotation.SerializeType;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-07 17:57
 */
public abstract class SerializerTypeImportSelector<A extends Annotation> implements ImportSelector {
    public static final String DEFAULT_SERIALIZER_TYPE_ATTRIBUTE_NAME = "serializeType";
    protected AnnotationAttributes attributes;

    @Override
    public final String[] selectImports(AnnotationMetadata importMetadata) {
        this.attributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableJsonFieldSerializer.class.getName(), false));
        if (this.attributes == null) {
            throw new IllegalArgumentException(
                    "@EnableJsonFieldSerializer is not present on importing class " + importMetadata.getClassName());
        }
        SerializeType serializeType = attributes.getEnum(DEFAULT_SERIALIZER_TYPE_ATTRIBUTE_NAME);
        String[] imports = selectImports(serializeType);
        if (imports == null) {
            throw new IllegalArgumentException("Unknown SerializeType: " + serializeType);
        }
        return imports;
    }

    protected abstract String[] selectImports(SerializeType serializeType);
}
