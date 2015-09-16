package business

/**
 * 账号设置
 * @author Pony
 *
 */
class TUser {
	String username
	String password
	
	static mapping = {
		version false
	}
	
	static constraints = {
		username nullable:false	
		password nullable:false
	}
}
