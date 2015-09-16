class ApplicationFilters {
	def filters = {
		all(controller:'*', action:'*') {
			before = {
				 if(!session.user && controllerName && controllerName != 'login' && controllerName != 'logout' && controllerName != 'release' && controllerName != 'interface' ) {
					session.targetParams = params
					redirect(controller:'login', action:'auth')
					return
				}
//				if(controllerName in ['website','member','campaign','customerservice','group','privilege','coupon','customertext','customerimage','customerlocation','customermenu','businesscard','mall','chain','estate','hotel','animation'] &&
//					actionName in ['themes','orders','products','syncIndex','billlist','cardList','card','management','dial','attention','reattention','defaulttext','index','list','content','nav','vote','quiz','product','category','brand','lifeservice','photoalbum','branches','coupons','impression','introduction','room','resturant','order','album','contact','statistic']) {
//					session.currentUrl = "/${controllerName}/${actionName}"
//				}
			}
		}
	}
}
