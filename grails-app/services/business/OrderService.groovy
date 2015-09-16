package business


import basic.GlobalUtil

/**
 * @Package： business
 * @Title：OrderServines.groovy
 * @author： Pony
 * @Description： TODO
 *
 */
class OrderService {

	
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
