package com.gy.commonviewdemo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 Parcelable 序列化的一般写法
 1 read 与 write 顺序要一致
 2 要有一个CREATOR 变量
 */
public class RecordModel implements Parcelable{

    private String recordId;//用来标识一条记录的主键
    private String title;
    private String content;
    private String alarmTime;
    private int level;
    private String userId;
    private String labelNames;//里面有多条label记录，使用","隔开
    private int recordType;//记事类型
    public RecordModel(){

    }

    public RecordModel(Parcel in){
        recordId = in.readString();
        title=in.readString();
        content=in.readString();
        alarmTime=in.readString();
        level=in.readInt();
        userId=in.readString();
        labelNames =in.readString();
        recordType = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recordId);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(alarmTime);
        dest.writeInt(level);
        dest.writeString(userId);
        dest.writeString(labelNames);
        dest.writeInt(recordType);
    }

    public static final Parcelable.Creator<RecordModel> CREATOR = new Parcelable.Creator(){

        @Override
        public RecordModel createFromParcel(Parcel source) {
            RecordModel p = new RecordModel(source);
            return p;
        }

        @Override
        public RecordModel[] newArray(int size) {
            return new RecordModel[size];
        }
    };



}
