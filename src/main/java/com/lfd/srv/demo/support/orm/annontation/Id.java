package com.lfd.srv.demo.support.orm.annontation;

import java.lang.annotation.*;

/**
 * remark as ID
 * @author linfengda
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
	
}
