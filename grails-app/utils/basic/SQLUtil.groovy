package basic

class SQLUtil {
	def static dataSource
	def static getResult(String sql,  List parameter){
		def db = new groovy.sql.Sql(dataSource)
		def rows = db.rows(sql, parameter)
		return rows
	}

	/**
	 * 执行sql
	 * @param sql
	 * @return
	 */
	def static execute(String sql){
		def db = new groovy.sql.Sql(dataSource)
		return db.execute(sql)
	}
}
