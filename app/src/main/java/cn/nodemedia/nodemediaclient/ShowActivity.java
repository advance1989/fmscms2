package cn.nodemedia.nodemediaclient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import cn.weilian.fmscmsutil.Util;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;

public class ShowActivity extends Activity implements Runnable, View.OnClickListener,MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback {
	public Socket mSocket ;
	{
		try {
				 mSocket = IO.socket("http://182.92.65.176:1717");
				//mSocket = IO.socket("http://61.160.245.104:1717");
		} catch (URISyntaxException e) {
			Toast.makeText(this,"服务器连接失败...",Toast.LENGTH_LONG).show();
		}
	};

	private String url;
	private TextView tv1, tv2, tv3,tv5;
	private Button bt4;
	private FragmentManager fm;
	private String  eid="";
	private String  uid="";
	private String  username="";
	private String  nickname="";
	private String  roomid="1";
	private String  ucuid="0";
	private List<Userinfo> userlistinfo;
	private android.app.FragmentTransaction ft;
	private String getjsoninfo=null;
	private User user;
	private MyApplication myApplication;
	private Liaotian lt=null;
	private Gongxian gx=null;
	private Guanzhong gz=null;
	private More more=null;
	private String isatt_emcee="0";
	private ImageButton showbakc_btn;
	//nodeclient
	private static final String TAG = "MediaPlayerDemo";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;

