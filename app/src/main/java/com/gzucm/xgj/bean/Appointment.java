package com.gzucm.xgj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by XQM on 2017/2/12.
 */

public class Appointment extends BmobObject {
    private BmobFile img_touxiang;
    private BmobFile img_appoint;
    private String peo_name;
    private String time_count;

    public BmobFile getImg_touxiang() {
        return img_touxiang;
    }

    public void setImg_touxiang(BmobFile img_touxiang) {
        this.img_touxiang = img_touxiang;
    }

    public BmobFile getImg_appoint() {
        return img_appoint;
    }

    public void setImg_appoint(BmobFile img_appoint) {
        this.img_appoint = img_appoint;
    }

    public String getPeo_name() {
        return peo_name;
    }

    public void setPeo_name(String peo_name) {
        this.peo_name = peo_name;
    }

    public String getTime_count() {
        return time_count;
    }

    public void setTime_count(String time_count) {
        this.time_count = time_count;
    }
}
