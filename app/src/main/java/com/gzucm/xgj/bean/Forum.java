package com.gzucm.xgj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/15 0015.
 */

public class Forum extends BmobObject implements Parcelable,Serializable {

    private String name;
    private String Forum_remark;


    protected Forum(Parcel in) {
        name = in.readString();
        Forum_remark = in.readString();

    }

    public static final Creator<Forum> CREATOR = new Creator<Forum>() {
        @Override
        public Forum createFromParcel(Parcel in) {
            return new Forum(in);
        }

        @Override
        public Forum[] newArray(int size) {
            return new Forum[size];
        }
    };

    public String getFornum_remark() {
        return Forum_remark;
    }

    public void setFornum_remark(String fornum_remark) {
        Forum_remark = fornum_remark;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(Forum_remark);

    }
}
