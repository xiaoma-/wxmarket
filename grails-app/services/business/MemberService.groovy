package business

import basic.GlobalUtil

/**
 * @Package： business
 * @Title：OrderServines.groovy
 * @author： maqiankun
 * @Description： TODO
 * @date： 2015年6月5日下午3:16:14
 */
class MemberService {

	
	def saveOrder(params){
		println params
		def flag = "success"
		try {
			
		} catch (Exception e) {
			flag = "fail"
			println e
			e.printStackTrace()
		}
		return flag 
	}
	
}
