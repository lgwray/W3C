package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.example.w3c_school.adapter.AdPagerAdapter;
import com.example.w3c_school.adapter.FragementAdapter;
import com.example.w3c_school.entity.ApkInfo;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.fragment.BookFragment;
import com.example.w3c_school.fragment.TestFragment;
import com.example.w3c_school.fragment.VideoFragment;
import com.example.w3c_school.service.VersionUpdateService;
import com.example.w3c_school.utils.AppManager;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.DataManager;
import com.example.w3c_school.utils.FaceConversionUtil;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.MethodUtils;
import com.example.w3c_school.utils.UtilsURL;
import com.example.w3c_school.view.FloatView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private static final int REQ_CODE = 1;
	private ViewPager adViewPager;
	private PagerSlidingTabStrip tabHost;
	private AdPagerAdapter viewPagerAdapter;
	private RelativeLayout myInfo;

	private ImageView photo;
	private RelativeLayout save;

	private RelativeLayout updatePwd;

	private ViewPager mainViewPager;
	private List<ImageView> imageData;
	private LinearLayout indicatorContainer;
	private ActionBar actBar;
	private DrawerLayout myDrawerLayout;
	private ActionBarDrawerToggle myActionBarDrawerToggle;

	private CheckBox selfLogin;

	private User user;

	private TextView name;

	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowManagerParams = null;
	private FloatView floatView = null;
	private DisplayMetrics dm;
	private int sWidth;
	private int sHeight;
	private ApkInfo apkInfo;
	private TextView versionName;
	private long firstTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		apkInfo = ApplicationDIV.getInstance().getApkInfo();
		AppManager.getAppManager().addActivity(this);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		user = ApplicationDIV.getInstance().getUser();
		adViewPager = (ViewPager) findViewById(R.id.adViewPager);
		tabHost = (PagerSlidingTabStrip) findViewById(R.id.tabHost);
		mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
		photo = (ImageView) findViewById(R.id.photo);
		save = (RelativeLayout) findViewById(R.id.save);
		name = (TextView) findViewById(R.id.name);
		myInfo = (RelativeLayout) findViewById(R.id.myInfo);
		updatePwd = (RelativeLayout) findViewById(R.id.updatePwd);
		selfLogin = (CheckBox) findViewById(R.id.auto_login);
		versionName = (TextView) findViewById(R.id.versionName);
		findViewById(R.id.note).setOnClickListener(this);
		findViewById(R.id.version).setOnClickListener(this);

		save.setOnClickListener(this);
		myInfo.setOnClickListener(this);
		updatePwd.setOnClickListener(this);
		imageData = new ArrayList<ImageView>();
		initViewPagerData();
		viewPagerAdapter = new AdPagerAdapter(imageData);
		indicatorContainer = (LinearLayout) findViewById(R.id.indicatorContainer);
		adViewPager.setAdapter(viewPagerAdapter);
		initAdView();
		initTabView();
		initActionBar();
		if (null != user) {
			initLeftView();
		}
		setVersionName();
		createFloatView();
		// ���ر���
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(ApplicationDIV.getInstance());
			}
		}).start();

	}

	/**
	 * �༭actionBar
	 */
	@SuppressLint("NewApi")
	private void initActionBar() {
		// ��ȡactionBar
		actBar = getActionBar();
		actBar.show();// ��ʾ��ʽ

		actBar.setDisplayShowHomeEnabled(false);
//
//		actBar.setDisplayUseLogoEnabled(true);// ����logo��ʾ
//	    actBar.setLogo(R.drawable.logo);

		actBar.setDisplayShowTitleEnabled(true);// ���⿪��
		actBar.setTitle("��  ѧ");

		actBar.setDisplayHomeAsUpEnabled(true);// ���ذ�ť
		actBar.setDisplayShowCustomEnabled(true);// �Զ��忪�ز���
		myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		myActionBarDrawerToggle = new ActionBarDrawerToggle(this,
				myDrawerLayout, R.string.app_name, R.string.hello_world);
		myDrawerLayout.setDrawerListener(myActionBarDrawerToggle);
	}

	/**
	 * ���������������
	 */
	private void initTabView() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		List<String> categories = DataManager.getTotalCategory();
		fragments.add(new BookFragment());
		fragments.add(new TestFragment());
		fragments.add(new VideoFragment());

		FragementAdapter adapter = new FragementAdapter(
				getSupportFragmentManager(), fragments, categories);
		this.mainViewPager.setAdapter(adapter);

		tabHost.setTextSize(40);
		tabHost.setTextColor(Color.BLACK);
		tabHost.setViewPager(mainViewPager);
	}

	/**
	 * ���ø�����������
	 */
	private void initLeftView() {
		SharedPreferences sp = getSharedPreferences(Constant.SP_FILE_NAME,
				MODE_PRIVATE);

	//	String nameStr = sp.getString("userName", null);
		name.setText(user.getUserName());

		ImageLoaderUitil.display(user.getUserImg(), photo);

		boolean self_login_flag = sp.getBoolean("self_login", true);
		selfLogin.setChecked(self_login_flag);
		selfLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("self_login", isChecked);
				editor.commit();

			}
		});
	}

	/**
	 * ��̬���������
	 */
	private void initAdView() {
		for (int i = 0; i < imageData.size(); i++) {
			ImageView iView = new ImageView(MainActivity.this);
			if (i == 0) {
				iView.setImageResource(R.drawable.image_indicator_focus);
			} else {
				iView.setImageResource(R.drawable.image_indicator);
			}

			indicatorContainer.addView(iView);
		}

		adViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(final int arg0) {

				for (int i = 0; i < imageData.size(); i++) {
					ImageView indicator = (ImageView) indicatorContainer
							.getChildAt(i);
					if (i == arg0) {
						indicator
								.setImageResource(R.drawable.image_indicator_focus);
					} else {
						indicator.setImageResource(R.drawable.image_indicator);
					}
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		default:
			break;
		}
		return super.onOptionsItemSelected(item)
				|| myActionBarDrawerToggle.onOptionsItemSelected(item);

	}

	// ���÷����仯ʱ
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		myActionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * ���ͼƬ
	 */
	private void initViewPagerData() {
		
		
		ImageView view = new ImageView(this);
		view.setImageResource(R.drawable.ad1);
		view.setScaleType(ScaleType.FIT_XY);
		imageData.add(view);

		view = new ImageView(this);
		view.setImageResource(R.drawable.w3welcome);
		view.setScaleType(ScaleType.FIT_XY);
		imageData.add(view);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myInfo:
			user = ApplicationDIV.getInstance().getUser();
			if (user == null) {
				startActivityForResult(new Intent(MainActivity.this,
						LoginActivity.class), REQ_CODE);

			} else {
				startActivity(new Intent(MainActivity.this,
						SelfInfoActivity.class));
			}
			break;
		case R.id.updatePwd:
			if (user == null) {
				Toast.makeText(getApplicationContext(), "���ȵ�¼��", Toast.LENGTH_SHORT).show();

			} else {
				startActivity(new Intent(MainActivity.this, UpdataPwdActivity.class));
			}
			

			break;
		case R.id.save:
			if (user == null) {
				Toast.makeText(getApplicationContext(), "���ȵ�¼��", Toast.LENGTH_SHORT).show();

			} else {
				startActivity(new Intent(MainActivity.this, SaveActivity.class));
			}

			

			break;
		case R.id.note:
			if (user == null) {
				Toast.makeText(getApplicationContext(), "���ȵ�¼��", Toast.LENGTH_SHORT).show();

			} else {
				startActivity(new Intent(MainActivity.this, NoteListActivity.class));
			}

			

			break;
		case R.id.version:
			updateVersion();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
			user = ApplicationDIV.getInstance().getUser();
			initLeftView();
		}
		if (requestCode == REQ_CODE && resultCode == RESULT_FIRST_USER) {
			user = ApplicationDIV.getInstance().getUser();
			initLeftView();
		}

	}

	/**
	 * �汾��⼰����
	 */
	public void updateVersion() {
		int vcode;
		// ���汾�ź͵�ǰ��װ�汾�ı�Ž��жԱ�
		PackageManager pm = getPackageManager();
		PackageInfo pki = null;
		try {
			pki = pm.getPackageInfo("com.example.w3c_school", 0);
			int curCode = pki.versionCode;// ��ǰ�汾���
			if (null != apkInfo) {
				vcode = apkInfo.getVid();
			} else {
				vcode = curCode;
			}
			if (vcode > curCode) {
				// �汾�и���
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("�汾����").setMessage("��⵽�°汾���Ƿ���������");
				builder.setPositiveButton("��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {// which�ǰ�ťid

								// ׼������
								Intent intent = new Intent(
										getApplicationContext(),
										VersionUpdateService.class);
								String url = UtilsURL.ROOT_URL
										+ apkInfo.getUrl();
								intent.putExtra("url", url);
								startService(intent);
								dialog.dismiss();// ��ʧ
							}
						});
				builder.setNegativeButton("��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();// ��ʧ
							}
						});
				AlertDialog alertDialog = builder.create();
				alertDialog.setCancelable(false);
				alertDialog.show();

			} else {
				Toast.makeText(getApplicationContext(), "��ǰΪ���°汾",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ð汾
	 */
	public void setVersionName() {
		PackageManager pm = getPackageManager();
		PackageInfo pki = null;
		try {
			pki = pm.getPackageInfo("com.example.w3c_school", 0);
			String curName = pki.versionName;// ��ǰ�汾����
			versionName.setText(curName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ȫ�ָ�����ť
	 */
	private void createFloatView() {

		sWidth = dm.widthPixels;
		sHeight = dm.heightPixels;
		floatView = new FloatView(getApplicationContext());
		floatView.setOnClickListener(new OnClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				View contextView = LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.pop_float, null);
				final PopupWindow pop = new PopupWindow(contextView,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				LinearLayout share = (LinearLayout) contextView
						.findViewById(R.id.share);
				share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(MainActivity.this,
								ShareActivity.class));
						pop.dismiss();
					}
				});

				LinearLayout note = (LinearLayout) contextView
						.findViewById(R.id.note);
				note.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(MainActivity.this,
								NoteActivity.class));
						pop.dismiss();
					}
				});
				pop.setFocusable(true);
				pop.setOutsideTouchable(true);
				pop.setBackgroundDrawable(new ColorDrawable());
				pop.showAtLocation(v, Gravity.RIGHT, 120, 0);
			}
		});
		floatView.setImageResource(R.drawable.morefloat); // ����򵥵����Դ���icon������ʾ
		// ��ȡWindowManager
		windowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		// ����LayoutParams(ȫ�ֱ�������ز���
		windowManagerParams = ApplicationDIV.getInstance().getWindowParams();

		windowManagerParams.type = LayoutParams.TYPE_PHONE; // ����window type
		windowManagerParams.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��
		// ����Window flag
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * ע�⣬flag��ֵ����Ϊ�� �����flags���Ե�Ч����ͬ���������� ���������ɴ������������κ��¼�,ͬʱ��Ӱ�������¼���Ӧ��
		 * LayoutParams.FLAG_NOT_TOUCH_MODAL ��Ӱ�������¼�
		 * LayoutParams.FLAG_NOT_FOCUSABLE ���ɾ۽� LayoutParams.FLAG_NOT_TOUCHABLE
		 * ���ɴ���
		 */
		// �����������������Ͻǣ����ڵ�������
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
		windowManagerParams.x = sWidth - 50;
		windowManagerParams.y = sHeight - 50;
		// �����������ڳ�������
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
		// ��ʾmyFloatViewͼ��
		windowManager.addView(floatView, windowManagerParams);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// �ڳ����˳�(Activity���٣�ʱ������������
		windowManager.removeView(floatView);
		AppManager.getAppManager().finishActivity(MainActivity.class);
	}

	@Override
	protected void onStart() {
		super.onStart();
		user = ApplicationDIV.getInstance().getUser();
		if (null != user) {
			initLeftView();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if ((secondTime - firstTime) > 1000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
						Toast.LENGTH_SHORT).show();
				firstTime = System.currentTimeMillis();
			} else {
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				long endApp = System.currentTimeMillis();
				long startApp = sp.getLong("startAppTime", 0);
				long runTime = endApp-startApp;
				Toast.makeText(getApplicationContext(),"��һ��ѧϰ��\n"+ MethodUtils.getTimeFormat(runTime)+"\n����Ŭ������", Toast.LENGTH_LONG).show();
				AppManager.getAppManager().finishAllActivity();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
