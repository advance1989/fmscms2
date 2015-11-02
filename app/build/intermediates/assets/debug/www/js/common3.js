/**
 * 判断浏览器
 */

 
var purl="http://zhibo.1198.com";
// 判断扩展API是否准备，否则监听"plusready"事件
// 创建页面 预加载 
//是否需要更新

var isupdate=localStorage.getItem('isupdate'); 
//是否更新数据 2015-9-14 修改 杨
$.getJSON(purl+"/index.php/Api/update_server_info/act/getappupdate", function(data) {
	

        var update=localStorage.getItem('update');
        
        if(update==undefined){
        	
        	localStorage.setItem('update',data.isupdate);
        	localStorage.setItem('isupdate','true');
        }else{
        	
        	if(update!=data.isupdate){
        		localStorage.setItem('update',data.isupdate);
        		localStorage.setItem('isupdate','true');
        	}else{ 
        	
        		localStorage.setItem('isupdate','false');
        	}
        }   
} )


 
 

//周榜更新时间
var weekuptime = 1000*60*60;

//日榜更新时间
var dayuptime = weekuptime*24;
 
//月榜更新时间

var monuptime = weekuptime*24;

//取消浏览器的所有事件，使得active的样式在手机上正常生效
document.addEventListener('touchstart',function(){
    return false;
},true);
// 禁止选择
document.oncontextmenu=function(){
	return false;
};
// H5 plus事件处理
var as='pop-in';// 默认窗口动画
var ws=null;
var list=null;
var i=0;
function plusReady(){
	
	// Android处理返回键 
	plus.key.addEventListener('backbutton',function(){
		if(pagename=="index")
		{
			if(confirm('确认退出？')){
				plus.runtime.quit();
			}
		}
		else
		{
			if(pagename!="edit")
			{
				history.go(-1);
			}
			else
			{
				window.location.href = "userinfo.html"; 
			}
			  
		}
		

		
	},false);
	compatibleAdjust();
}

if(window.plus){
	plusReady();
}else{
	document.addEventListener('plusready',plusReady,false);
}
// 刷新页面



// DOMContentLoaded事件处理
var _domReady=false;
document.addEventListener('DOMContentLoaded',function(){
	_domReady=true;
	compatibleAdjust();
},false);
// 兼容性样式调整
var _adjust=false;
function compatibleAdjust(){
	if(_adjust||!window.plus||!_domReady){
		return;
	} 
	_adjust=true;

	// 预创建二级窗口
 //	preateWebviews();
 
 if(pagename!="order"){
 	preateWebivew('order.html');
 }
 
	// 关闭启动界面
	setTimeout(function(){
		plus.navigator.closeSplashscreen();
	},500);
}
// 处理点击事件
var _openw=null;
function clicked(id,a,s){
	
    
	window.location.href=id;

}  
// 预创建二级页面
var preate={};
function preateWebviews(){
	preateWebivew('order.html');
	preateWebivew('index.html');
 
}
function preateWebivew(id){
	 
	if(!preate[id]){
		var w=plus.webview.create(id,id,{scrollIndicator:'none',scalable:false,popGesture:'hide'},{preate:true});
		preate[id]=w;
       
		w.addEventListener('close',function(){//页面关闭后可再次打开
			_openw=null;
			preate[id]&&(preate[id]=null);//兼容窗口的关闭
		},false);
	}
}
// 清除预创建页面(仅)
function preateClear(){
	for(var p in preate){
		var w=preate[p];
		if(w&&w.showded&&!w.isVisible()){
			w.close();
			preate[p]=null;
		}
	}
}
		 

//创建页面 预加载结束
var Sys={};
var Gift_obj={};
var Gift_numobj={};
var ua=navigator.userAgent.toLowerCase();
Sys.ie=(s=ua.match(/msie ([\d.]+)/)) ? true : false;
Sys.ie6=(s=ua.match(/msie ([0-6]\.+)/)) ? s[1] : false;
Sys.ie7=(s=ua.match(/msie ([7]\.+)/)) ? s[1] : false;
Sys.ie8=(s=ua.match(/msie ([8]\.+)/)) ? s[1] : false;
Sys.firefox=(s=ua.match(/firefox\/([\d.]+)/)) ? true : false;
Sys.chrome=(s=ua.match(/chrome\/([\d.]+)/)) ? true : false;
Sys.opera=(s=ua.match(/opera.([\d.]+)/)) ? s[1] : false;
Sys.safari=(s=ua.match(/version\/([\d.]+).*safari/)) ? s[1] : false;
Sys.ie6&&document.execCommand("BackgroundImageCache",false,true);
Sys.ispro="";//是否推广url过来
String.prototype.hasString=function(a){
	if(typeof a=="object"){
		for(var b=0,c=a.length;b<c;b++)
		   if(!this.hasString(a[b]))
		   	  return false;
			return true
	}else if(this.indexOf(a)!=-1)
		return true
};



 function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
    var context = "";  
    if (r != null)  
         context = r[2];   
    reg = null;   
    r = null;   
    return context == null || context == "" || context == "undefined" ? "" : context;  
}
/**
 * 计算位置
 */
