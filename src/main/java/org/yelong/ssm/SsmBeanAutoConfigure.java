/**
 * 
 */
package org.yelong.ssm;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.Assert;

/**
 * @author pengfei<yl1430834495@163.com>
 * @date 2019年11月6日上午9:36:46
 * @version 1.2
 */
public class SsmBeanAutoConfigure {

	protected BeanDefinitionRegistry registry;

	public SsmBeanAutoConfigure(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	/**
	 * 简单的bean注入
	 * @author 彭飞
	 * @date 2019年10月28日下午4:13:19
	 * @version 1.2
	 * @param beanName
	 * @param beanClass
	 */
	public void registerSimpleBean(String beanName,Class<?> beanClass) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
		//使用BeanDefinitionBuilder获取bean定义器
		BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
		registry.registerBeanDefinition(beanName, beanDefinition);
	}
	
	/**
	 * 注册 modelConfiguration、modelService、db、jdbcDB 四个bean
	 * @date 2019年11月6日上午9:56:37
	 * @version 1.2
	 * @throws  
	 * @throws Exception 
	 */
	public void registerGroup(SsmModelProperties ssmModelProperties) throws Exception {
		String ssmBeanDefinitionBuilderClassName = ssmModelProperties.getSsmBeanDefinitionBuilderClassName();
		SsmBeanDefinitionBuilder ssmBeanDefinitionBuilder = ssmModelProperties.getSsmBeanDefinitionBuilder();
		if( null == ssmBeanDefinitionBuilder) {
			if( StringUtils.isNotBlank(ssmBeanDefinitionBuilderClassName) ) {
				ssmBeanDefinitionBuilder = (SsmBeanDefinitionBuilder) ClassUtils.getClass(ssmBeanDefinitionBuilderClassName).newInstance();
			} else {
				ssmBeanDefinitionBuilder = getDefaultSsmBeanDefinitionBuilder();
			}
		}
		Assert.notNull(ssmModelProperties.getDatabaseDialect(),
				"未发现数据库方言");
		Assert.notNull(ssmBeanDefinitionBuilder, 
				"未发现ssm bean 的构建器");
		ssmBeanDefinitionBuilder.setSsmModelProperties(ssmModelProperties);
		registry.registerBeanDefinition(ssmModelProperties.getMyBatisBaseDataBaseOperationBeanName(), ssmBeanDefinitionBuilder.buidlerMyBatisBaseDataBaseOperation());
		registry.registerBeanDefinition(ssmModelProperties.getModelConfigurationBeanName(), ssmBeanDefinitionBuilder.buidlerModelConfiguration());
		registry.registerBeanDefinition(ssmModelProperties.getModelServiceBeanName(), ssmBeanDefinitionBuilder.buidlerModelService());
		registry.registerBeanDefinition(ssmModelProperties.getMyBatisJdbcBaseDataBaseOperationBeanName(), ssmBeanDefinitionBuilder.buidlerMyBatisJdbcBaseDataBaseOperation());
	}

	public SsmBeanDefinitionBuilder getDefaultSsmBeanDefinitionBuilder() {
		return new DefaultSsmBeanDefinitionBuilder();
	}
	
	
}
