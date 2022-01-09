package com.gy.commonviewdemo.db.model;

import android.content.ContentResolver;
import android.net.Uri;


/**
 * 用户表
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015-07-03  15:46
 */
public class UserColumns  {
    public final static String PACKAGE_NAME="com.gy.commonviewdemo";
    public final static String AUTHORITY = PACKAGE_NAME+ ".provider";
    public final static String _ID="_id";
    public final static String CREATAT="creat_time";
    public final static String UPDATEAT="update_time";
    public final static String TABLE_NAME="user";
    public final static String USERNAME="username";
    public final static String PASSWORD="password";
    public final static String EMAIL="email";
    public final static String PHONE="phone";
    public static final String PROFILE_IMAGE_URL = "profile_image_url";
    public final static Uri CONTENT_URI= Uri.parse("content://"+ AUTHORITY+"/"+TABLE_NAME);

    // 知识点：这里的text 前后的空格不可丢
    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + " ( "
            + _ID + " integer primary key autoincrement, "
            + USERNAME + " text not null, "
            + PASSWORD + " text not null, "
            + EMAIL + " text not null, "
            + PHONE + " text, "
            + PROFILE_IMAGE_URL + " text, "
            + CREATAT + " text not null, "
            + UPDATEAT + " text not null, "
            + "unique ( "
            + USERNAME
            + " ) on conflict ignore );";

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mykeep.user";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mykeep.user";
}