var dom=document.documentElement || document.body;
var oPos={
	width:function(a){return parseInt(a.offsetWidth)},
	height:function(a){return parseInt(a.offsetHeight)},
	pageWidth:function(){return document.body.scrollWidth||document.documentElement.scrollWidth},
	pageHeight:function(){return document.body.scrollHeight||document.documentElement.scrollHeight},
	windowWidth:function(){var a=document.documentElement;return self.innerWidth||a&&a.clientWidth||document.body.clientWidth},
	windowHeight:function(){var a=document.documentElement;return self.innerHeight||a&&a.clientHeight||document.body.clientHeight},
	scrollX:function(){
	  var b=document.documentElement;
	  return self.pageXOffset||b&&b.scrollLeft||document.body.scrollLeft
	}
	,scrollY:function(){
	  var b=document.documentElement;
	  return self.pageYOffset||b&&b.scrollTop||document.body.scrollTop
	},
	popW:function(){return Math.max(dom.clientWidth,dom.scrollWidth)},
	popH:function(){return Math.max(dom.clientHeight,dom.scrollHeight)}
}
var mousePosition=function(e){
  var e=e || window.event;
  return {x:e.clientX+oPos.scrollX(),y:e.clientY+oPos.scrollY()}
};

/**
 * 获取layer居中的位置
 */
var getMiddlePos=function(obj){
	this.objPop=obj;
	this.winW=oPos.windowWidth();  
    this.winH=oPos.windowHeight();  
	this.dScrollTop=oPos.scrollY();
	this.dScrollLeft=oPos.scrollX();
	this.dWidth=$('#'+this.objPop).width(),dHeight=$('#'+this.objPop).height();
	this.dLeft=(this.winW/2)-(this.dWidth)/2+this.dScrollLeft;
	this.dTop=(this.winH/2)-(this.dHeight/2)+this.dScrollTop;
	return {"pl":this.dLeft,'pt':this.dTop};
}

/**
 * read html ? param
 * @param strName:需要获取参数的名字
 * @return string
 */
function getParam(strName)
{
	var arg=arguments[1]?arguments[1]:"";
	var strHref=location.href;
	if(arg!=""){
		strHref=arg;	
	}
	var intPos=strHref.indexOf("?");
	var strRight=strHref.substr(intPos + 1);
	var arrTmp=strRight.split("&");
	for(var i=0;i<arrTmp.length;i++)
	{
		var arrTemp=arrTmp[i].split("=");
		if(arrTemp[0].toUpperCase()==strName.toUpperCase()) return arrTemp[1];
	}
	return "";
}
/**
 * jump location.href
 * @param urlfile:跳转URL
 */
var jump=function(urlfile)
{
	window.location.href=urlfile;
	return false;
}
/**
 * 将JSON字符串转换成object
 * @param evalJSON:json string
 * @return json object
 * */
function evalJSON(strJSON)
{
	if (typeof(strJSON)=='undefined'||strJSON=='')
		return false;

	return eval("("+ strJSON +")");
}

/**
 * 是否在数组内
 */
function in_array(searcher,sArray)
{
	for(var i=sArray.length;i--;)
	{
		if(searcher==sArray[i])// TODO 应该用===
		{
			return true;
		}
	}
	return false;
}

function jmsgPop(txt,showtime){
	var box=$('#giveBox');
	if(txt){$('#pop-text').html(txt);}
	var alertPop=getMiddlePos('giveBox');
	var vl=alertPop.pl;
	var vt=alertPop.pt;
	box.css({"left":vl+"px","top":vt-100+"px"}).show(); 
	$('#pop-close,#pop-btnclose').click(function(){
		box.hide();
	})
	if(isNaN(showtime)){
		box.hide();
	}else{
		window.setTimeout(function(){
			box.hide();					   
		},showtime*1000)
	}
}

function addEvent(obj,type,fn){
	if (obj.attachEvent){
		obj['e'+type+fn]=fn;
		obj[type+fn]=function(){
			obj['e'+type+fn](window.event);
		}
		obj.attachEvent('on'+type,obj[type+fn]);
	} else
	 obj.addEventListener(type,fn,false);
}
function removeEvent(obj,type,fn){
	if (obj.detachEvent){
		obj.detachEvent('on'+type,obj[type+fn]);
		obj[type+fn]=null;
	} else
	 obj.removeEventListener(type,fn,false);
}
/*
 * jQuery.JShowTip({centerTip:$('#signuplogin_tip')}); 居中显示POP Tip
 * $(document).trigger('close.JoyShowTip')
 * */
