package com.linfengda.sb.chapter1.springbootImport.target;

import com.linfengda.sb.chapter1.springbootImport.color.Blue;
import com.linfengda.sb.chapter1.springbootImport.color.Green;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 描述: @Import可以传入ImportSelector的实现类
 *
 * @author linfengda
 * @create 2019-12-30 15:13
 */
public class ColorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {Blue.class.getName(), Green.class.getName()};
    }
}
