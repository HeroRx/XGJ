package com.gzucm.xgj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public class Sch extends BmobObject implements Parcelable,Serializable{

    private BmobFile sch_image;
    public String sch_name;
    public String sch_location;
    public String sch_phone;
    public String sch_location_detail;
    public Integer sch_score_integer;
    public Integer sch_score_decimals;
    public BmobFile sch_icon;
    public String information;

    protected Sch(Parcel in) {
        sch_name = in.readString();
        sch_location = in.readString();
        sch_phone = in.readString();
        sch_location_detail = in.readString();
        information = in.readString();
    }

    public static final Creator<Sch> CREATOR = new Creator<Sch>() {
        @Override
        public Sch createFromParcel(Parcel in) {
            return new Sch(in);
        }

        @Override
        public Sch[] newArray(int size) {
            return new Sch[size];
        }
    };

    public BmobFile getSch_icon() {
        return sch_icon;
    }

    public void setSch_icon(BmobFile sch_icon) {
        this.sch_icon = sch_icon;
    }

    public Integer getSch_score_integer() {
        return sch_score_integer;
    }

    public void setSch_score_integer(Integer sch_score_integer) {
        this.sch_score_integer = sch_score_integer;
    }

    public Integer getSch_score_decimals() {
        return sch_score_decimals;
    }

    public void setSch_score_decimals(Integer sch_score_decimals) {
        this.sch_score_decimals = sch_score_decimals;
    }

    public String getSch_location_detail() {
        return sch_location_detail;
    }

    public void setSch_location_detail(String sch_location_detail) {
        this.sch_location_detail = sch_location_detail;
    }

    public String getSch() {return sch_name;}

    public void setSch(String sch) {
        this.sch_name = sch;
    }

    public BmobFile getsch_image(){
        return sch_image;
    }

    public void setsch_image(BmobFile sch_image){
        this.sch_image = sch_image;
    }

    public String getSch_location() {
        return sch_location;
    }

    public void setSch_location(String sch_location) {
        this.sch_location = sch_location;
    }

    public String getSch_phone() {
        return sch_phone;
    }

    public void setSch_phone(String sch_phone) {
        this.sch_phone = sch_phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sch_name);
        parcel.writeString(sch_location);
        parcel.writeString(sch_phone);
        parcel.writeString(sch_location_detail);
        parcel.writeString(information);
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
