/**
 * 
 */
package org.yelong.ssm;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.dialect.Dialects;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.condition.support.DefaultConditionResolver;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.resolve.AnnotationModelResolver;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.DefaultModelSqlFragmentFactory;
import org.yelong.core.model.sql.DefaultSqlModelResolver;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.ssm.service.SsmModelServiceImpl;
import org.yelong.support.orm.mybaits.jdbc.MyBatisJdbcBaseDataBaseOperation;
import org.yelong.support.orm.mybaits.mapper.DefaultMyBatisBaseDataBaseOperation;

/**
 * 默认的ssm bean构建
 * @author pengfei<yl1430834495@163.com>
 * @date 2019年11月6日上午11:39:43
 * @version 1.2
 */
public class DefaultSsmBeanDefinitionBuilder implements SsmBeanDefinitionBuilder{
	
	protected SsmModelProperties ssmModelProperties;

	@Override
	public BeanDefinition buidlerModelConfiguration() {
		BeanDefinitionBuilder modelConfigurationDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ModelConfiguration.class);
		
		Dialect dialect = Dialects.valueOfByName(ssmModelProperties.getDatabaseDialect()).getDialect();
		ModelAndTableManager modelAndTableManager = new ModelAndTableManager(new AnnotationModelResolver(ssmModelProperties));
		ModelSqlFragmentFactory modelSqlFragmentFactory = new DefaultModelSqlFragmentFactory(dialect,modelAndTableManager);
		ConditionResolver conditionResolver = new DefaultConditionResolver(modelSqlFragmentFactory);
		
		modelConfigurationDefinitionBuilder.addConstructorArgValue(dialect);
		modelConfigurationDefinitionBuilder.addConstructorArgValue(ssmModelProperties);
		modelConfigurationDefinitionBuilder.addConstructorArgValue(modelAndTableManager);
		modelConfigurationDefinitionBuilder.addConstructorArgValue(modelSqlFragmentFactory);
		modelConfigurationDefinitionBuilder.addConstructorArgValue(conditionResolver);
		modelConfigurationDefinitionBuilder.addConstructorArgValue(new DefaultSqlModelResolver(modelAndTableManager, conditionResolver));
		//使用BeanDefinitionBuilder获取bean定义器
		BeanDefinition modelConfigurationDefinition = modelConfigurationDefinitionBuilder.getBeanDefinition();
		return modelConfigurationDefinition;
	}

	@Override
	public BeanDefinition buidlerModelService() {
		BeanDefinitionBuilder modelServiceDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SsmModelServiceImpl.class);
		modelServiceDefinitionBuilder.addConstructorArgReference(ssmModelProperties.getModelConfigurationBeanName());
		modelServiceDefinitionBuilder.addConstructorArgReference(ssmModelProperties.getMyBatisBaseDataBaseOperationBeanName());
		//modelServiceDefinitionBuilder.addConstructorArgValue(Dialects.valueOfByName(ssmModelProperties.getDatabaseDialect()).getDialect());
		//使用BeanDefinitionBuilder获取bean定义器
		BeanDefinition modelServiceDefinition = modelServiceDefinitionBuilder.getBeanDefinition();
		return modelServiceDefinition;
	}

	@Override
	public BeanDefinition buidlerMyBatisBaseDataBaseOperation() {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMyBatisBaseDataBaseOperation.class);
		beanDefinitionBuilder.addConstructorArgReference(ssmModelProperties.getSqlSessionBeanName());
		//使用BeanDefinitionBuilder获取bean定义器
		BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
		return beanDefinition;
	}

	@Override
	public BeanDefinition buidlerMyBatisJdbcBaseDataBaseOperation() {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyBatisJdbcBaseDataBaseOperation.class);
		beanDefinitionBuilder.addConstructorArgReference(ssmModelProperties.getSqlSessionBeanName());
		//使用BeanDefinitionBuilder获取bean定义器
		BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
		return beanDefinition;	
	}

	@Override
	public void setSsmModelProperties(SsmModelProperties ssmModelProperties) {
		this.ssmModelProperties =ssmModelProperties;
	}
	
	public SsmModelProperties getSsmModelProperties() {
		return ssmModelProperties;
	}
	

}
