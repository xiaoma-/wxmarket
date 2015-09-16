package business



/**
 * 微客服图文表
 * @author Pony
 *
 */
class TCustomerImage {

	TUser user
	TCustomerImage parent
	String keyword
	String imagePath
	String title
	String content
	String summary
	String url
	String type
	Integer viewNum	// 阅读数
	Integer goodNum	// 点赞数
	
	Date dateCreated
	Date lastUpdated
	
	static constraints = {
		user(nullable:false)
		parent(nullable:true)
		title(nullable:true)
		keyword(nullable:true)
		content(nullable:true)
		summary(nullable:true)
		url(nullable:true)
		type(nullable:true)
		viewNum(nullable:true)
		goodNum(nullable:true)
	}

	static mapping = {
		version false
		//cache true
		content(type: 'text')
	}
	
	def getChildren() {
		def result = []
		if(id) {
			result = TCustomerImage.findAllByParent(this)
		}
		return result
	}
	
	def getNextChild(currentChild) {
		def result
		if(id) {
			result = TCustomerImage.createCriteria().list(max: 1, offset: 0) {
				and {
					eq('parent', this)
					if(currentChild) {
						gt('id', currentChild.id)
					}
				}
				order("id", "asc")
			}[0]
		}
		return result
	}
	
	def doDelete() {
		children.each {
			it.delete()
		}
		this.delete()
	}
}
