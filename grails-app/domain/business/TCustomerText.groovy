package business


/**
 * 微客服文字表
 * @author Pony
 *
 */
class TCustomerText {

	TUser user
	String keyword
	String content
	String type
	
	Date dateCreated
	Date lastUpdated
	
	static constraints = {
		keyword(nullable:true)
		content(nullable:true)
		type(nullable:true)
	}

	static mapping = {
		version false
		//cache true
		content(type: 'text')
	}
}
