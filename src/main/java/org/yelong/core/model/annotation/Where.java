/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * where注解。
 * 应用在model 或者 view 中
 * 
 * @author PengFei
 * @date 2019年12月22日上午2:28:31
 * @since 1.0
 */
@Deprecated
public @interface Where {

	/**
	 * model or view 默认sql是否存在 where 关键字
	 * 如果为 true 则在拼接条件中不在进行拼接 where 关键字
	 * 
	 * @return <tt>true</tt> 存在 where 关键字
	 */
	boolean existWhereKeyword() default false;
	
	/**
	 * 默认条件
	 * 
	 * @return
	 */
	String defaultCondition() default "";
	
}
