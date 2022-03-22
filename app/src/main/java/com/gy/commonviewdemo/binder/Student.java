package com.gy.commonviewdemo.binder;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    String name = "gy";
    int code = 100;

    Student(){}

    protected Student(Parcel in) {
      this.name = in.readString();
      this.code = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(code);
    }
}
