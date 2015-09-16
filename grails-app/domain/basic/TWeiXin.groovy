package basic

import business.TUser

/**
 * 微信基本配置
 * @author Pony
 *
 */
class TWeiXin {
	
	String appid	//AppID(应用ID)
	String appsecret //AppSecret(应用密钥)
	String wxid   //原始ID
	String wxName	//微信名
	String weixinid //微信账号
	TUser user   //用户
	
	static mapping = {
		version false
	}
	
	static constraints = {
		appid nullable:false
		appsecret nullable:false
		wxid nullable:true
		wxName nullable:true
		weixinid nullable:true
		user nullable:false
	}
}
