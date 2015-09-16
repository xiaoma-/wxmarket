<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en"><!--<![endif]-->
	<head>
	    <meta name="keywords" content="微盘" />
		<meta name="description" content="微盘" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta charset="utf-8" />
		<title><g:layoutTitle default="微盘管理"/></title>
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
		<script type="text/javascript"><g:render template="/common/appjs"></g:render></script>
		<asset:javascript src="application.js"/>
		
		<script src="${request.contextPath}/ace/js/jquery-1.10.2.min.js"></script>
		<!-- basic styles -->
		<link href="${request.contextPath }/ace/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${request.contextPath }/ace/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="${request.contextPath }/ace/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->


		<link rel="stylesheet" href="${request.contextPath }/ace/css/ace.min.css" />
		<link rel="stylesheet" href="${request.contextPath }/ace/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${request.contextPath }/ace/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${request.contextPath }/ace/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="${request.contextPath }/ace/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="${request.contextPath }/ace/js/html5shiv.js"></script>
		<script src="${request.contextPath }/ace/js/respond.min.js"></script>
		<![endif]-->
		
		
		

		<script src="${request.contextPath }/ace/js/bootstrap.min.js"></script>
		<script src="${request.contextPath }/ace/js/typeahead-bs2.min.js"></script>
		<!-- ace scripts -->
		<script src="${request.contextPath }/ace/js/ace-elements.min.js"></script>
		<script src="${request.contextPath }/ace/js/ace.min.js"></script>
		
		<!-- inline scripts related to this page -->
		<g:layoutHead/>
	</head>
	<body>
       <!--navbar-->
	   <g:render template="/layouts/navbar"></g:render>
	   <div class="main-container" id="main-container">
	        <script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
	   		<div class="main-container-inner">

	    	<!--menu-->
		     <g:render template="/layouts/menus"></g:render>
			
		    <g:layoutBody/>

		    <!--theme-->
		    <g:render template="/layouts/theme"></g:render>
		    	
	   		</div><!-- /.main-container-inner -->
			     <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
					<i class="icon-double-angle-up icon-only bigger-110"></i>
				 </a>
		</div><!-- /.main-container -->
	</body>
</html>
