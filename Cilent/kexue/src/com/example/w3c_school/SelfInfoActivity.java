package com.example.w3c_school;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.AppManager;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.FileUitlity;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.j256.ormlite.dao.Dao;

public class SelfInfoActivity extends BaseActivity implements OnClickListener {

	private ImageView photo;
	private TextView name;
	private TextView account;
	private TextView province;
	private TextView city;
	private User user;
	private String photoPath;
	private Bitmap bitmap;
	// ���
	private static final int TAKE_PHOTO_CAPTURE = 1;
	// ���
	private static final int TAKE_FROM_ALBUM = 2;
	// ���ͼ��
	private static final int RESULT_PHOTO = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		photo = (ImageView) findViewById(R.id.photo);
		photo.setOnClickListener(this);
		user = ApplicationDIV.getInstance().getUser();
		findViewById(R.id.exit).setOnClickListener(this);
		if (null != user) {
			initView();
		}

	}

	private void initView() {
		name = (TextView) findViewById(R.id.name);
		account = (TextView) findViewById(R.id.account);
		province = (TextView) findViewById(R.id.province);
		city = (TextView) findViewById(R.id.city);
		name.setText(user.getUserName());
		account.setText(user.getUserNo());
		province.setText(user.getProvince());
		city.setText(user.getCity());
		ImageLoaderUitil.display(user.getUserImg(), photo,this);

	}

	@SuppressLint("InflateParams")
	@Override
	public void onClick(View v) {
	//	if (null != user) {
			switch (v.getId()) {
			case R.id.photo:
				View contextView = LayoutInflater.from(this).inflate(
						R.layout.pop_window, null);
				final PopupWindow pop = new PopupWindow(contextView,
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				pop.setAnimationStyle(R.style.pop_bottom_style);

				contextView.findViewById(R.id.camera).setOnClickListener(
						new OnClickListener() {

							/*
							 * (non-Javadoc) ������
							 */
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								// ��Դ��ַ
								File parent = FileUitlity.getInstance(
										getApplicationContext()).makeDir(
										"school_photo");
								photoPath = parent.getPath() + File.separator
										+ System.currentTimeMillis() + ".png";
								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(photoPath)));
								startActivityForResult(intent,
										TAKE_PHOTO_CAPTURE);
								pop.dismiss();

							}
						});
				contextView.findViewById(R.id.gallery).setOnClickListener(
						new OnClickListener() {

							/*
							 * (non-Javadoc)��ñ������
							 */
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(Intent.ACTION_PICK);
								intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
								startActivityForResult(intent, TAKE_FROM_ALBUM);
								pop.dismiss();

							}
						});
				contextView.findViewById(R.id.cancel).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								pop.dismiss();

							}
						});
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.5f;
				getWindow().setAttributes(lp);
				pop.setFocusable(true);
				pop.setOutsideTouchable(true);
				pop.setBackgroundDrawable(new ColorDrawable());
				pop.showAtLocation(v, Gravity.BOTTOM, 0, 0);
				pop.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.alpha = 1.0f;
						getWindow().setAttributes(lp);

					}
				});
				break;
				
			case R.id.exit:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("�˳���¼").setMessage("ȷ���˳���ǰ�û���");
				builder.setPositiveButton("ȷ��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {// which�ǰ�ťid

								SharedPreferences sp = getSharedPreferences(
										Constant.SP_FILE_NAME, MODE_PRIVATE);
								SharedPreferences.Editor editor = sp.edit();
								editor.putBoolean("self_login", false);
								editor.commit();
								ApplicationDIV.getInstance().setUser(null);
								AppManager.getAppManager().AppExit(
										getApplicationContext());
								Intent intent = new Intent();
								intent.setClass(SelfInfoActivity.this,
										SplashActivity.class);
								startActivity(intent);

							}
						});
				builder.setNegativeButton("ȡ��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();// ��ʧ
							}
						});
				AlertDialog alertDialog = builder.create();
				alertDialog.setCancelable(false);
				alertDialog.show();
				break;
			default:
				break;
			}
	//	} else {
		//	Toast.makeText(getApplicationContext(), "���ȵ�¼", Toast.LENGTH_SHORT)
		//			.show();
	//	}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// �������
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case TAKE_PHOTO_CAPTURE:
			if (null != photoPath) {
				startPhotoZoom(Uri.fromFile(new File(photoPath)));
				photo.setImageURI(Uri.fromFile(new File(photoPath)));

			}
			break;

		case TAKE_FROM_ALBUM:
			Cursor c = this.getContentResolver().query(data.getData(),
					new String[] { MediaStore.Images.Media.DATA }, null, null,
					null);
			c.moveToFirst();
			photoPath = c.getString(c
					.getColumnIndex(MediaStore.Images.Media.DATA));
			c.close();
			startPhotoZoom(Uri.fromFile(new File(photoPath)));
			break;
		case RESULT_PHOTO:
			Bundle bundle = data.getExtras();
			if (null != bundle) {
				bitmap = (Bitmap) bundle.getParcelable("data");

				// ѯ���Ƿ��ϴ�ͼƬ
				// ����ǿ�ʼ�ϴ�����ͼƬת����Base64������ַ���
				alertUpdatePhoto(bitmap);
			}
			break;
		default:
			break;
		}
	}

	public void alertUpdatePhoto(final Bitmap b) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ʾ").setMessage("�Ƿ��ϴ�ͼƬ��");
		builder.setPositiveButton("��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {// which�ǰ�ťid
						updatePhoto(b);
						photo.setImageBitmap(bitmap);

					}
				});
		builder.setNegativeButton("��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();// ��ʧ
						return;

					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.setCancelable(false);
		alertDialog.show();

	}

	/**
	 * @param bitmap
	 * @return ��ͼƬת����Base64������ַ���
	 * 
	 */
	public String convertBitmap2String(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// ѹ��ͼƬ
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);// 100%�ı���
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] buffer = out.toByteArray();
		byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
		return new String(encode);

	}

	public void updateUser(User user) {
		Dao<User, String> userDao = DBHelper.getInstance(SelfInfoActivity.this)
				.getUserDao();

		try {
			User u = userDao.queryForId(user.getUserNo());
			if (null != user) {
				userDao.delete(u);

			}
			userDao.create(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param b
	 *            ����ͼƬ
	 */
	public void updatePhoto(Bitmap b) {
		StringPostRequest request = new StringPostRequest(UtilsURL.LOGIN_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if (null != arg0) {
							if (null != user) {
								user.setUserImg(arg0);
								ApplicationDIV.getInstance().setUser(user);
								updateUser(user);
							}

						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "����������ʧ�ܣ�������",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("userNo", user.getUserNo());
		request.putParams("userPwd", user.getUserPwd());
		request.putParams("headImage", "1");
		request.putParams("uhead", convertBitmap2String(b));// ���ͼƬ
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	/**
	 * �ü�ͼƬ
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_PHOTO);
	}

	/**
	 * �˳��ý���
	 */
	public void back(View v) {
		finish();
	}

}
