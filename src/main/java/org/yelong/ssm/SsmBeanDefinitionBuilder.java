/**
 * 
 */
package org.yelong.ssm;

import org.springframework.beans.factory.config.BeanDefinition;
import org.yelong.core.annotation.Nullable;

/**
 * ssm框架中BeanDefinition 的构建。
 * 构建后将注入spring容器。
 * @author pengfei<yl1430834495@163.com>
 * @date 2019年11月6日上午11:30:39
 * @version 1.2
 */
public interface SsmBeanDefinitionBuilder {

	/**
	 * @date 2019年11月6日上午11:38:31
	 * @version 1.2
	 * @return modelConfiguration BeanDefinition
	 */
	BeanDefinition buidlerModelConfiguration();
	
	/**
	 * @date 2019年11月6日上午11:38:44
	 * @version 1.2
	 * @return modelService BeanDefinition
	 */
	BeanDefinition buidlerModelService();
	
	/**
	 * @date 2019年11月6日上午11:38:50
	 * @version 1.2
	 * @return MyBatisBaseDataBaseOperation BeanDefinition
	 */
	BeanDefinition buidlerMyBatisBaseDataBaseOperation();
	
	/**
	 * @date 2019年11月6日上午11:38:44
	 * @version 1.2
	 * @return MyBatisJdbcBaseDataBaseOperation BeanDefinition
	 */
	@Nullable
	BeanDefinition buidlerMyBatisJdbcBaseDataBaseOperation();
	
	/**
	 * 设置配置,此方法优先被调用
	 * @date 2019年11月6日上午11:32:33
	 * @version 1.2
	 * @param ssmModelProperties
	 */
	void setSsmModelProperties(SsmModelProperties ssmModelProperties);
	
}
