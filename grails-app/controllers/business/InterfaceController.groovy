package business

import grails.converters.JSON
import basic.FileUtil
import basic.GlobalUtil
import basic.JsonUtil
import basic.StringUtil

import com.github.x0001.weixin.WeiXin
import com.github.x0001.weixin.vo.recv.WxRecvEventMsg
import com.github.x0001.weixin.vo.recv.WxRecvMsg
import com.github.x0001.weixin.vo.recv.WxRecvPicMsg
import com.github.x0001.weixin.vo.recv.WxRecvTextMsg
import com.github.x0001.weixin.vo.send.WxSendMsg
import com.github.x0001.weixin.vo.send.WxSendNewsMsg
import com.github.x0001.weixin.vo.send.WxSendTextMsg

class InterfaceController {
	
	def memberService
	
	def static TOKEN = 'weixinzhushou'

	def receive = {
//		if(WeiXin.access(TOKEN, params.signature, params.timestamp, params.nonce)) {
//			if(params.echostr) {
//				render params.echostr
//				return
//			}
//			WxRecvMsg msg = WeiXin.recv(request.getInputStream())
//			WxSendMsg sendMsg = WeiXin.builderSendByRecv(msg)
//			def company = TCompany.findByWxid(msg.toUser)
//			def customertext
//			def customerimage
//			def customerlocation
//			if(msg instanceof WxRecvTextMsg) {
//				def keyword = msg.content?.trim()
//				if((keyword.endsWith("天气") || keyword.startsWith("翻译") || keyword.startsWith("歌曲") || keyword == "历史上的今天") || keyword.startsWith("快递") && keyword.size() > 2) {
//					WeixinUtil.sendLifeServiceInfo(keyword, sendMsg, response.getOutputStream())
//				}else if(keyword.contains("您好")||keyword.contains("你好")||keyword.contains("在吗")||keyword.contains("有人吗")){ 
//				    //多客服添加
//				    println "INTO CUSTOMER SERVICE"
//					sendMsg.setMsgType("transfer_customer_service")
//					WeiXin.send(sendMsg, response.getOutputStream())
//				} else {
//					customertext = TCustomerText.findByCompanyAndKeyword(company, keyword)
//					customerimage = TCustomerImage.findByCompanyAndKeyword(company, keyword)
//					customerlocation = TCustomerLocation.findByCompanyAndKeyword(company, keyword)
//					if(!customertext && !customerimage && !customerlocation) {
//						customertext = TCustomerText.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_DEFAULT)
//						customerimage = TCustomerImage.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_DEFAULT)
//					}
//				}
//			} else if(msg instanceof WxRecvEventMsg) {
//				if(msg.event == 'subscribe') {
//					def member = TMember.findByOpenid(msg.fromUser)
//					if(member && (TCustomerText.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_REATTENTION) 
//						|| TCustomerImage.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_REATTENTION))) {
//						customertext = TCustomerText.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_REATTENTION)
//						customerimage = TCustomerImage.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_REATTENTION)
//					} else {
//						customertext = TCustomerText.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_ATTENTION)
//						customerimage = TCustomerImage.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_ATTENTION)
//						memberService.saveNewMember(company, msg.fromUser)
//					}
//				} else if(msg.event == 'CLICK') {
//					def keyword = msg.eventKey
//					customertext = TCustomerText.findByCompanyAndKeyword(company, keyword)
//					customerimage = TCustomerImage.findByCompanyAndKeyword(company, keyword)
//					customerlocation = TCustomerLocation.findByCompanyAndKeyword(company, keyword)
//				}
//			} else if(msg instanceof WxRecvPicMsg) {
//				def keyword = msg.picUrl
//				WeixinUtil.sendLifeServiceInfo(keyword, sendMsg, response.getOutputStream())
//			} else {
//				customertext = TCustomerText.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_DEFAULT)
//				customerimage = TCustomerImage.findByCompanyAndType(company, GlobalUtil.CUSTOMERSERVICE_TYPE_DEFAULT)
//			}
//			if(customertext) {
//				sendMsg = new WxSendTextMsg(sendMsg, customertext.content)
//			} else if(customerimage) {
//				sendMsg = new WxSendNewsMsg(sendMsg)
//				if(customerimage.url?.startsWith("http://") || customerimage.url?.startsWith("https://")) {
//					sendMsg.addItem(customerimage.title, customerimage.summary, WeixinUtil.getImgUrl(customerimage.imagePath), customerimage.url)
//				} else {
//					sendMsg.addItem(customerimage.title, customerimage.summary, WeixinUtil.getImgUrl(customerimage.imagePath), WeixinUtil.getReleaseUrl(customerimage.url?:"customerimage/${customerimage.id}"))
//				}
//				customerimage.children.each {
//					if(it.url?.startsWith("http://") || it.url?.startsWith("https://")) {
//						sendMsg.addItem(it.title, it.summary, WeixinUtil.getImgUrl(it.imagePath), it.url)
//					} else {
//						sendMsg.addItem(it.title, it.summary, WeixinUtil.getImgUrl(it.imagePath), WeixinUtil.getReleaseUrl(it.url?:"customerimage/${it.id}"))
//					}
//				}
//			} else if(customerlocation) {
//				sendMsg = new WxSendNewsMsg(sendMsg)
//				sendMsg.addItem(customerlocation.name, customerlocation.address, null, WeixinUtil.getReleaseUrl("customerlocation/${customerlocation.id}"))
//			}
//			if(customertext || customerimage || customerlocation) {
//				WeiXin.send(sendMsg, response.getOutputStream())
//			}
//		}
	}
	
	def oauth2 = {
//		def company = TCompany.get(params.id)
//		println "session member id: " + session."${params.id}_memberid"
//		def actionname = params.actionname?:'mallindex'
//		if(!TMember.get(session."${params.id}_memberid"?:0)) {
//			def url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+company.appid+"&redirect_uri="+basic.TConfig.findByCode('website.address')?.value+"/interface/oauth2callback&response_type=code&scope=snsapi_base&state=${params.id},${actionname}#wechat_redirect"
//			println "oauth2 url: " + url
//			redirect(url:url)
//		} else {
//			println "redirect to ${actionname}"
////			redirect(controller:'release', action:'mallindex', params:[id:params.id])
//			render(view:'index', model:[url:"${request.contextPath}/release/${actionname}/${company?.id}"])
//		}
	}
	
	def oauth2callback = {
		println "callback from oauth2: ${params}"
//		def companyid = params.state.split(",")[0]
//		def actionname = params.state.split(",")[1]
//		def company = TCompany.get(companyid)
//		if(params.code) {
//			def openid = WeixinUtil.getOpenid(company?.appid, company?.appsecret, params.code)
//			if(openid) {
//				def member = TMember.findByOpenid(openid?:'0')?:memberService.saveNewMember(company, openid)
//				if(member) {
//					session."${company.id}_memberid" = member.id
//					session.setMaxInactiveInterval(21600)
//				}
//			}
//		}
//		redirect(controller:'release', action:'mallindex', params:[id:company.id])
		render(view:'index', model:[url:"${request.contextPath}/release/${actionname}/${company?.id}"])
	}
	
	
}
