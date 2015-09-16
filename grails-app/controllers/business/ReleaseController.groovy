package business

import basic.GlobalUtil


class ReleaseController {
	
	def orderService
	
	//首页
	def index = {
		
		
//		def company = session.company
//		def openid = session.openid
//		def member = TMember.findByOpenidAndCompany(openid,company)
//		def lists = TProduct.createCriteria().list{
//			eq("company",company)
//			order("id", "asc")
//		}
//		[lists:lists,company:company,openid:openid,member:member]
	}
	
	//登录
	def weixinauth = {
//		def company = TCompany.get(params.id?:0)
//		def openid  = session.openid
		if(openid){
			redirect(action:'index')
		}
		else redirect(url:"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+company.appid+"&redirect_uri="+basic.TConfig.findByCode('website.address')?.value+"/release/register&response_type=code&scope=snsapi_userinfo&state="+params.id+"#wechat_redirect");
	}
	
	//微信回调接口
	def register = {
//		println params
//		def company = TCompany.get(params.state?:0 as long)
//		def openid  = WeixinUtil.getOpenid(company?.appid, company?.appsecret, params.code)
//		def member = TMember.findByOpenidAndCompany(openid?:'0',company)
//		println "openid: "+openid
//		
//		if(member){
//			session.company = company
//			session.member = member
//			session.openid = openid
//			redirect(action:'index')
//		}else{
//			 render(view:"register",model:[openid:openid,companyId:params.state])
//		}
	}
	
}
