package com.gy.commonviewdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

public class CallLogExample {

    public static void getCallLogs(Context context) {
        ContentResolver contentResolver = context.getContentResolver();


        // 指定想要查询的列
        String[] projection = new String[]{
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION
        };

        // 执行查询
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                CallLog.Calls.DATE + " DESC"); // 按日期降序排列

        // 遍历查询结果
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String callType = "";
                switch (type) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "Incoming";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Outgoing";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Missed";
                        break;
                }
                long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));

                // 打印通话记录
                System.out.println("Number: " + number + " Type: " + callType + " Date: " + date + " Duration: " + duration);
            } while (cursor.moveToNext());
        }

        // 关闭Cursor
        if (cursor != null) {
            cursor.close();
        }
    }
}
