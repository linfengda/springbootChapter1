package com.lfd.soa.srv.demo.support.orm.annontation;

import java.lang.annotation.*;

/**
 * remark as table on entity
 * @author linfengda
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

	String name() default "";
}
