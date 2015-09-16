			<a class="menu-toggler" id="menu-toggler" href="#">
				<span class="menu-text"></span>
			</a>

			<div class="sidebar" id="sidebar">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>
				<%--
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="icon-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="icon-pencil"></i>
						</button>

						<button class="btn btn-warning">
							<i class="icon-group"></i>
						</button>

						<button class="btn btn-danger">
							<i class="icon-cogs"></i>
						</button>
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- #sidebar-shortcuts -->
 				--%>
				<ul class="nav nav-list">
					<li class="active">
						<a href="${request.contextPath}/admin/index">
							<i class="icon-dashboard"></i>
							<span class="menu-text"> 控制台 </span>
						</a>
					</li>

					<li class="${controllerName=='wechat'?'active':''}">
						<a href="#" class="dropdown-toggle">
							<i class="icon-text-width"></i>
							<span class="menu-text"> 微信设置 </span>
							<b class="arrow icon-angle-left"></b>
						</a>
						<ul class="submenu">
							<li>
								<a href="tables.html">
									<i class="icon-double-angle-right"></i>
									基础设置
								</a>
							</li>

							<li>
								<a href="jqgrid.html">
									<i class="icon-double-angle-right"></i>
									菜单设置
								</a>
							</li>
						</ul>
					</li>

					<li class="${controllerName in ['product','member','order']?'active':''}">
						<a href="#" class="dropdown-toggle">
							<i class="icon-desktop"></i>
							<span class="menu-text">期货</span>

							<b class="arrow icon-angle-left"></b>
						</a>

						<ul class="submenu">
							<li>
								<a href="${request.contextPath}/product/list">
									<i class="icon-double-angle-right"></i>
									期货设置
								</a>
							</li>

							<li>
								<a href="${request.contextPath}/member/list">
									<i class="icon-double-angle-right"></i>
									会员管理
								</a>
							</li>
							<li>
								<a href="${request.contextPath}/order/list">
									<i class="icon-double-angle-right"></i>
									订单管理
								</a>
							</li>
						</ul>
					</li>
					<li class="${controllerName=='news'?'active':''}">
						<a href="#" class="dropdown-toggle">
							<i class="icon-text-width"></i>
							<span class="menu-text">信息设置</span>

							<b class="arrow icon-angle-left"></b>
						</a>

						<ul class="submenu">
							<li>
								<a href="${request.contextPath}/news/list">
									<i class="icon-double-angle-right"></i>
									信息通知
								</a>
							</li>
							<li>
								<a href="${request.contextPath}/news/list">
									<i class="icon-double-angle-right"></i>
									公告信息
								</a>
							</li>							
						</ul>
					</li>

									
					
				</ul><!-- /.nav-list -->

				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
				</div>

				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>
