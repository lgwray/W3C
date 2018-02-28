package com.example.w3c_school;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegularActivity extends BaseActivity{

	private WebView regular;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regular);
		regular = (WebView) findViewById(R.id.regular);
		regular.loadUrl("file:///android_asset/html/regular.html");
		progressDialog = new ProgressDialog(RegularActivity.this);//��ʾ��Ϊ��
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("���ڼ���...");
		progressDialog.show();
		
		regular.setWebViewClient(new WebViewClient(){



			@Override
			public void onPageFinished(WebView view, String url) {
				if(progressDialog!=null&&progressDialog.isShowing()){//���ݼ������ʱ
				      progressDialog.dismiss();
				 }
			}
			
		});
	}
	public void back(View view) {
		finish();
	}

}
