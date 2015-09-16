<!DOCTYPE html>
<html>
<head>
<title>微盘</title>
<meta name="layout" content="app"/>
<link  href="${request.contextPath }/css/home.css" rel="stylesheet">
%{-- <script type="text/javascript" src="http://hq.sinajs.cn/list=hf_CL"></script> --}%
<style type="text/css">
.fa-sort-asc{
	vertical-align: -50%;
}
.fa-sort-desc{
	vertical-align: 15%;
}
</style>
</head>
<body style="background: #F8F9FA;">
	<input type="hidden" id="openid" name="openid" value="${openid}" />
	<input type="hidden" name="companyId" value="${company?.id}" />
	<section class="embed-drop birth-vip" id="tellme">
		<div class="embed-bg">
			<div class="embed-cake">
			
			</div>
		</div>
	</section>

	<header class="embed-header" style="35px;"><!-- 超级会员 -->
		<div class="embed-dear">
		</div>
		<div class="embed-tbar">
			<a href="${request.contextPath }/release/user?id=${member?.id}"><b class="btn-ucenter"><span>个人中心</span></b></a>
			<b class="btn-checkin"><a href="${request.contextPath }/release/addMoney?id=${member?.id}"><span style="color:#fff;">充值</span></a></b>
		</div>
	</header>

	<article class="embed-article">
		<section class="mb10">
			<div class="embed-congra mb10">
				<div class="embed-congra-in mb10">
					<p class="c1">恭喜您加入微盘！</p>
					<p class="c2">加入<span class="em">微盘</span>,财富更进一步！</p>
				</div>
			</div>	
		</section>
		<section class="embed-hotact mb10">
			<h2>大盘走势</h2>
			<div class="sec-bd">
				<ul class="embed-actlist">
					<li>
						<div class="ui-list-info b-color-red li-div-top" id="money1">
				            <h4>白银报价</h4><br>
				            <h5  ><i class="fa fa-sort-asc fa-lg"> </i> <span>休市</span></h5>
				        </div>
				    </li>
				    <li>
						<div class="ui-list-info b-color-red li-div-top" id="money2">
				            <h4 >铜报价</h4><br>
				            <h5 ><i class="fa fa-sort-asc fa-lg" > </i> <span>休市</span></h5>
				        </div>
				    </li>
				    <li>
						<div class="ui-list-info b-color-red li-div-top" id="money3">
				            <h4 >原油报价</h4><br>
				            <h5 ><i class="fa fa-sort-asc fa-lg"> </i> <span>休市</span></h5>
				        </div>
				    </li>
				</ul>
			</div>
		</section>
		<section class="embed-button mb10">
			<button class="embed-btn-common">个人帐户：<g:formatNumber number="${member?.money }" format="0.00"/> 元</button>
		</section>
		<section class="embed-hotact mb10">
		   <div style="height: 30px;">
	            <h2 style="float: left;" class="title">交易产品</h2>
	            <h2 style="float: right;" class="action">规则</h2>
	        </div>
			<div class="sec-bd">
					
						<g:each in="${lists }" var="pro" status="i">
						   <g:if test="${i%3==0 }">
						   		<ul class="embed-actlist">
						   </g:if>
							<li>
								<div class="ui-list-info li-div-product border1px">
									<button class="ui-btn ui-btn-green  upordown" data-code="${pro?.code}"  data-type="-1" data-id="${pro?.id }">买 跌</button>
									<div class="tits">${pro?.name }</div>
									<div class="txts">${pro?.weight }${pro?.unit?.name }</div>
									<div class="txts"><span class="em">${pro?.price }</span>元/手</div>
									<div class="txts">波动盈利:<span class="em">${pro?.profit }</span></div>
									<button class="ui-btn ui-btn-danger upordown" data-code="${pro?.code}" data-type="1" data-id="${pro?.id }">买 涨</button>
	
						        </div>
							</li>
							   <g:if test="${(i+1)%3==0}">
							   		</ul>
							   		<br>
							   </g:if>
						</g:each>
				</div>
		</section>
		<section class="embed-vipteq mb10">
			<h2>预言家资讯</h2>
			<div class="sec-bd">
				<div class="embed-teqlist">
					<ul class="border1px-tb">
						<li>
							<div class="teqlist-con border1px-b">
								<div class="tit nowrap">晚盘财经数据</div>
							<div>
						</div></div></li>
						<li>
							<div class="teqlist-con border1px-b">
								<div class="tit nowrap">6月4日机构持仓变化</div>
							<div>
						</div></div></li>
						<li>
							<div class="teqlist-con border1px-b">
								<div class="tit">隔夜要闻：原油创最近五个交易日最低收盘位</div>
							<div>
						</div></div></li>
						<li>
							<div class="teqlist-con border1px-b">
								<div class="tit">关于调整交易时间通知</div>
							<div>
						</div></div></li>
						<li>
							<div class="teqlist-con border1px-b">
								<div class="tit">新手指南</div>
							<div>
						</div></div></li>
					</ul>
				</div>
			</div>
		</section>
	</article>
	
	<div class="ui-dialog"> </div>
	
	
	<footer class="embed-footer" style="display:none;">
		<button class="embed-btn-common"><img src="http://imgcache.gtimg.cn/vipstyle/mobile/embed/v2/img/symbol.png" width="17">2015</button>
	</footer>
	



