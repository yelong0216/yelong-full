/**
 * 
 */
package org.yelong.commons.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author PengFei
 * @date 2020年1月20日下午4:56:30
 */
public class BeanUtils {

	public static Object getProperty(Object bean , String propertyName) throws NoSuchMethodException {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, bean.getClass());
			Method readMethod = propertyDescriptor.getReadMethod();
			return readMethod.invoke(bean);
		} catch (Exception e) {
			throw new NoSuchMethodException(bean.getClass() + " not found get or is " + propertyName + " method ");
		}
	}

	public static void setProperty(Object bean , String propertyName , Object value) throws NoSuchMethodException {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, bean.getClass());
			Method writeMethod = propertyDescriptor.getWriteMethod();
			writeMethod.invoke(bean,value);
		} catch (Exception e) {
			throw new NoSuchMethodException(bean.getClass() + " not found set " + propertyName + " method ");
		}
	}

}
