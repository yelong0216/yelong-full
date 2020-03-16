/**
 * 
 */
package org.yelong.ssm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.yelong.commons.util.PropertiesUtils;
import org.yelong.support.properties.wired.PropertiesWiredProcessorBuilder;
import org.yelong.support.spring.ApplicationContextDecorator;

/**
 * 此类由spring初始化。
	<bean class="org.yl.support.ssm.SsmConfiguration" id="modelConfiguration">
		<property name="configLocations">
			<array>
				<value>oracle-model-configuration.properties</value>
				<value>mysql-model-configuration.properties</value>
			</array>
		</property>
	</bean>
 * @author 彭飞
 * @date 2019年10月17日下午4:53:59
 * @version 1.3
 */
public class SsmConfiguration implements BeanDefinitionRegistryPostProcessor{

	public static final String SSM_PROPERTIES_PREFIX = "yelong.ssm";

	/**
	 * 配置
	 */
	private SsmModelProperties [] ssmModelProperties;

	/**
	 * 配置文件路径
	 */
	private String [] configLocations;

	public SsmConfiguration() {

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		try {

			List<SsmModelProperties> ssmModelPorpertiesList = new ArrayList<SsmModelProperties>();
			if( null != ssmModelProperties) {
				ssmModelPorpertiesList.addAll(Arrays.asList(ssmModelProperties));
			} else {
				for (String configLocation : configLocations) {
					ssmModelPorpertiesList.add(PropertiesWiredProcessorBuilder.builder(SsmModelProperties.class, PropertiesUtils.load(configLocation), SSM_PROPERTIES_PREFIX).wiredObj());
				}
			}
			this.ssmModelProperties = ssmModelPorpertiesList.toArray(new SsmModelProperties[] {});
			if( (null == ssmModelProperties || 0 == ssmModelProperties.length) ) {
				throw new IllegalArgumentException("没有发现配置文件！");
			}
			SsmBeanAutoConfigure beanAutoConfigure = new SsmBeanAutoConfigure(registry);
			beanAutoConfigure.registerSimpleBean("applicationContextDecorator", ApplicationContextDecorator.class);
			for (int i = 0; i < ssmModelProperties.length; i++) {
				SsmModelProperties properties = ssmModelProperties[i];
				beanAutoConfigure.registerGroup(properties);
			}
		} catch (Exception e) {
			throw new BeansException("注册ssm bean出现异常",e) {
				private static final long serialVersionUID = -3967291510169415183L;};
		}
	}

	public SsmModelProperties[] getSsmModelProperties() {
		return ssmModelProperties;
	}

	public void setSsmModelProperties(SsmModelProperties[] ssmModelProperties) {
		this.ssmModelProperties = ssmModelProperties;
	}

	public String[] getConfigLocations() {
		return configLocations;
	}

	public void setConfigLocations(String[] configLocations) {
		this.configLocations = configLocations;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}


}
