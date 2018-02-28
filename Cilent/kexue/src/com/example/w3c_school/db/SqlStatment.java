package com.example.w3c_school.db;


import com.example.w3c_school.db.DBConstant.ContentItemTable;
import com.example.w3c_school.db.DBConstant.CourseTable;
import com.example.w3c_school.db.DBConstant.NoteTable;
import com.example.w3c_school.db.DBConstant.SaveTable;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.User;

/**
 * @author xieyu
 *
 */
public class SqlStatment {

	/**
	 * 
	 * ����saves��items���ѯuser�ղ����ݵ�sql���
	 * 
	 * @param user
	 * @return
	 */
	public static String querySavesSql(User user) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select a.* from ");
		sqlBuilder.append(DBConstant.ContentItemTable.TABLE_NAME + " a,");
		sqlBuilder.append(DBConstant.SaveTable.TABLE_NAME + " b where ");
		sqlBuilder.append("a." + ContentItemTable.COL_ID + "= b."
				+ SaveTable.COL_CONTENT_ID);
		sqlBuilder.append(" and b." + SaveTable.COL_USER + "="
				+ user.getUserNo());

		return sqlBuilder.toString();

	}

	/**
	 * 
	 * ����saves��items���ѯuser�ղ����ݵ�sql���
	 * 
	 * @param user
	 * @return
	 */
	public static String querySavesSql(ContentItem item, User user) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(DBConstant.SaveTable.TABLE_NAME + " where ");
		sqlBuilder.append(SaveTable.COL_CONTENT_ID + "=" + item.getId());
		sqlBuilder
				.append(" and " + SaveTable.COL_USER + "=" + user.getUserNo());

		return sqlBuilder.toString();

	}

	

	/**
	 * ȡ�����û����ղصĸ������ݵ�sql���
	 * @param item
	 * @param user
	 * @return
	 */
	public static String deleteSaveSql(ContentItem item, User user) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("delete from " + SaveTable.TABLE_NAME);
		sqlBuilder.append(" where " + SaveTable.COL_CONTENT_ID + "="
				+ item.getId());
		sqlBuilder
				.append(" and " + SaveTable.COL_USER + "=" + user.getUserNo());
		return sqlBuilder.toString();

	}
	
	/**
	 * ����notes��items���ѯuser�ʼ����ݵ�sql���
	 * 
	 * @param user
	 * @return
	 */
//	public static String queryNotesSql(User user) {
//		StringBuilder sqlBuilder = new StringBuilder();
//		sqlBuilder.append("select a.* from ");
//		sqlBuilder.append(DBConstant.ContentItemTable.TABLE_NAME + " a,");
//		sqlBuilder.append(DBConstant.SaveTable.TABLE_NAME + " b where");
//		sqlBuilder.append("a." + ContentItemTable.COL_ID + "=b."
//				+ NoteTable.COL_CONTENT_ID);
//		sqlBuilder.append(" and b." + NoteTable.COL_USER + "="
//				+ user.getUserNo());
//
//		return sqlBuilder.toString();
//
//	}
	
	/**
	 * ��ѯ��ǰitem��user�ʼ����ݵ�sql���
	 * 
	 * @param user
	 * @return
	 */
	public static String queryNotesSql(ContentItem item,User user) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(DBConstant.NoteTable.TABLE_NAME + " where ");
		sqlBuilder.append(NoteTable.COL_CONTENT_ID + "=" + item.getId());
		sqlBuilder
				.append(" and " + NoteTable.COL_USER + "=" + user.getUserNo());

		return sqlBuilder.toString();

	}
	
	/**
	 * ��ѯ��ǰuser�ʼ����ݵ�sql���
	 * 
	 * @param user
	 * @return
	 */
	public static String queryNotesSql(User user) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(DBConstant.NoteTable.TABLE_NAME + " where ");
		sqlBuilder.append(NoteTable.COL_USER + "=" + user.getUserNo());

		return sqlBuilder.toString();

	}
	
	
	/**
	 * ��ѯ��ǰitem��ĳ���ʼ��Ƿ���ڵ�sql���
	 * 
	 * @param user
	 * @return
	 */
	public static String queryNotesSqlByName(Note note,User user) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(DBConstant.NoteTable.TABLE_NAME + " where ");
		sqlBuilder.append(NoteTable.COL_TITLE + "=" + note.getName());
		sqlBuilder
				.append(" and " + NoteTable.COL_USER + "=" + user.getUserNo());

		return sqlBuilder.toString();

	}
	
	/**
	 * ɾ�����û��ĵĸ����ʼ����ݵ�sql���
	 * @param item
	 * @param user
	 * @return
	 */
	public static String deleteNoteSql(Note note, User user) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("delete from " + NoteTable.TABLE_NAME);
		sqlBuilder.append(" where " + NoteTable.COL_TITLE + "="
				+ note.getName());
		sqlBuilder
				.append(" and " + NoteTable.COL_USER + "=" + user.getUserNo());
		return sqlBuilder.toString();

	}
	
	/**
	 * ��ѯ�����������
	 * @return
	 */
	public static String queryCTitleSql(){
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select " + CourseTable.COL_TITLE_ID );
		sqlBuilder.append("," + CourseTable.COL_TITLE_NAME );
		sqlBuilder.append("," + CourseTable.COL_CID+" from " +CourseTable.TABLE_NAME);
		return sqlBuilder.toString();
	}
	
	/**
	 * ��ѯ�ڶ��������
	 * @return
	 */
	public static String queryCCouserSql(){
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select distinct " + CourseTable.COL_CID );
		sqlBuilder.append(", " + CourseTable.COL_COUSER_NAME );
		sqlBuilder.append("," + CourseTable.COL_CTYPE_ID+" from " +CourseTable.TABLE_NAME);
		return sqlBuilder.toString();
	}
	
	/**
	 * ��ѯ��һ�������
	 * @return
	 */
	public static String queryCTypeSql(){
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select distinct " + CourseTable.COL_CTYPE_ID );
		sqlBuilder.append("," + CourseTable.COL_CTYPE_NAME+" from " +CourseTable.TABLE_NAME);
		return sqlBuilder.toString();
	}
}
