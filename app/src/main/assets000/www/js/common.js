/**
 * 判断浏览器
 */

 
var purl="http://app.fmscms.com";
// 判断扩展API是否准备，否则监听"plusready"事件
// 创建页面 预加载 
//是否需要更新
/*$.ajax({ 
		    	type:"get",
		    	url:purl+"/index.php/Api/update_server_info/act/getappupdate",
		    	type:"jsonp",
		    	async:true,
		    	success:function(data){
		    		//alert(data);
		    		var data=eval("("+data+")");
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
		    	}
		    });*/
var isupdate=localStorage.getItem('isupdate'); 
//是否更新数据 2015-9-14 修改 杨
$.getJSON(purl+"/index.php/Api/update_server_info/act/getappupdate?callback=?", function(data) {
	

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
			history.go(-1);
			
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
	/*if(_openw){return;}
	 
	a||(a=as);
	_openw=preate[id];
	
	if(_openw){
		console.log(" preate"+id+"  no   null");
		_openw.showded=true;
		_openw.show(a,null,function(){
			_openw=null;//避免快速点击打开多个页面
		});
	}else{
			console.log("preate "+id+" null");
		var wa=plus.nativeUI.showWaiting();
		_openw=plus.webview.create(id,id,{scrollIndicator:'none',scalable:false,popGesture:'hide'},{preate:true});
		preate[id]=_openw;
		_openw.addEventListener('loaded',function(){//叶面加载完成后才显示
		setTimeout(function(){//延后显示可避免低端机上动画时白屏
			wa.close();
            if(_openw!=null)
			_openw.showded=true;
			s||_openw.show(a,null,function(){
				_openw=null;//避免快速点击打开多个页面
			});
			s&&(_openw=null);//避免s模式下变量无法重置
		},20); 
		},false);
		_openw.addEventListener('close',function(){//页面关闭后可再次打开
			_openw=null;
			preate[id]&&(preate[id]=null);//兼容窗口的关闭
		},false); 
	}*/
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


//表情
var nodeFace={
	de:function(str){
		str=str.replace(/<br \/>/ig, '\n').replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/(\n)+/igm, "<br>").replace(/\[`(.*?)`\]/ig,"<img src=\"Public/images/face/$1.gif\"/>");
		return str;
	},
	showFace:function(){
		var objFace=$('#showFaceInfo'),chatR=$("#ChatFace"),facePos={'facel':objFace.offset().left,'facet':objFace.offset().top};
		if(chatR.is(':hidden')){
			chatR.css({"left":(facePos.facel)+"px","top":(facePos.facet-182-11)+"px"}).show();
		}else{
			chatR.hide();
		}
	},
	deimg:function(str){
		str=str.replace(/\[`(.*?)`\]/ig,"");
		return str;
	},
	addEmot:function(myValue) {
		var objEditor=$("#msg");
		objEditor.val(objEditor.val()+myValue);
		$('#ChatFace').hide();
		objEditor.focus();
	}
};

//模糊查询列表

function search_list()
{
	$(".search_select").show();
	
	$(".search_select").html("<button onclick='close_search()'>关闭</button>");
	
	var re = /^[a-zA-Z][a-zA-Z0-9]*$/;
	
	var nickname = $("#inputbox").val();
	var res = re.exec(nickname);
	if(res)
	{
		$.ajax({
			url:purl+"/index.php/Appapi/Search_list/nickname/"+nickname,
			dataType:"jsonp",
			success:function (data)
			{	
				var i;
				for(i = 0;i<data.length;i++)
				{
					$(".search_select").append("<div class='line_div'><a href='show.html?roomid="+data[i].curroomnum+"'>"+data[i].nickname+"</a></div>");	
				}
			}
		});
	}

}
  
function close_search()
{
	$(".search_select").hide();
}
