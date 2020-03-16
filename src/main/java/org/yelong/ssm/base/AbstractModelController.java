/**
 * 
 */
package org.yelong.ssm.base;

import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.service.ModelService;
import org.yelong.support.spring.mvc.controller.AbstractSpringMvcController;

/**
 * @author pengfei<yl1430834495@163.com>
 * @date 2019年11月18日下午6:47:33
 * @version 1.3
 */
public abstract class AbstractModelController extends AbstractSpringMvcController{
	
	/**
	 * 获取组合条件
	 * @date 2019年11月13日上午11:32:57
	 * @version 1.2
	 * @param modelService
	 * @return
	 */
	public CombinationConditionSqlFragment createCombinationSqlCondition(ModelService modelService) {
		return modelService.getModelConfiguration().getModelSqlFragmentFactory().createCombinationConditionSqlFragment();
	}
	
	/**
	 * 创建简单条件
	 * @date 2019年11月13日上午11:34:23
	 * @version 1.2
	 * @param modelService
	 * @param condition
	 * @param params
	 * @return
	 */
	public ConditionSqlFragment createSimpleSqlCondition(ModelService modelService,String condition,Object ... params) {
		return modelService.getModelConfiguration().getModelSqlFragmentFactory().createConditionSqlFragment(condition, params);
	}
	
	/**
	 * 创建排序片段
	 * @date 2019年12月11日下午3:53:12
	 * @param modelService
	 * @return
	 */
	public SortSqlFragment createSortSqlFragment(ModelService modelService) {
		return modelService.getModelConfiguration().getModelSqlFragmentFactory().createSortSqlFragment();
	}
	
}