(function($){
		  $.JShowTip=function(data){$.JShowTip.loading(data);}
		  $.extend($.JShowTip,{
			  settings:{opacity:'0.2'},
			  loading:function(_tarwap){
							showOverlay();
						    _tarwap.centerTip.css({
								"left":$(window).width()/2-Math.ceil($('.poptip').width()/2),
								"top":oPos.scrollY()+(oPos.windowHeight()/3),
								"z-index":100
							}).show();
							if(_tarwap.param){
								var param_obj=_tarwap.param;
								for(var key in param_obj[0]){
									$('#'+key).html(param_obj[0][key]);
								}
							}
							$(document).bind('keydown',function(e){
								if(e.keyCode==27){//esc 关闭
									$(document).trigger('close.JShowTip');
								}
							});
					   }
		  });
		  function showOverlay(){
				$('body').append('<div id="face_overlay"></div>');
				$('#face_overlay').hide().addClass('face_overlayBG')
					.css({opacity:$.JShowTip.settings.opacity,height:oPos.popH()})
					.click(function(){
						$(document).trigger('close.JShowTip');
				}).fadeIn(200);
				$('.close').click(function(){
					$(document).trigger('close.JShowTip');						   
				})
		  }
		  function hideOverlay(){
				$this=$('.poptip,.p-Song');
				$this.fadeOut(200,function(){
					$('#face_overlay').remove();
				});
		  }
		  $(document).bind('close.JShowTip',function(){
				$(document).unbind('keydown');
				hideOverlay();
		  });
})(jQuery);

/**
	标签
*/
function turn(n,m,x){
	for(i=1;i<=m;i++){
		if(i==n){
			jQuery('#lm'+x+'_'+i).addClass("on");
			jQuery('#content'+x+'_'+i).css("display","block");
		}else{
			jQuery('#lm'+x+'_'+i).removeClass("on");
			jQuery('#content'+x+'_'+i).css("display","none");
		}
	}
}

/**
 * 滚动
 * @name HtmlMove
 * @grammar HtmlMove("play_triger","cf","scrollLeft","play_pre","play_next");
 * @param {string} 	fid 		父容器节点
 * @param {string} 	fnodename 		要遍历的节点
 * @param {string} 	path 		滚动方向
 * @param {string} 	prebtn 		往上滚 按钮 ID
 * @param {string} 	nextbtn 	往下滚 按钮 ID
 */
var HtmlMove=function(fid,fnodename,path,prebtn,nextbtn){
	var h=$('#'+fid),pre=$('#'+prebtn),next=$('#'+nextbtn),pageCount=h.find('.'+fnodename).size(),intIndex,scrollW=0,scrollval;
	pre.addClass('dis');
	intIndex=0;
	if(path=='scrollLeft'){
		var oneset=parseInt(h.find('.'+fnodename).eq(0).width());
	}else{
		var oneset=parseInt(h.find('.'+fnodename).eq(0).height());
	}
	if(pageCount==1){
		pre.addClass('dis');next.addClass('dis');
	}else{
		pre.addClass('dis');next.removeClass('dis');
	}
	var dur=300;
	pre.click(function(){
		if(intIndex>0){changePage(intIndex-1);}
	})
	next.click(function(){
		if(intIndex<pageCount-1){changePage(intIndex+1);}
	})
	function changePage(pageNum){
		intIndex=pageNum;
		if(intIndex>0 && intIndex<pageCount-1){
			pre.removeClass('dis');
			next.removeClass('dis');
		}else if(intIndex==0){
			pre.addClass('dis');
			next.removeClass('dis')	
		}else{
			next.addClass('dis');pre.
			removeClass('dis');	
		}
		scrollval=parseInt(intIndex*oneset);
		if(path=='scrollLeft'){
			h.stop().animate({'scrollLeft':scrollval},dur,'linear');
		}else{
			h.stop().animate({'scrollTop':scrollval},dur,"linear");
		}
		
	}
}

/*
  图片滚动
 */	
