package business



/**
 * 微信关注会员
 * @author Pony
 *
 */
class TMember {
	
	TUser user
	String nickname
	String openid //唯一openid
	String icon	//头像
	Integer sex		
	String phone
	String wechat	//微信号
	
	Date dateCreated
	Date lastUpdated
	
	static mapping = {
		version false
		//cache true
	}
	
	static constraints = {
		nickname(nullable: true)
		openid(nullable:false)
		sex(nullable:true)
		phone(unullable:false)
		wechat(nullable:true)
	}
	
	
	def getSexStr(){
		def str = this.sex==1?"男":(this.sex==0?"女":"")
		return str
	}
}
