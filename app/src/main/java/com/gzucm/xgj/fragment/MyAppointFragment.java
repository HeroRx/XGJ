package com.gzucm.xgj.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzucm.xgj.xgj_car.R;

/**
 * Created by Administrator on 2016/7/19.
 */
public class MyAppointFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newsLayout = inflater.inflate(R.layout.fragment_myappoint, container,
                false);
        return newsLayout;
    }
}
