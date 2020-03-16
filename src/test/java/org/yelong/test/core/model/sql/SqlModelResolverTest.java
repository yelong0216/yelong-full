/**
 * 
 */
package org.yelong.test.core.model.sql;

import org.yelong.core.jdbc.dialect.OracleDialect;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.condition.support.DefaultConditionResolver;
import org.yelong.core.model.ModelProperties;
import org.yelong.core.model.resolve.AnnotationModelResolver;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.DefaultModelSqlFragmentFactory;
import org.yelong.core.model.sql.DefaultSqlModelResolver;
import org.yelong.core.model.sql.SqlModelResolver;
import org.yelong.test.core.model.User;

/**
 * @author PengFei
 * @date 2020年3月2日下午4:33:48
 * @since 1.0
 */
public class SqlModelResolverTest {

	public void test1() {
		ModelAndTableManager modelAndTableManager = new ModelAndTableManager(new AnnotationModelResolver(new ModelProperties()));
		ConditionResolver conditionResolver = new DefaultConditionResolver(new DefaultModelSqlFragmentFactory(new OracleDialect(), modelAndTableManager));
		SqlModelResolver sqlModelResolver = new DefaultSqlModelResolver(modelAndTableManager, conditionResolver);
		User user = new User();
		user.setRealName("彭飞");
		user.addExtendAttribute("role.name", "彭飞");
		user.addExtendAttribute("id", "彭飞");
		user.addCondition(new Condition("creator","=","彭飞").setGroupName("A").setConnectWay(ConditionConnectWay.OR));
		user.addCondition(new Condition("updator","=","彭飞").setGroupName("A"));
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToCondition(user,true);
		System.out.println(conditionSqlFragment.getSqlFragment());
	}
	
	
}
