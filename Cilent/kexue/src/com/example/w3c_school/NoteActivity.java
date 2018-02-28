package com.example.w3c_school;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.dao.NoteDao;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.MethodUtils;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.j256.ormlite.dao.Dao;

public class NoteActivity extends BaseActivity {

	private Button add;
	private EditText tag;
	private EditText tagContent;

	private User user;
	private Note noteIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		add = (Button) findViewById(R.id.submit);
		tag = (EditText) findViewById(R.id.tag);
		tagContent = (EditText) findViewById(R.id.tagContent);
		user = ApplicationDIV.getInstance().getUser();
		noteIntent = (Note) getIntent().getSerializableExtra("note");
		setResult(RESULT_OK);

		iniView();
		doIt();
	}

	private void iniView() {
		if (null != noteIntent) {
			tag.setText(noteIntent.getName());
			tagContent.setText(noteIntent.getContent());
		} else {
			tag.setText("");
			tagContent.setText("");
		}
	}

	private void doIt() {
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != user) {
					insertData();
				} else {
					Toast.makeText(NoteActivity.this, "���¼",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * ���뱾�����ݿ�
	 */
	@SuppressLint("SimpleDateFormat")
	private void insertData() {
		String tagText = tag.getText().toString();
		String tagContentTtext = tagContent.getText().toString();
		if (tagText == null && tagContentTtext == null) {
			return;
		}

		Note n = new Note();
		if (null != tagText) {
			n.setName(tagText);
		}
		if (null != tagContentTtext) {
			n.setContent(tagContentTtext);
		}
		n.setDate(MethodUtils.getCurDate());
		n.setUserNo(user.getUserNo());

		NoteDao noteDao = new NoteDao(getApplicationContext());
		List<Note> isHave = noteDao.queryNotesByName(n, user);
		if (isHave.size() > 0 && null != isHave) {
			if (noteIntent==null) {
				Toast.makeText(getApplicationContext(), "�ʼ����ظ������޸�",
						Toast.LENGTH_SHORT).show();
			}else {//�޸ıʼ�
				noteDao.updateNote(n, user);
			}
			
		}
	}

	/**
	 * @param note
	 *            �����ݲ��뵽������
	 */
	private void setConData(Note note) {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Toast.makeText(NoteActivity.this, "�ɹ����µ����ݿ�",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(NoteActivity.this, "����������ʧ�ܣ�������",
								Toast.LENGTH_SHORT).show();
					}
				});

		request.putParams("name", note.getName());
		request.putParams("date", note.getDate());
		request.putParams("content", note.getContent());
		request.putParams("userNo", note.getUserNo());
		request.putParams("find", "notesInsert");// ��������
		ApplicationDIV.getInstance().getRequestQueue().add(request);

	}

	/**
	 * @param note
	 *            �ӷ�����ɾ������note
	 */
	private void deleteConData(Note note) {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Log.i("delete", "delete");
						// Toast.makeText(NoteActivity.this, "ɾ���ɹ�",
						// Toast.LENGTH_SHORT).show();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(NoteActivity.this, "����������ʧ�ܣ�������",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "notesDelete");
		request.putParams("id", note.getId() + "");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	public void back(View v) {
		finish();
	}

}