//滚动/切屏效果，参数说明:[id,子容器/孙容器,方向,速度,上按钮,下按钮,分页切换时间,每次切屏的条数]
function HtmlMove2(id,tag,path,upbt,downbt,pgtime,lis){
		var c,mous=false,fg=tag.split('/'),o=$('#'+id),as=o.find(fg[1]),fx=(path=='scrollRight'||path=='scrollLeft')?'scrollLeft':'scrollTop',
		ow=fx=='scrollTop'?as.eq(0).get(0).offsetHeight:as.eq(0).get(0).offsetWidth;
		o.hover(function(){mous=true;},function(){mous=false;})
		var pgsize=as.size();
		var pw=fx=='scrollTop'?o.height():o.width(),pgli=lis||Math.floor((pw+ow/2)/ow),pg=Math.floor((pgsize+(pgli-1))/pgli),pgmx=ow*pgli,now=0,mx,d;
		var os=o.find(fg[0]).eq(0),dur=600;	
		o.find(fg[0]).append(os.html());	
		if(pgtime==null){$('#'+upbt).click(function(){go_to(true);});$('#'+downbt).click(function(){go_to(false);});}else{d=setInterval(function(){go_to((path=="scrollTop"||path=="scrollLeft")?true:false);},pgtime);$('#'+upbt).click(function(){clearInterval(d);go_to(true);d=setInterval(function(){go_to(true);},pgtime);});$('#'+downbt).click(function(){clearInterval(d);go_to(false);d=setInterval(function(){go_to(false);},pgtime);});}	
		 function go_to(fxs){
			 	if(mous){return;};
				if(fxs){
						if(now<pg){
							now++;
						}else{
							now=1;
							if(fx=='scrollTop'){
								o.scrollTop(0);
							}else{
								o.scrollLeft(0);	
							}
						}
						mx=now*pgmx;
						if(fx=='scrollTop'){
							o.stop().animate({'scrollTop':mx},dur,'linear');
						}else{
							o.stop().animate({'scrollLeft':mx},dur,'linear');
						}
						
				}else{
					if(now>0){
						now--;
					}else{
						now=pg-1;
						if(fx=='scrollTop'){
								o.scrollTop(pg*pgmx);
						}else{
								o.scrollLeft(pg*pgmx);	
						}
					}
					mx=now*pgmx;
					if(fx=='scrollTop'){
						o.stop().animate({'scrollTop':mx},dur,'linear');
					}else{
						o.stop().animate({'scrollLeft':mx},dur,'linear');
					}
				}
			}
}
/**
	焦点图
*/
var FocusImages=function(){
	var FocusImage=arguments[0];
	this.id=jQuery(FocusImage.id);
	this.imgW=parseInt(FocusImage.imgW);
	this.imgH=parseInt(FocusImage.imgH);
	this.speed=parseInt(FocusImage.speed)||3000;
	this.focusData=arguments;
	this.autoPlay;
	this.links="";
};
FocusImages.prototype={	
	init:function(){
		var that=this;
		var strSpan='',strImg='';
		for(var i=1;i<that.focusData.length;i++){
			strSpan+="<span></span>";
			strImg+='<img src="'+that.focusData[i][0]+'" width="'+this.imgW+'" height="'+this.imgH+'" style="display:none;">';
		};
		that.id.find('.sildetab').html(strSpan);
		that.id.find('.slideimg').html(strImg);
		this.spring();
	},
	spring:function(){
		var that=this,_tab=that.id.find(".sildetab span");
		var gogo=function(i){
			var _index=i||1;
			that.alternation(_index);
			that.autoPlay=setInterval(function(){
				that.alternation(_index==that.focusData.length-1?_index=1:++_index);
			},that.speed);
		};
		gogo();		
		_tab.each(function(i){
			(function(i){
				_tab.eq(i).click(function(){
					if(jQuery(this)[0].className!="on"){
						clearTimeout(that.autoPlay);
						gogo(i+1)
					}
				})
			})(i)
		});
		this.id.find(".slideimg").click(function(){window.open(that.links);})
	},
	alternation:function(i){
		var that=this;
		that.id.find(".sildetab span.on").removeClass();
		that.id.find(".sildetab span").eq(i-1).addClass("on");
		var slideImg=that.id.find("img");
		slideImg.css({display:"none",opacity:1});
		slideImg.eq(i-1).css({display:"block",opacity:0}).stop().animate({opacity:1},300);
		that.links=that.focusData[i][1];
	}
};
/*===========top menu begin===========*/
/*$('.mycenter').live("mouseenter",function(){
	$(this).find('.mysetting').show();
}).live("mouseleave",function(){
	$(this).find('.mysetting').hide();
})*/
/*============top menu end============*/
/*===========================right Menu begin=====================================*/
function isInRegion(a,x,y){
	try{
		var b=a.offset();
		var c=b.left;
		var d=b.top;
		var e=d + a.height();
		var f=c + a.width();
		return x > c && x < f && y > d && y < e;
	}catch(e){}
}
/*$('.hover_des li').live("mouseenter",function(){
		$(this).addClass("hover");
	}).live("mouseleave",function(){
		$(this).removeClass("hover");
	});*/
function set_contextmenu(a,c,d){
	var e=$('#hoverPerson');
	var f=c.offset();
	var g=d == null || d=='right'?f.left-e.width()-8:f.left+c.width()-10;
	$('#person_title').text(a);
	e.css("left", g).css("top", f.top).show();
}
  