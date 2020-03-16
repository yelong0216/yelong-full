/**
 * 
 */
package org.yelong.commons.annotation;

import java.lang.annotation.Annotation;

/**
 * @author PengFei
 * @date 2019年12月24日下午8:48:34
 */
public class AnnotationUtils {
	
	/**
	 * 获取c 类标注的注解 如果recursive 为 true 将进行递归获取annotation注解
	 * 递归：如果c类不存在annotation注解，将在c的父类中进行查找直至Object类
	 * 注意：此方法不会抛出注解存在异常，如果注解不存在将返回 null
	 * @date 2019年12月24日下午8:52:43
	 * @param <A>
	 * @param c class
	 * @param annotation 获取的注解类型
	 * @param recursive 是否递归
	 */
	public static <A extends Annotation> A getAnnotation(Class<?> c , Class<A> annotation , boolean recursive){
		if( c.isAnnotationPresent(annotation) ) {
			return c.getAnnotation(annotation);
		}
		if(!recursive) {
			return null;
		}
		Class<?> superClass = c.getSuperclass();
		while(true) {
			if( superClass == Object.class ) {
				return null;
			}
			if( superClass.isAnnotationPresent(annotation) ) {
				return superClass.getAnnotation(annotation);
			}
			superClass = superClass.getSuperclass();
		}
	}
	
	
}
