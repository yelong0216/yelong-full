/**
 * 
 */
package org.yelong.commons.toy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 彭飞
 * @date 2019年6月10日上午11:46:30
 */
public class ObjectUtils {
	
	/**
	 * 设置字段值
	 * <p>
	 * 	对对象中指定类型的字段设置为统一的值<br/>
	 * 	基础类型无法替换</br>
	 *  不会对不为null的值进行替换
	 * </p>
	 * @author 彭飞
	 * @date 2019年6月10日下午1:27:22
	 * @param obj
	 * @param fieldType
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> void setFieldValue(Object obj , Class<T> fieldType , T value ) throws IllegalArgumentException, IllegalAccessException {
		setFieldValue(obj, fieldType, value, false);
	}
	
	/**
	 * 设置字段值
	 * <p>
	 * 	对对象中指定类型的字段设置为统一的值<br/>
	 * 	基础类型无法替换</br>
	 *  不会对不为null的值进行替换</br>
	 *  不对notContainsField中包含的字段进行赋值
	 * </p>
	 * @author 彭飞
	 * @date 2019年6月10日下午1:27:22
	 * @param obj
	 * @param fieldType
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> void setFieldValue(Object obj , Class<T> fieldType , T value ,String notContainsField) throws IllegalArgumentException, IllegalAccessException {
		setFieldValue(obj, fieldType, value, false,notContainsField);
	}
	
	/**
	 * 设置字段值
	 * <p>
	 * 	对对象中指定类型的字段设置为统一的值<br/>
	 * 	基础类型无法替换<br/>
	 * </p>
	 * @author 彭飞
	 * @date 2019年6月10日下午1:24:33
	 * @param obj 
	 * @param fieldType 设置的字段类型
	 * @param value 设置的值
	 * @param cover 是否覆盖(true:覆盖)
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> void setFieldValue(Object obj , Class<T> fieldType , T value , boolean cover) throws IllegalArgumentException, IllegalAccessException {
		setFieldValue(obj, fieldType, value, cover, null);
	}
	
	/**
	 * 设置字段值
	 * <p>
	 * 	对对象中指定类型的字段设置为统一的值<br/>
	 * 	基础类型无法替换<br/>
	 *  默认排除final字段
	 * </p>
	 * @author 彭飞
	 * @date 2019年6月10日下午1:24:33
	 * @param obj 
	 * @param fieldType 设置的字段类型
	 * @param value 设置的值
	 * @param cover 是否覆盖(true:覆盖)
	 * @param notContainsField 不需要替换的字段(多个之间用逗号分隔)
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> void setFieldValue(Object obj , Class<T> fieldType , T value , boolean cover , String notContainsField) throws IllegalArgumentException, IllegalAccessException {
		if( null == obj ) {
			throw new NullPointerException();
		}
		if (null == notContainsField) {
			notContainsField = "";
		}
		//当前对象内所有字段
		Field [] fields = obj.getClass().getDeclaredFields();
		//进行设置的字段
		List<Field> setFields = new ArrayList<Field>(fields.length);
		
		//选出需要进行替换的字段
		for (Field field : fields) {
			//不是final字段
			if(Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			//不是指定类型
			if( field.getType() != fieldType) {
				continue;
			}
			//不需要赋值的字段
			if(notContainsField.contains(field.getName())) {
				continue;
			}
			//不是空的字段
			if( !cover ) {
				field.setAccessible(true);
				Object fieldValue = field.get(obj);
				if( fieldValue != null ) {
					if (fieldValue instanceof String) {
						if("".equals(fieldValue)) {
							continue;
						}
					}
					continue;
				}
			}
			setFields.add(field);
		}
		
		//赋值
		for (Field field : setFields) {
			if(field.isAccessible()) {
				field.set(obj, value);
			} else {
				field.setAccessible(true);
				field.set(obj, value);
			}
		}
		
	}
	
	/**
	 * 获取obj对象中propertyNames数组中所有属性的值<br/>
	 * @author 彭飞
	 * @date 2019年8月6日下午3:22:40
	 * @version 1.0
	 * @param obj 对象
	 * @param propertyNames 需要获取值的属性数组
	 * @return obj对象中propertyNames属性的值集合。该集合为ArrayList。保证了与数组的一一对应性
	 * @throws NoSuchFieldException 如果propertyNames属性中有一个是obj中get方法不存在且属性也不存在
	 */
	public static List<Object> getObjectPropertyValues(Object obj , String [] propertyNames) throws NoSuchFieldException{
		List<Object> values = new ArrayList<Object>(propertyNames.length);
		for (String propertyName : propertyNames) {
			values.add(getObjectPropertyValue(obj, propertyName));
		}
		return values;
	}
	
	
	/**
	 * 获取obj对象的propertyName属性的值<br/>
	 * 优先使用get方法获取，如果没有get方法则通过反射获取。
	 * 如果当前类不存在该属性则通过其父类寻找直至Object类为止都没有找到则抛出{@link NoSuchFieldException}
	 * @author 彭飞
	 * @date 2019年7月25日上午11:08:13
	 * @param obj 对象
	 * @param propertyName 属性名称
	 * @return obj对象的propertyName属性值
	 * @throws NoSuchFieldException 如果没有这个属性
	 */
	public static Object getObjectPropertyValue(Object obj , String propertyName)  throws NoSuchFieldException{
		String getMethodName = "get"+propertyName.substring(0, 1).toUpperCase()+propertyName.substring(1);
		Class<?> objClass = obj.getClass();
		Object fieldValue = null;
		try {
			Method method = objClass.getMethod(getMethodName);
			if( null != method) {
				fieldValue = method.invoke(obj);
			}
		} catch (Exception e) {
			//方法调用异常后使用反射进行获取
			while(true) {
				if( objClass == Object.class ) {
					throw new NoSuchFieldException(obj.getClass().getName()+"没有找到："+propertyName+"属性");
				}
				try {
					Field field = objClass.getDeclaredField(propertyName);
					if( null != field) {
						field.setAccessible(true);
						fieldValue = field.get(obj);
						break;
					}
				}catch (Exception e1) {
					objClass = objClass.getSuperclass();
					continue;
				} 
			}
		}
		return fieldValue;
	}
	
