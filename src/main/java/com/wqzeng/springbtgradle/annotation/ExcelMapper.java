package com.wqzeng.springbtgradle.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author moke.tsang
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelMapper {
	/**
	 * 对应Excel表格第几列
	 * @return
	 */
	int rowNum() default 0;

	/**
	 * 日期格式
	 */
	String dateFormat() default "yyyy-MM-dd HH:mm:ss";
}
