package com.linfengda.sb.chapter1.demo.springbootImport.target;

import com.linfengda.sb.chapter1.demo.springbootImport.color.Yellow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: @Import可以传入配置类
 *
 * @author linfengda
 * @create 2019-12-30 15:09
 */
@Configuration
public class ColorRegistrarConfiguration {

    @Bean
    public Yellow yellow(){
        return new Yellow();
    }
}