<script>


window.onload = function() {
    voices = speechSynthesis.getVoices();
    setTimeout(function(){
    	$(".embed-drop").addClass("dropdown");
    },2000);
}
$(document).ready(function($) {
	refresh();
	setInterval(refresh,5000);
});

var FU0,AG0,CU0;
function refresh(){
    var m1 = $("#money1");//白银
    var m2 = $("#money2");//铜
    var m3 = $("#money3");//油
	
	
	$.getScript("http://hq.sinajs.cn/list=FU0,AG0,CU0",function(res){
		var newyy = parseInt(hq_str_FU0.split(",")[8]);
		var oldyy = parseInt(hq_str_FU0.split(",")[5]);
		var newby = parseInt(hq_str_AG0.split(",")[8]);
		var oldby = parseInt(hq_str_AG0.split(",")[5]);
		var newtb = parseInt(hq_str_CU0.split(",")[8]);
		var oldtb = parseInt(hq_str_CU0.split(",")[5]);
		if(newyy>oldyy){
			m3.find("i").removeClass("fa-sort-desc").addClass("fa-sort-asc");
			m3.removeClass("b-color-green").addClass("b-color-red"); 
		}else{
			m3.find("i").removeClass("fa-sort-asc").addClass("fa-sort-desc");
			m3.removeClass("b-color-red").addClass("b-color-green");
		}
		m3.find("span").text(newyy);
		FU0 = newyy;
		if(newby>oldby){
			m1.find("i").removeClass("fa-sort-desc").addClass("fa-sort-asc");
			m1.removeClass("b-color-green").addClass("b-color-red"); 
		}else{
			m1.find("i").removeClass("fa-sort-asc").addClass("fa-sort-desc");
			m1.removeClass("b-color-red").addClass("b-color-green");
		}
		m1.find("span").text(newby);
		AG0 = newby;
		if(newtb>oldtb){
			m2.find("i").removeClass("fa-sort-desc").addClass("fa-sort-asc");
			m2.removeClass("b-color-green").addClass("b-color-red"); 
		}else{
			m2.find("i").removeClass("fa-sort-asc").addClass("fa-sort-desc");
			m2.removeClass("b-color-red").addClass("b-color-green");
		}
		m2.find("span").text(newtb);
		CU0 = newtb;
		addNum();
	});
		
}
var code;
function addNum(){
	var val1;
	if(code=="CU0"){val1=CU0;}
	if(code=="AG0"){val1=AG0;}
	if(code=="FU0"){val1=FU0;}
	$("#dpj").val(val1);
	$("#dpjshow").text(val1);
}



$(".upordown").tap(function(e){
	var id = $(this).attr("data-id");
	var type = $(this).attr("data-type");
	code = $(this).attr("data-code");
	var openid = $("#openid").val();
	
	$.ajax({
		type:"POST",
		url:app.contextPath+"/release/buyTempl",
		data:{id:id,type:type,openid:openid},
		success:function(res){
			$(".ui-dialog").html(res);
			addNum();
			$(".ui-dialog").dialog("show");
		}
		
	});
});

</script>

  </body>
  </html>