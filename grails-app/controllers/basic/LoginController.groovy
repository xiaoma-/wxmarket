package basic

import business.TUser



class LoginController {
	
	def auth = {
		[]
	}
	
	def doLogin = {
		def user = TUser.findByUsernameAndPassword(params.username,params.password)
		if(user){
			session.user = user
			render "success"
		}else render "fail"
	}
}
