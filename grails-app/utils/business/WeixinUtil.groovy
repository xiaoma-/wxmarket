package business

import basic.DateTimeUtil
import basic.HttpClientUtil
import basic.JsonUtil

import com.github.x0001.weixin.WeiXin
import com.github.x0001.weixin.vo.send.WxSendMusicMsg
import com.github.x0001.weixin.vo.send.WxSendNewsMsg
import com.github.x0001.weixin.vo.send.WxSendTextMsg


class WeixinUtil {
	
	public static String getAccessToken(String appid, String secret) throws Exception {
		System.out.println("log--->getAccessToken[start]:")
		def result
		if(appid && secret) {
			def company //= TCompany.findByAppid(appid)
			System.out.println("companyParams:id["+company?.id+"] appid["+appid+"]secret["+secret+"]")
			if(company?.accesstoken && company?.accesstokenexpire > new Date()) {
				result = company?.accesstoken
			} else {
				String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret
				String res = HttpClientUtil.getResponseByUrl(url)
				result = JsonUtil.string2json(res).access_token
				System.out.println("weixinResult: "+res)
				company.accesstoken = result
				company.accesstokenexpire = DateTimeUtil.addSeconds(new Date(), JsonUtil.string2json(res)?.expires_in)
				company.save()
			}
		}
		System.out.println("log--->getAccessToken[end]:")
		return result
	}
	
	public static String getjsapi_ticket(String appid, String secret) {
		def result
		if(appid && secret) {
			def company //= TCompany.findByAppid(appid)
			if(company?.jsapi_ticket && company?.jsapi_ticketexpire > new Date()) {
				result = company?.jsapi_ticket
			} else {
				String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=${getAccessToken(appid, secret)}&type=jsapi"
				String res = HttpClientUtil.getResponseByUrl(url)
				result = JsonUtil.string2json(res).ticket
				println("jsapi_ticket#companyid(${company.id}): "+res)
				company.jsapi_ticket = result
				company.jsapi_ticketexpire = DateTimeUtil.addSeconds(new Date(), JsonUtil.string2json(res)?.expires_in)
				company.save()
			}
		}
		return result
	}
	
	public static getImgUrl(String imgPath) {
		def result = "${basic.TConfig.findByCode('website.address')?.value}/${imgPath?:'images/clear.png'}"
		return result
	}
	
	public static getReleaseUrl(String uri) {
		def result = "${basic.TConfig.findByCode('website.address')?.value}/release/${uri}"
		return result
	}
	
	public static getOpenid(String appid, String secret, String code) {
		def url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=${appid}&secret=${secret}&code=${code}&grant_type=authorization_code"
		String res = HttpClientUtil.getResponseByUrl(url)
		def json = JsonUtil.string2json(res)
		println "oauth2/access_token: "+json
		return json.openid
	}
	
	public static sendLifeServiceInfo(keyword, sendMsg, outputStream) {
		if(keyword.startsWith("歌曲")) {
			sendMusicInfo(keyword, sendMsg, outputStream)
		} else if(keyword.endsWith("天气")) {
			sendWeatherInfo(keyword, sendMsg, outputStream)
		} else if(keyword == "历史上的今天") {
			sendHistoryTodayInfo(keyword, sendMsg, outputStream)
		} else if(keyword.startsWith("翻译")) {
			sendTranslationInfo(keyword, sendMsg, outputStream)
		} else if(keyword.startsWith("http://")) {
			sendPersonFaceInfo(keyword, sendMsg, outputStream)
		}else if(keyword.startsWith("快递")) {
			sendDeliveryInfo(keyword, sendMsg, outputStream)
		}
	}
	
