package com.gzucm.xgj.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.gzucm.xgj.xgj_car.R;

/**
 * Created by XQM on 2017/2/13.
 */

public class ShareFragment extends Fragment{
    private GridView gridView;
    private Button btn_cancel;
    private Context mconText;
    int[] imageIds = new int[]{R.drawable.icon_weixin,R.drawable.icon_friend,R.drawable.icon_qq,R.drawable.icon_weibo};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mconText = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_share,container,false);
        return rootView;
    }
}
