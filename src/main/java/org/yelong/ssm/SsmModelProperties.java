/**
 * 
 */
package org.yelong.ssm;

import org.yelong.core.model.ModelProperties;

/**
 * @author 彭飞
 * @date 2019年10月18日下午5:02:29
 * @version 1.2
 */
public class SsmModelProperties extends ModelProperties{

	public static final String DEFAULT_MODEL_CONFIGURATION_BEAN_NAME = "modelConfiguration";
	
	public static final String DEFAULT_MODEL_SERVICE_BEAN_NAME = "modelService";
	
	public static final String DEFAULT_MYBATIS_BASEDATABASEOPERATION_BEAN_NAME = "myBatisBaseDataBaseOperation";
	
	public static final String DEFAULT_MYBATIS_JDBCBASEDATABASEOPERATION_BEAN_NAME = "myBatisJdbcBaseDataBaseOperation";
	
	public static final String DEFAULT_SQLSESSION_BEAN_NAME = "sqlSessionTemplate";

	/**
	 * 注册后的modelConfiguration bean name
	 */
	private String modelConfigurationBeanName = DEFAULT_MODEL_CONFIGURATION_BEAN_NAME;
	
	/**
	 * 注册后的modelService bean name
	 */
	private String modelServiceBeanName = DEFAULT_MODEL_SERVICE_BEAN_NAME;
	
	/**
	 * 注册后的myBatisBaseDataBaseOperation bean name
	 */
	private String myBatisBaseDataBaseOperationBeanName = DEFAULT_MYBATIS_BASEDATABASEOPERATION_BEAN_NAME;
	
	/**
	 * 注册后的myBatisJdbcBaseDataBaseOperation bean name
	 */
	private String myBatisJdbcBaseDataBaseOperationBeanName = DEFAULT_MYBATIS_JDBCBASEDATABASEOPERATION_BEAN_NAME;
	
	/**
	 * spring容器中的sqlSession 的 bean name
	 */
	private String sqlSessionBeanName = DEFAULT_SQLSESSION_BEAN_NAME;

	/**
	 * ssm bean 构建器class名称
	 */
	private String ssmBeanDefinitionBuilderClassName;

	/**
	 * ssm bean 构建器
	 * 此属性存在时，ssmBeanDefinitionBuilderClassName属性将不使用
	 */
	private SsmBeanDefinitionBuilder ssmBeanDefinitionBuilder;
	
	
	public String getModelConfigurationBeanName() {
		return modelConfigurationBeanName;
	}

	public void setModelConfigurationBeanName(String modelConfigurationBeanName) {
		this.modelConfigurationBeanName = modelConfigurationBeanName;
	}

	public String getMyBatisBaseDataBaseOperationBeanName() {
		return myBatisBaseDataBaseOperationBeanName;
	}

	public void setMyBatisBaseDataBaseOperationBeanName(String myBatisBaseDataBaseOperationBeanName) {
		this.myBatisBaseDataBaseOperationBeanName = myBatisBaseDataBaseOperationBeanName;
	}

	public String getMyBatisJdbcBaseDataBaseOperationBeanName() {
		return myBatisJdbcBaseDataBaseOperationBeanName;
	}

	public void setMyBatisJdbcBaseDataBaseOperationBeanName(String myBatisJdbcBaseDataBaseOperationBeanName) {
		this.myBatisJdbcBaseDataBaseOperationBeanName = myBatisJdbcBaseDataBaseOperationBeanName;
	}

	public String getSqlSessionBeanName() {
		return sqlSessionBeanName;
	}

	public void setSqlSessionBeanName(String sqlSessionBeanName) {
		this.sqlSessionBeanName = sqlSessionBeanName;
	}

	public String getModelServiceBeanName() {
		return modelServiceBeanName;
	}

	public void setModelServiceBeanName(String modelServiceBeanName) {
		this.modelServiceBeanName = modelServiceBeanName;
	}

	public String getSsmBeanDefinitionBuilderClassName() {
		return ssmBeanDefinitionBuilderClassName;
	}

	public void setSsmBeanDefinitionBuilderClassName(String ssmBeanDefinitionBuilderClassName) {
		this.ssmBeanDefinitionBuilderClassName = ssmBeanDefinitionBuilderClassName;
	}

	public SsmBeanDefinitionBuilder getSsmBeanDefinitionBuilder() {
		return ssmBeanDefinitionBuilder;
	}

	public void setSsmBeanDefinitionBuilder(SsmBeanDefinitionBuilder ssmBeanDefinitionBuilder) {
		this.ssmBeanDefinitionBuilder = ssmBeanDefinitionBuilder;
	}
	
	
	
}