	public static sendWeatherInfo(keyword, sendMsg, outputStream) {
		def city = keyword.replace("天气", "").trim()
		def url = "http://api.map.baidu.com/telematics/v3/weather?location=${city}&output=json&ak=3a00a921b0870edd40478b1feb9d3338"
		String res = HttpClientUtil.getResponseByUrl(url)
		def json = JsonUtil.string2json(res)
		if(json.results) {
			def weather_data = json.results[0].weather_data
			sendMsg = new WxSendNewsMsg(sendMsg)
			weather_data.eachWithIndex { weather, i ->
				if(i == 0) {
					sendMsg.addItem("${weather.date} ${weather.temperature} ${weather.weather} ${weather.wind}", "${weather.temperature} ${weather.weather} ${weather.wind}", null, null)
				} else {
					sendMsg.addItem("${weather.date.substring(0, 2)} ${weather.temperature} ${weather.weather} ${weather.wind}", null, weather.dayPictureUrl, null)
				}
			}
			WeiXin.send(sendMsg, outputStream)
		}
	}
	
	public static sendTranslationInfo(keyword, sendMsg, outputStream) {
		def q = keyword.replace("翻译", "")
		def url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=rzTpz2aOlncbOLAoGCZttoNl&q=${q}&from=auto&to=auto"
		String res = HttpClientUtil.getResponseByUrl(url)
		def json = JsonUtil.string2json(res)
		if(json.trans_result) {
			def dst = json.trans_result[0].dst
			sendMsg = new WxSendTextMsg(sendMsg, dst)
			WeiXin.send(sendMsg, outputStream)
		}
	}
	
	public static sendHistoryTodayInfo(keyword, sendMsg, outputStream) {
		def url = "http://history.sturgeon.mopaas.com/jsonp"
		String res = HttpClientUtil.getResponseByUrl(url)
		def json = JsonUtil.string2json(res)
		def borths = json."出生"[1..2]
		def events = json."大事记"[1..2]
		def content = ""
		borths.eachWithIndex { b, i ->
			if(i == 0) {
				content += "出生：\r\n"
			}
			content += "${b}\r\n"
		}
		events.eachWithIndex { e, i ->
			if(i == 0) {
				content += "大事件：\r\n"
			}
			content += "${e}\r\n"
		}
		if(content) {
			content.substring(0, content.lastIndexOf("\r\n"))
		}
		sendMsg = new WxSendTextMsg(sendMsg, content)
		WeiXin.send(sendMsg, outputStream)
	}
	
	public static sendMusicInfo(keyword, sendMsg, outputStream) {
		def q = keyword.replace("歌曲", "").trim()
		def url = "http://api2.sinaapp.com/search/music/?appkey=0020130430&appsecert=fa6095e1133d28ad&reqtype=music&keyword=${q}"
		String res = HttpClientUtil.getResponseByUrl(url)
		def json = JsonUtil.string2json(res)
		if(json.music && json.music.musicurl) {
			def songUrl = json.music.musicurl 
			def hqSongUrl = json.music.hqmusicurl
			sendMsg = new WxSendMusicMsg(sendMsg, q, "来自百度音乐", songUrl, hqSongUrl)
			WeiXin.send(sendMsg, outputStream)
		}
	}
	
