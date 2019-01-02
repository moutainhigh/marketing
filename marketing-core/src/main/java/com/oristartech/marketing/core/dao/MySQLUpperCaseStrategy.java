package com.oristartech.marketing.core.dao;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
public class MySQLUpperCaseStrategy extends PhysicalNamingStrategyStandardImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -117278299852425564L;

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		// 将表名全部转换成大写
		String tableName = name.getText().toLowerCase();
		
		return name.toIdentifier(tableName);
	}
}