	/**
	 * 设置对象属性值<br/>
	 * 优先使用set方法设置，如果没有set方法则通过反射设置。如果当前类不存在该属性则通过其父类寻找直至Object类为止都没有找到则抛出{@link NoSuchFieldException}
	 * @author 彭飞
	 * @date 2019年8月2日下午3:29:46
	 * @version 1.0
	 * @param obj 需要设置值的对象
	 * @param propertyName 需要设置的属性名称
	 * @param value 属性值
	 * @return 设置值后的的对象obj
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Object setObjectPropertyValue(Object obj , String propertyName , Object value )  throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		String setMethodName = "set"+propertyName.substring(0, 1).toUpperCase()+propertyName.substring(1);
		Class<?> objClass = obj.getClass();
		try {
			Method setMethod = objClass.getMethod(setMethodName, value.getClass());
			setMethod.invoke(obj, value);
		} catch (Exception e) {
			//方法调用异常后使用反射进行获取
			while(true) {
				if( objClass == Object.class ) {
					throw new NoSuchFieldException("没有找到："+propertyName+"字段！");
				}
				try {
					Field field = objClass.getDeclaredField(propertyName);
					if( null != field) {
						field.setAccessible(true);
						field.set(obj, value);
						break;
					}
				}catch (NoSuchFieldException e1) {
					objClass = objClass.getSuperclass();
					continue;
				} 
			}
		} 
		return obj;
	}
	
	
	
	
	
	/**
	 * 验证对象属性是否为空<br/>
	 * {@link CharSequence}类型使用{@link StringUtils#isEmpty(CharSequence)}进行验证<br/>
	 * 其余对象如果为null则判断为空<br/>
	 * 获取属性方式：<br/>
	 * &nbsp;1、通过get方法获取。<br/>
	 * &nbsp;2、通过反射获取。<br/>
	 * &nbsp;3、获取父类，通过父类获取。<br/>
	 * 如果没有该属性将抛出{@link NoSuchFieldException}
	 * @author 彭飞
	 * @date 2019年7月25日上午10:47:18
	 * @return 如果实行不为空则返回true。否则返回false。{@link CharSequence}类型默认使用{@link StringUtils#isEmpty(CharSequence)}进行验证
	 * @throws NoSuchFieldException 如果没有这个字段。
	 */
	public static boolean validateObjectPropertyIsEmpty(Object obj ,String fieldName) throws NoSuchFieldException {
		Object fieldValue = getObjectPropertyValue(obj, fieldName);
		if( fieldValue == null ) {
			return true;
		} else {
			if( fieldValue instanceof CharSequence ) {
				return StringUtils.isEmpty(fieldValue.toString());
			}
			return false;
		}
	}
	
	
	
	
}