	public static sendPersonFaceInfo(keyword, sendMsg, outputStream) {
		def url = "http://apicn.faceplusplus.com/v2/detection/detect?api_key=94421f9b0f262d1a1640c348048936e2&api_secret=TyZtkAwA860NvrOWoyqvf3xsl-jnNo0b&url=${keyword}"
		String res = HttpClientUtil.getResponseByUrl(url)
		def json = JsonUtil.string2json(res)
		def content = ""
		if(json.face) {
			def age = new Integer(json.face[0]?.attribute?.age?.value)
			def genderconfidence = new Double(json.face[0]?.attribute?.gender?.confidence).intValue()
			def gender= json.face[0]?.attribute?.gender?.value == "Female"?'女':'男'
			def raceconfidence = new Double(json.face[0]?.attribute?.race?.confidence).intValue()
			def race = [Asian:'亚洲人',White:'白人',Black:'黑人'][json.face[0]?.attribute?.race?.value]
			def smiling = new Double(json.face[0]?.attribute?.smiling?.value * 0.7)?.intValue()
			def beautypoint = (130 - Math.abs(25 - age) + smiling) / 2
			def beautydescription
			def agedescription
			if(age < 20) {
				agedescription = "豆蔻年华"
			} else if(age < 30) {
				agedescription = "碧玉年华"
			} else if(age < 40) {
				if(gender == '女') {
					agedescription = "半老徐娘"
				} else {
					agedescription = "而立之年"
				}
			} else if(age < 50) {
				agedescription = "不惑之年"
			} else if(age < 60) {
				agedescription = "知命之年"
			} else {
				agedescription = "从心之年"
			}
			if(beautypoint < 30) {
				beautydescription = "纯吊丝"
			} else if(beautypoint < 40) {
				beautydescription = "吊丝"
			} else if(beautypoint < 50) {
				beautydescription = "基本是人了"
			} else if(beautypoint < 60) {
				if(gender == '女') {
					beautydescription = "人见人爱"
				} else {
					beautydescription = "风流倜傥"
				}
			} else if(beautypoint < 70) {
				if(gender == '女') {
					beautydescription = "人间尤物"
				} else {
					beautydescription = "玉树临风"
				}
			} else if(beautypoint < 80) {
				if(gender == '女') {
					beautydescription = "倾国倾城"
				} else {
					beautydescription = "玉面郎君"
				}
			} else if(beautypoint < 90) {
				if(gender == '女') {
					beautydescription = "美若天仙"
				} else {
					beautydescription = "貌赛潘安"
				}
			} else {
				if(gender == '女') {
					beautydescription = "女神啊！"
				} else {
					beautydescription = "高富帅"
				}
			}
			content = "察颜~观色~面相~摸骨~ 嘿！有了： 约${age}岁(${agedescription})， ${raceconfidence}% ${race}， ${genderconfidence}% ${gender}性， ${gender == '女'?'漂亮':'帅气'}指数：${beautypoint}(${beautydescription}/::B)"
		} else {
			content = "察颜~观色~面相~摸骨~ 嘿！石头一枚！"
		}
		sendMsg = new WxSendTextMsg(sendMsg, content)
		WeiXin.send(sendMsg, outputStream)
	}
	
	public static sendDeliveryInfo(keyword, sendMsg, outputStream) {
		def postId = keyword.replace("快递", "").trim()
		def postKey = ['顺丰':'shunfeng','申通':'shentong','圆通':'yuantong','韵达':'yunda','中通':'zhongtong','宅急送':'zhaijisong','汇通':'huitongkuaidi','天天':'tiantian','邮政国内':'youzhengguonei','EMS':'ems','德邦物流':'debangwuliu']
		def key
		def value
		try {
			for(keys in postKey){
				def url = "http://api.kuaidi100.com/api?id=2e1acdfc1a2be1f7&com=${keys.getValue()}&nu=${postId}&valicode=&show=2&muti=1&order="
//				def url = "http://api.ickd.cn/?id=103702&secret=81b1b8f5740fb8ba8d97cb08298625c0&com=${keys.getValue()}&nu=${postId}&type=json"
					String res = HttpClientUtil.getResponseByUrl(url)
					def nullstring = """地点和跟踪进度</td>"""
					def isNull = res?.contains(nullstring)
					if(isNull && res){
						key = keys.getKey()
						value = keys.getValue()
						break
					}
				}
			} catch (Exception e) {
			e.printStackTrace()
		}
		def title = "${key?:'快递单号'}：${postId}"
		def description = "${key?'点击查看更快递详情！':'没有查到快递详情，请检查快递单号是否输入正确！或点击查看全文重新查询！'}"
		def picUrl = "${basic.TConfig.findByCode('website.address')?.value}/images/express.jpg"
		def url = "http://m.kuaidi100.com/index_all.html?type=${value}&postid=${key?postId:''}"
		sendMsg = new WxSendNewsMsg(sendMsg)
		sendMsg.addItem(title, description, picUrl, url)
		WeiXin.send(sendMsg, outputStream)
	}
}
