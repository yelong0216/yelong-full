package org.yelong.core.jdbc.sql.test;

import java.util.ArrayList;
import java.util.List;

import org.yelong.core.jdbc.dialect.MySqlDialect;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.condition.support.DefaultConditionResolver;
import org.yelong.core.jdbc.sql.factory.DefaultSqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

public class ConditionTest {

	public static void main(String[] args) {
		SqlFragmentFactory sqlFragmentFactory = new DefaultSqlFragmentFactory(new MySqlDialect());
		ConditionResolver conditionResolver = new DefaultConditionResolver(sqlFragmentFactory);
		List<Condition> conditionList = new ArrayList<>();
		conditionList.add(new Condition("id","is null"));
		conditionList.add(new Condition("id","is null").setGroupName("A").setConnectWay(ConditionConnectWay.OR));
		conditionList.add( new Condition("id","is null").setGroupName("A"));
		conditionList.add(new Condition("id","is null").setGroupName("C"));
		conditionList.add( new Condition("id","is null").setGroupName("B"));
		conditionList.add(new Condition("id","is null").setGroupName("B"));
		
		CombinationConditionSqlFragment sqlFragment = conditionResolver.resolve(conditionList);
		System.out.println(sqlFragment.getSqlFragment());
		
		
		
	}
	
	
}
