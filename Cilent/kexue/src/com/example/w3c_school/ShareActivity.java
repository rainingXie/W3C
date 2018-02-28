package com.example.w3c_school;

import com.example.w3c_school.utils.Constant;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ShareActivity extends BaseActivity implements OnClickListener {

	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constant.DESCRIPTOR);

	private SHARE_MEDIA mPlatform = SHARE_MEDIA.TENCENT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.qZone).setOnClickListener(this);
		findViewById(R.id.wechat).setOnClickListener(this);
		findViewById(R.id.friend).setOnClickListener(this);
		

		// 设置分享的内容
		setShareContent();
	}

	/**
	 * 添加qqZone平台
	 */
	private void addQZonePlatform() {
		String appId = "1104919418";
		String appKey = "HBkbwvXRyMBROtEe";

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId,
				appKey);
		qZoneSsoHandler.addToSocialSDK();
	}

	/**
	 * 添加qq平台
	 */
	private void addQQPlatform() {
		String appId = "1104919418";
		String appKey = "HBkbwvXRyMBROtEe";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appKey);
		qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
		qqSsoHandler.addToSocialSDK();

	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = "wx00943f674620552f";
		String appSecret = "33e6dd8d6aef26ba1bd632746cbc5be1";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
		wxHandler.addToSocialSDK();

	}

	/**
	 * @功能描述 : 添加微信朋友圈分享
	 * @return
	 */
	private void addWXFriendPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = "wx00943f674620552f";
		String appSecret = "33e6dd8d6aef26ba1bd632746cbc5be1";

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}


	private void setShareContent() {

		// 配置SSO
		 mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		SinaSsoHandler sinaHandler = new SinaSsoHandler();
		sinaHandler.addToSocialSDK();
		mController.setShareContent("渴学在手，知识我有，快来丰富学识吧~");

		UMQQSsoHandler qq = new UMQQSsoHandler(ShareActivity.this,
				"1104840419", "qx6O2OY6wS211lhm");
		qq.addToSocialSDK();
		mController.setShareContent("渴学在手，知识我有，快来丰富学识吧~");

		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"1104840419", "qx6O2OY6wS211lhm");
		qZoneSsoHandler.addToSocialSDK();
		mController.setShareContent("渴学在手，知识我有，快来丰富学识吧~");

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent("渴学在手，知识我有，快来丰富学识吧~");
		weixinContent.setTitle("渴学-微信");
		weixinContent.setTargetUrl("http://www.umeng.com/social");
		// weixinContent.setShareMedia(urlImage);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent("渴学在手，知识我有，快来丰富学识吧~");
		circleMedia.setTitle("友盟社会化分享组件-朋友圈");
		// circleMedia.setShareMedia(urlImage);
		// circleMedia.setShareMedia(uMusic);
		// circleMedia.setShareMedia(video);
		circleMedia.setTargetUrl("http://www.umeng.com/social");
		mController.setShareMedia(circleMedia);
		//
		// QQShareContent qq = new QQShareContent();
		// qq.setShareContent("渴学在手，知识我有，快来丰富学识吧~");
		// // 设置tencent分享内容s
		// mController.setShareMedia(qq);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qq:
			mPlatform = SHARE_MEDIA.QQ;
			addQQPlatform();
			directShare();
			break;
		case R.id.qZone:
			mPlatform = SHARE_MEDIA.QZONE;
			addQZonePlatform();
			directShare();
			break;
		case R.id.wechat:
			mPlatform = SHARE_MEDIA.WEIXIN;
			addWXPlatform();
			directShare();
			break;
		case R.id.friend:
			mPlatform = SHARE_MEDIA.WEIXIN_CIRCLE;
			addWXFriendPlatform();
			directShare();

			break;
		

		default:
			break;
		}

	}

	private void directShare() {
		mController.directShare(ShareActivity.this, mPlatform,
				new SnsPostListener() {

					@Override
					public void onStart() {
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode,
							SocializeEntity entity) {
						String showText = "分享成功";
						if (eCode != StatusCode.ST_CODE_SUCCESSED) {
							//showText = "分享失败 [" + eCode + "]";
						}
						Toast.makeText(ShareActivity.this, showText,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig()
				.getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public void back(View v) {
		finish();
	}
}
