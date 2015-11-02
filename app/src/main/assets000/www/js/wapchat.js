

var purl  =  "http://app.fmscms.com";

var socket = io(localStorage.getItem('nodejs'));

	//$("#chat_hall").append(localStorage.getItem('nodejs'));
//var socket =  io("http://182.92.65.176:1717");
var online_num = 0; 

socket.on('cnn', function(data) {
	

	
	/*if(data.state==1)
	{
		alert('你已被踢出房间');
		window.location.href="index.html";
		return 0;
	}*/
	
	_show.enterChat = 1;
	
	//进入房间时欢迎
	var lastindex = data.length-1;

	var lastusername = data[lastindex]['nickname'];
	$(function(){

		$("#chat_hall").append("欢迎"+lastusername+"进入直播间<br />");
	})

	//当有人连接时返回房间人数
	var usrArr = new Array();
	var userlist='';
	for(var i in data){
		console.log(data[i])
		if(data[i]!=null){
			if(data[i]['roomnum']==roomid){
				usrArr.push(data[i]['uid']);
				userlist+=data[i]['uid']+",";
				
			}
			
		}
		
	}
	var num=userlist.split(",");

	userlist=userlist.substr(0,userlist.length-1);
	//var username = data.usernameq7654321.split(",");
	$(".tip_onlinenum").html(num.length);//房间人数;

	$("#guanzhong_ul").html("");
	$("#to_nickname").html("<option value='0'>全部人</option>");
	
	$.getJSON(purl+"/index.php/Api/getuserlist?callback=?&users="+userlist,function(data2){
		
		for (var i = 0; i < usrArr.length; i++) {
		//alert(data.use rname);

       
			if(data2[i]['vip']>0){
				$("#guanzhong_ul").append("<li style='clear: both;'><img width='40px' src="+purl+"/passport/avatar.php?uid="+data2[i].ucuid+"/><div class='rank_div'>"+data2[i].username+"<img style='float: right;' width='35px' src='img/leve/wealth10.png' /></div><hr class='hr'/></li>");
				//$("#guanzhong_ul").append(" <li class='list-group-item'> <div>vip "+data2[i]['username']+"</div><div class='btn-group'><button class='btn btn-default' onclick='shutup("+data2[i]['id']+")'>禁言</button><button class='btn btn-default' onclick='Kick("+data2[i]['id']+")'>踢人</button><button class='btn btn-default' onclick='huifu("+data2[i]['id']+")'>恢复发言</button></div></li>");
				
				$("#to_nickname").append("<option value='" + usrArr[i].uid + "'>" + data2[i]['username'] + "<a  href='#'></a></option>"); //显示在页面
			}else{
				var getrid = purl + "/index.php/Api/getrid?callback=?&roomid="+roomid;
		     	//获取rid
		     	
		        $("#guanli_ul").append("<li style='clear: both;'><img width='40px' src="+purl+"/passport/avatar.php?uid="+data2[i].ucuid+"/><div class='rank_div'>"+data2[i].username+"<img style='float: right;' width='35px' src='img/leve/wealth10.png' /></div><hr class='hr'/></li>");
				
			}
      }
		
	});
	
 
});




/********************* 聊天开始 ************************************/
//处理私聊信息		   
socket.on('pchat', function(data) {
	send_veiw(data.nickname, data.text, data.touserid, data.tousername, 1);
}); 

//处理公聊信息
socket.on('msg', function(data) {

	var det = nodeFace.de(data.text);
	send_veiw(data.username, det, data.touserid, data.tousername, 0,data.u_roomid);
});
 
//初始化房间ID
function init_roomid(uid, roomid, nickname,username) {

	try{
		socket.emit('cnn', {
		uid: uid,
		roomnum:roomid,
		nickname: nickname,
		username:username
		});		
	}catch(e){
		alert(e);
	}

}



//发送信息函数
/*
			uid:发送用户id
			roomid:房间id
			text:发送内容
			msgType:0公聊，1私聊，2，飞屏
			touid：发送至id
			tousername:发送人至用户名
			username:发送人
			sid :验证秘钥 
		  */
		// nodeSend(_show.emceeId, _show.roomId, wval, "0", touserid, tousernmae,$.cookie("nickname"), "4297f44b13955235245b2497399d7a93",$.cookie("u_roomid"),_show.roomId,0);
function nodeSend(uid, roomid, text, msgType, touid, tousername, username,nickname, sid,u_roomid,action) {


	socket.emit('msg', {
		uid: uid,
		roomid: roomid,
		text: text,
		msgType: msgType,
		touid: touid,
		tousername: tousername,
		username: username,
		sid: sid,
		u_roomid:u_roomid,
		action:action,
		nickname:nickname
	});
}


