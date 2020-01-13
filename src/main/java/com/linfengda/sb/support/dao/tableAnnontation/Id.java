package com.linfengda.sb.support.dao.tableAnnontation;

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