	private SurfaceHolder holder;
	private String path;
	private Bundle extras;
	private static final String MEDIA = "media";
	private static final int LOCAL_AUDIO = 1;
	private static final int STREAM_AUDIO = 2;
	private static final int RESOURCES_AUDIO = 3;
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;
	private WebView webv;
	private  SurfaceView mPreview;
	String cdnl;
	//nodeclient end



	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);



		if (!LibsChecker.checkVitamioLibs(this))
			return;

		// Set by <content src="index.html" /> in config.xml
	  /*  loadUrl(Config.getStartUrl());*/
		setContentView(R.layout.acitity_show);
		//String uid, String username, String nickname, String roomid, String eid
		myApplication= (MyApplication) getApplication();
		/*获取控件*/
		tv1= (TextView) findViewById(R.id.tab1);
		tv2= (TextView) findViewById(R.id.tab2);
		tv3= (TextView) findViewById(R.id.tab3);
		tv5= (TextView) findViewById(R.id.tab5);
		bt4= (Button) findViewById(R.id.tab4);
		mPreview = (SurfaceView) findViewById(R.id.surfaceView);
		showbakc_btn= (ImageButton) findViewById(R.id.showback_btn);



		Bundle bundle=getIntent().getExtras();
		eid = (String) bundle.get("eid");
		roomid= (String) bundle.get("roomid");
		SharedPreferences configfms = getSharedPreferences("configfms", 0);
		cdnl=configfms.getString("cdnl","");
		cdnl=cdnl.trim();
		SharedPreferences settings = getSharedPreferences("login", 0);
		String configuid = settings.getString("uid", "0");
		//获取uid
		if(myApplication.getUserinfo()!=null){
			JSONObject userinfo=myApplication.getUserinfo();
			try {

				user=new User(userinfo.getString("id"),userinfo.getString("username"),userinfo.getString("nickname"),roomid,eid,userinfo.getString("ucuid"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if(configuid!="0"){
			user=new User(configuid,settings.getString("username", ""),settings.getString("nickname",""),roomid,eid,settings.getString("ucuid", "0"));
			//Toast.makeText(this,user.getUid(),Toast.LENGTH_LONG).show();
	    }else {

			user=new User("0",username,nickname,roomid,eid,ucuid);
		}
		myApplication.setUser(user);
		/*---*/
		lt=new Liaotian(user);


		/*注册点击事件*/
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv5.setOnClickListener(this);
		bt4.setOnClickListener(this);
		mPreview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int vb=showbakc_btn.getVisibility();
				if(vb==0){
					showbakc_btn.setVisibility(View.GONE);
				}else{
					showbakc_btn.setVisibility(View.VISIBLE);
				}

			}
		});
		//跳转首页
		showbakc_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(ShowActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		fm=getFragmentManager();
		ft=fm.beginTransaction();
		//Liaotian lt=new Liaotian(user);
		ft.replace(R.id.content, lt);
		ft.commit();

		tv1.setTextColor(Color.rgb(255, 137, 0));
		tv1.setText(Html.fromHtml("<u>公屏</u>"));

		mSocket.connect();
		mSocket.on("cnn", cnn);


		url=myApplication.getUrl()+"/index.php/Api/android_getrankbyshow/eid/"+eid;//获取贡献列表
		this.getinterfacedata();
		//查看是否关注

		new Thread(){
			@Override
			public void run() {
				isatt_emcee=Util.MyHttprequest(myApplication.getUrl() + "/index.php/Appapi/android_Attention/uid/"+user.getUid()+"/eid/"+eid);
				Log.i("isatt_aaa",isatt_emcee);
				if(Integer.parseInt(isatt_emcee.trim())==1){
					Message message=Message.obtain();
					message.what= 1118;
					handler.sendMessage(message);

				}else{
					Message message=Message.obtain();
					message.what= 1119;
					handler.sendMessage(message);
				}
			}
		}.start();
		//保存历史记录
		Util.MysetSharedPreferences(this, "historical", roomid, String.valueOf(System.currentTimeMillis()));

		//nodeclient player

		path="rtmp://"+cdnl+"/5showcam/stream"+roomid+"";

		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setFormat(PixelFormat.RGBA_8888);
		//nodeclient end





	}
	private Emitter.Listener cnn = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					JSONArray userlist = (JSONArray) args[0];
					for (int i = 0; i < userlist.length(); i++) {
						try {
							if (userlist.get(i) != null) {
								userlistinfo = new ArrayList<Userinfo>();
								Userinfo userinfo1 = new Userinfo();
								String userstr = userlist.get(i).toString();
								JSONObject jsonObject = new JSONObject(userstr);

								userinfo1.setUsername(jsonObject.getString("username"));
								userinfo1.setuid(jsonObject.getString("uid"));
								userinfo1.setImgurl(jsonObject.getString("ucuid"));
								userlistinfo.add(userinfo1);
								Log.i("userlist", jsonObject.getString("username"));

							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					// add the message to view
					Log.i("liebiao", args[0].toString());

				}
			});

		}
	};

	public void run(){

		HttpClient httpClient=new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(url);
		String res="";
		try {
			HttpResponse httpResponse=httpClient.execute(httpGet);
			HttpEntity httpEntity=httpResponse.getEntity();
			if(httpEntity!=null){

				res=EntityUtils.toString(httpEntity);
				getjsoninfo=res;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}



	}
	public void getinterfacedata(){

		Thread thread = new Thread(this);
		thread.start();
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onClick(View v) {
		 ft=fm.beginTransaction();
		switch (v.getId()){
			case R.id.tab1:
				tv1.setTextColor(Color.rgb(255,137,0));
				tv2.setTextColor(Color.rgb(0,0,0));
				tv3.setTextColor(Color.rgb(0,0,0));
				tv5.setTextColor(Color.rgb(0,0,0));
				tv1.setText(Html.fromHtml("<u>公屏</u>"));
				tv2.setText("观众");
				tv3.setText("贡献");
				tv5.setText("更多");
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.content,lt);
				break;
			case R.id.tab2:

				tv2.setTextColor(Color.rgb(255,137,0));
				tv1.setTextColor(Color.rgb(0,0,0));
				tv3.setTextColor(Color.rgb(0,0,0));
				tv5.setTextColor(Color.rgb(0,0,0));
				tv2.setText(Html.fromHtml("<u>观众</u>"));
				tv1.setText("公屏");
				tv3.setText("贡献");
				tv5.setText("更多");
				if(userlistinfo==null) return;
				if(gz==null){
					gz=new Guanzhong(userlistinfo);
				}
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.content,gz);
				break;
			case R.id.tab3:
				tv3.setTextColor(Color.rgb(255,137,0));
				tv1.setTextColor(Color.rgb(0,0,0));
				tv2.setTextColor(Color.rgb(0,0,0));
				tv5.setTextColor(Color.rgb(0,0,0));
				tv3.setText(Html.fromHtml("<u>贡献</u>"));
				tv1.setText("公屏");
				tv2.setText("观众");
				tv5.setText("更多");
				if(getjsoninfo==null) return;
				if(gx==null){
					 gx=new Gongxian(getjsoninfo);
				}
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.content, gx);

				break;
			case R.id.tab4:
				new Thread(){
					@Override
					public void run() {
						String isatt= (String) bt4.getText();
						String atturl="";
						if(isatt=="关注"){
							 atturl=myApplication.getUrl()+"/index.php/Appapi/android_interest/uid/"+user.getUid()+"/eid/"+eid;
							 Message message=Message.obtain();
							 message.what= 1118;
							 handler.sendMessage(message);

						}else{
							 atturl=myApplication.getUrl()+"/index.php/Appapi/android_cancelInterest/uid/"+user.getUid()+"/eid/"+eid;
							Message message=Message.obtain();
							message.what= 1119;
							handler.sendMessage(message);

						}
						HttpClient httpClient= new DefaultHttpClient();
						HttpGet httpGet=new HttpGet(atturl);
						try {
							HttpResponse httpResponse=httpClient.execute(httpGet);
							HttpEntity httpEntity=httpResponse.getEntity();
							String res=EntityUtils.toString(httpEntity);
							Log.i("关注",res);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}.start();

				//Toast.makeText(this,"正在开发.....",Toast.LENGTH_LONG).show();
				break;
			case R.id.tab5:
				tv5.setTextColor(Color.rgb(255,137,0));
				tv1.setTextColor(Color.rgb(0,0,0));
				tv2.setTextColor(Color.rgb(0,0,0));
				tv3.setTextColor(Color.rgb(0,0,0));
				tv5.setText(Html.fromHtml("<u>更多</u>"));
				tv1.setText("公屏");
				tv2.setText("观众");
				tv3.setText("贡献");
				if(more==null){
					more=new More(roomid);
				}
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.content,more);

				break;
		}
		ft.commit();

	}



	//nodeclient

	private void playVideo(Integer Media) {
		doCleanUp();
		try {

			switch (Media) {
				case LOCAL_VIDEO:
				/*
				 * TODO: Set the path variable to a local media file path.
				 */
					path = "";
					if (path == "") {
						// Tell the user to provide a media file URL.
						Toast.makeText(ShowActivity.this, "Please edit MediaPlayerDemo_Video Activity, " + "and set the path variable to your media file path." + " Your media file must be stored on sdcard.", Toast.LENGTH_LONG).show();
						return;
					}
					break;
				case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or
				 * 3gpp format URL. Http protocol should be used.
				 * Mediaplayer can only play "progressive streamable
				 * contents" which basically means: 1. the movie atom has to
				 * precede all the media data atoms. 2. The clip has to be
				 * reasonably interleaved.
				 *
				 */

					if (path == "") {
						// Tell the user to provide a media file URL.
						Toast.makeText(ShowActivity.this, "Please edit MediaPlayerDemo_Video Activity," + " and set the path variable to your media file URL.", Toast.LENGTH_LONG).show();
						return;
					}

					break;

			}

			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer(this);
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			setVolumeControlStream(AudioManager.STREAM_MUSIC);

		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		// Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
		playVideo(5);

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	//nodeclient end



	private Handler handler = new Handler() {
		// 回调处理
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			StringBuffer sb = new StringBuffer();
			SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
			String sRecTime = sDateFormat.format(new java.util.Date());
			sb.append(sRecTime);
			sb.append(" - ");
			sb.append(msg.getData().getString("msg"));
			sb.append("\r\n");


			switch (msg.what) {
				case 1000:
					// "正在连接视频",

					break;
				case 1001:
					//"视频连接成功",

					break;
				case 1002:
					// "视频连接失败",
					//
					break;
				case 1004:
					//"视频播放结束",
					break;
				case 1005:
					// "网络异常,播放中断",
					//
					break;
				case 1006:
					//"视频数据未找到",
					//
					break;
				case 1007:
					// "音频数据未找到",
					//
					break;
				case 1008:
					// "无法打开视频解码器",
					//
					break;
				case 1009:
					//  "无法打开音频解码器",
					//
					break;
				case 1100:
					System.out.println("NetStream.Buffer.Empty");
					break;
				case 1102:
					System.out.println("NetStream.Buffer.Full");
					break;
				case 1103:
					System.out.println("Stream EOF");
					break;
				case 1104:

					break;
				case 1118:
					bt4.setText("取消关注");
					//Toast.makeText(ShowActivity.this,"已关注",Toast.LENGTH_LONG).show();
					break;
				case 1119:
					bt4.setText("关注");

					//Toast.makeText(ShowActivity.this,"未关注",Toast.LENGTH_LONG).show();
					break;
				case 1117:

				default:
					break;
			}
		}
	};
}