/*
 * 展示信息
 *
 * is_prive:0公聊，1私聊
 */
function send_veiw(username, text, touserid, tousername, is_prive,u_roomid) {

	$(document).ready(function() {
		var date = new Date();
		var h = date.getHours();
		var m = date.getMinutes();
		ftime = h + ":" + m;
/*		if (is_prive == 0) 
		{

			if(_show.vip>0){
				if(tousername=="全部人"){
					$("#chat_hall").append(ftime + " <font color='red'>" + username + "</font><font color='orange'>:</font><b>" + text + "</b><br />");
				}else{
					$("#chat_hall").append(ftime + " <font color='red'>" + username + "</font>对<font color='orange'>" + tousername + ":</font><b>" + text + "</b><br />");
				}
				
			}else{
				if(tousername=="全部人"){
					$("#chat_hall").append(ftime + " <font color='greenyellow'>" + username + ":</font><b>" + text + "</b><br />");
				}else{
					$("#chat_hall").append(ftime + " <font color='greenyellow'>" + username + "</font><font color='orange'>" + tousername + ":</font><b>" + text + "</b><br />");
				}
				
			}
			

		}
		else 
		{

			
			$("#qiaoqiaohua").append(ftime + " <font color='greenyellow'>" + username + "</font>对<font color='orange'>" + tousername + "</font>说" + text + "<br />");
		}*/
		$("#chat_hall").append("<div style='margin:6px'> <font color='#A9A9A9'>" + username + "：<font color='#6C6C6C' style='font-size:20px'>" + text + "</font></div>");
	});
		} 

/********************* 聊天结束 **************************/




/********************* 礼物开始 *************************/

//发送礼物
function sendGift(
	giftPath,
	giftStyle,
	giftGroup,
	giftType,
	toUserNo,
	isGift,
	giftLocation,
	giftIcon,
	giftSwf,
	toUserId,
	toUserName,
	userNo,
	giftCount,
	userId,
	giftName,
	userName,
	giftId,
	roomid
) {
	socket.emit('sendgift', {
		giftPath: giftPath,
		giftStyle: giftStyle,
		giftGroup: giftGroup,
		giftType: giftType,
		toUserNo: toUserNo,
		isGift: isGift,
		giftLocation: giftLocation,
		giftIcon: giftIcon,
		giftSwf: giftSwf,
		toUserId: toUserId,
		toUserName: toUserName,
		userNo: userNo,
		giftCount: giftCount,
		userId: userId,
		giftName: giftName,
		userName: userName,
		giftId: giftId,
		roomid: roomid
	});
}


//礼物返回信息
socket.on('sendgift', function(data) {
		//处理礼物返回信息
		console.log("礼物返回信息");
		console.log(data);

        $("#chat_hall").append("<span>"+data.userName+"向"+data.toUserName+"赠送了"+data.giftCount+"个<img src='http://app.fmscms.com"+data.giftPath+"'/>"+data.giftName+"</span><br/>");
	})
	/********************* 礼物结束 *************************/

/*******************抢座开始******************************/

//发送抢座请求

function fetchSofa(userNick, userIcon, seatId, seatPrice, roomid) {
	socket.emit('sofa', {
		userNick: userNick,
		userIcon: userIcon,
		seatId: seatId,
		seatPrice: seatPrice,
		roomid: roomid
	});
}


//接收返回参数

socket.on('sofa', function(data) {
	//处理礼物返回信息

	//.........
	alert(data.userNick);

})





/*******************抢座结束******************************/


/*********************禁言开始****************************/

//禁言操作
function ShutUp(uid, roomid, username, time) {
	socket.emit('shutup', {
		uid: uid,
		roomid: roomid,
		username: username,
		time: time
	});
}

//监听禁言事件
socket.on('shutup', function(data) {
	alert("已被禁言");
})

//恢复发言
function ResumeUser(uid, roomid, username, time) {
	socket.emit('resumeuser', {
		uid: uid,
		roomid: roomid,
		username: username,
		time: time
	});
}

//监听恢复事件
socket.on('resumeuser', function(data) {
	alert(data);
})



/*******************禁言结束**************************/


/*************** 踢人开始 **************************/

//踢人操作
function KickUser(uid, roomid, username, time) {
	alert(username);
	socket.emit('kickuser', {
		uid: uid,
		roomid: roomid,
		username: username,
		time: time
	});
}

//监听踢人事件

socket.on('kickuser', function(data) {
	if(data=="1")
	{
		alert("操作成功！");
	}
	else if(data=="2")
	{
		alert('你已被踢出房间');
		window.location.href="index.html";
	}
})

/***************** 踢人结束 ***********************/

