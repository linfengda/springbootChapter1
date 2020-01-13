package com.linfengda.sb.support.dao.tableAnnontation;

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

	String name() default "none";
}
