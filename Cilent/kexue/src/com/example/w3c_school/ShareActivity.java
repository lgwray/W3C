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
		

		// ���÷��������
		setShareContent();
	}

	/**
	 * ���qqZoneƽ̨
	 */
	private void addQZonePlatform() {
		String appId = "1104919418";
		String appKey = "HBkbwvXRyMBROtEe";

		// ���QZoneƽ̨
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId,
				appKey);
		qZoneSsoHandler.addToSocialSDK();
	}

	/**
	 * ���qqƽ̨
	 */
	private void addQQPlatform() {
		String appId = "1104919418";
		String appKey = "HBkbwvXRyMBROtEe";
		// ���QQ֧��, ��������QQ�������ݵ�target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, appId, appKey);
		qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
		qqSsoHandler.addToSocialSDK();

	}

	/**
	 * @�������� : ���΢��ƽ̨����
	 * @return
	 */
	private void addWXPlatform() {
		// ע�⣺��΢����Ȩ��ʱ�򣬱��봫��appSecret
		// wx967daebe835fbeac������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
		String appId = "wx00943f674620552f";
		String appSecret = "33e6dd8d6aef26ba1bd632746cbc5be1";
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
		wxHandler.addToSocialSDK();

	}

	/**
	 * @�������� : ���΢������Ȧ����
	 * @return
	 */
	private void addWXFriendPlatform() {
		// ע�⣺��΢����Ȩ��ʱ�򣬱��봫��appSecret
		// wx967daebe835fbeac������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
		String appId = "wx00943f674620552f";
		String appSecret = "33e6dd8d6aef26ba1bd632746cbc5be1";

		// ֧��΢������Ȧ
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}


	private void setShareContent() {

		// ����SSO
		 mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		SinaSsoHandler sinaHandler = new SinaSsoHandler();
		sinaHandler.addToSocialSDK();
		mController.setShareContent("��ѧ���֣�֪ʶ���У������ḻѧʶ��~");

		UMQQSsoHandler qq = new UMQQSsoHandler(ShareActivity.this,
				"1104840419", "qx6O2OY6wS211lhm");
		qq.addToSocialSDK();
		mController.setShareContent("��ѧ���֣�֪ʶ���У������ḻѧʶ��~");

		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"1104840419", "qx6O2OY6wS211lhm");
		qZoneSsoHandler.addToSocialSDK();
		mController.setShareContent("��ѧ���֣�֪ʶ���У������ḻѧʶ��~");

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent("��ѧ���֣�֪ʶ���У������ḻѧʶ��~");
		weixinContent.setTitle("��ѧ-΢��");
		weixinContent.setTargetUrl("http://www.umeng.com/social");
		// weixinContent.setShareMedia(urlImage);
		mController.setShareMedia(weixinContent);

		// ��������Ȧ���������
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent("��ѧ���֣�֪ʶ���У������ḻѧʶ��~");
		circleMedia.setTitle("������ữ�������-����Ȧ");
		// circleMedia.setShareMedia(urlImage);
		// circleMedia.setShareMedia(uMusic);
		// circleMedia.setShareMedia(video);
		circleMedia.setTargetUrl("http://www.umeng.com/social");
		mController.setShareMedia(circleMedia);
		//
		// QQShareContent qq = new QQShareContent();
		// qq.setShareContent("��ѧ���֣�֪ʶ���У������ḻѧʶ��~");
		// // ����tencent��������s
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
						String showText = "����ɹ�";
						if (eCode != StatusCode.ST_CODE_SUCCESSED) {
							//showText = "����ʧ�� [" + eCode + "]";
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
