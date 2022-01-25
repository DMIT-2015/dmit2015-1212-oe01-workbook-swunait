package common.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;

@DataSourceDefinitions({
//	@DataSourceDefinition(
//		name="java:app/datasources/h2databaseDS",
//		className="org.h2.jdbcx.JdbcDataSource",
////		url="jdbc:h2:file:~/dmit2015db",
//        url="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
//		user="sa",
//		password="sa"),

//	@DataSourceDefinition(
//		name="java:app/datasources/hsqldatabaseDS",
//		className="org.hsqldb.jdbc.JDBCDataSource",
//		url="jdbc:hsqldb:file:~/databases/dmit2015-1212-assignment02-hsqldb;shutdown=true",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mssqlDS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://localhost;databaseName=DMIT2015DB",
//		user="user2015",
//		password="Password2015"),

	@DataSourceDefinition(
	 	name="java:app/datasources/oracleUser2015DS",
	 	className="oracle.jdbc.pool.OracleDataSource",
	 	url="jdbc:oracle:thin:@localhost:1521/xepdb1",
	 	user="user2015",
	 	password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mysqlDS",
//		className="com.mysql.cj.jdbc.MysqlDataSource",
//		url="jdbc:mysql://127.0.0.1/DMIT2015DB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mariadbDS",
//		className="org.mariadb.jdbc.MariaDbDataSource",
//		url="jdbc:mariadb://127.0.0.1:13306/dmit2015DB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/postgresqlDS",
//		className="org.postgresql.ds.PGPoolingDataSource",
//		url="jdbc:postgresql://127.0.0.1/DMIT2015DB",
//		user="user2015",
//		password="Password2015"),

})

@ApplicationScoped
public class ApplicationConfig {

}

