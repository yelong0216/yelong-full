/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.model.Model;
import org.yelong.core.model.annotation.Transient;

/**
 * @author PengFei
 * @date 2020年2月21日下午4:14:50
 * @since 1.0
 */
public class SqlModel extends Model{

	/**
	 * @date 2020年3月2日上午9:15:14
	 */
	private static final long serialVersionUID = -3986147639250539909L;

	@Transient
	private transient final Map<String, String> conditionOperatorMap = new HashMap<>();

	@Transient
	private transient final Map<String, Object> extendAttributesMap = new LinkedHashMap<>();

	@Transient
	private transient final Map<String, String> sortFieldMap = new LinkedHashMap<>();

	@Transient
	private transient final List<Condition> conditions = new ArrayList<>();
	
	@Transient
	private transient final Class<? extends Model> modelClass;
	
	/**
	 * 指定sql model为其本身
	 */
	protected SqlModel(){
		this(null);
	}
	
	/**
	 * 指定该sql model属于那个model。
	 * @param modelClass
	 */
	public SqlModel(Class<? extends Model> modelClass) {
		this.modelClass = modelClass;
	}
	
	/**
	 * 添加查询的条件
	 * 这个查询条件映射的值为拓展属性或者其model对象中的属性值
	 * @date 2019年11月13日上午8:58:22
	 * @version 1.2
	 * @param conditionName 条件字段名称。
	 * @param condition 条件
	 * @return this
	 */
	public SqlModel addConditionOperator(String column, String operator){
		this.conditionOperatorMap.put(column, operator);
		return this;
	}

	/**
	 * 获取所有条件
	 * @date 2019年11月13日上午9:16:55
	 * @version 1.2
	 * @return
	 */
	public Map<String, String> getConditionOperatorMap() {
		return this.conditionOperatorMap;
	}

	/**
	 * 添加一个拓展属性
	 * @date 2019年11月13日上午9:15:47
	 * @version 1.2
	 * @param attrName 属性名称 。推荐添加别名。否则会出现未知的为题
	 * @param attrValue 属性值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SqlModel addExtendAttribute(String attrName, Object attrValue) {
		if (this.extendAttributesMap.containsKey(attrName)) {
			Object value = this.extendAttributesMap.get(attrName);
			List<Object> valueList = null;

			if ((value instanceof List)) {
				valueList = (List<Object>)value;
			} else {
				valueList = new ArrayList<>();
				valueList.add(value);
			}
			valueList.add(attrValue);
			this.extendAttributesMap.put(attrName, valueList);
		}
		else {
			this.extendAttributesMap.put(attrName, attrValue);
		}
		return this;
	}

	/**
	 * 移除一个拓展属性
	 * @date 2019年11月13日上午9:16:05
	 * @version 1.2
	 * @param attrName
	 * @return
	 */
	public SqlModel removeExtendAttribute(String attrName){
		this.extendAttributesMap.remove(attrName);
		return this;
	}

	/**
	 * 获取拓展属性
	 * @date 2019年11月13日上午9:16:24
	 * @version 1.2
	 * @param attrName 属性名称
	 * @return
	 */
	@Nullable
	public Object getExtendAttribute(String attrName){
		return this.extendAttributesMap.get(attrName);
	}

	/**
	 * 获取所有拓展属性
	 * @date 2019年11月13日上午9:16:41
	 * @version 1.2
	 * @return
	 */
	public Map<String, Object> getExtendAttributesMap(){
		return this.extendAttributesMap;
	}

	public Map<String, String> getSortFieldMap() {
		return this.sortFieldMap;
	}
	
	/**
	 * 添加排序
	 * @date 2019年11月13日上午10:19:51
	 * @version 1.2
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
	public SqlModel addSortField(String sortField, String sortOrder) {
		this.sortFieldMap.put(sortField, sortOrder);
		return this;
	}

	/**
	 * 添加一个查询条件
	 * @date 2019年12月16日上午10:35:34
	 * @param queryFilterInfo
	 * @return this
	 */
	public SqlModel addCondition(Condition condition) {
		this.conditions.add(condition);
		return this;
	}

	/**
	 * 获取所有条件
	 * @return
	 */
	public List<Condition> getConditions() {
		return conditions;
	}
	
	/**
	 * 获取sqlModel对应的class
	 * 如果{@link #modelClass} != null 则返回{@link #modelClass} ，否则返回{@link #getClass()}
	 * @return
	 */
	public Class<? extends Model> getModelClass() {
		return modelClass != null ? modelClass : getClass();
	}
	
}
