package com.gzucm.xgj.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzucm.xgj.adapter.ForumAdapter;
import com.gzucm.xgj.bean.Forum;
import com.gzucm.xgj.xgj_car.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class ForumActivity extends BaseActivity{


    private ListView mListView;
    private ForumAdapter forumAdapter;
    private Context mContext;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_forum);
        mListView = (ListView)findViewById(R.id.forum_lv);
    }

    @Override
    public void initViews() {
        loadlist();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    /**
     * 加载list列表
     */
    private void loadlist() {

        BmobQuery<Forum> userQuery = new BmobQuery<Forum>();
        userQuery.setLimit(1000);
        userQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        userQuery.findObjects(new FindListener<Forum>() {
            @Override
            public void done(final List<Forum> list, BmobException e) {

                if (e == null){
                    forumAdapter = new ForumAdapter(ForumActivity.this,list);
                    forumAdapter.notifyDataSetChanged();
                    Log.e("ss","" + list.get(0).getName());
                    //跳转对应的SchMainActivity即可，如何跳转
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                            //跳转到报名界面
                        }
                    });
                    mListView.setAdapter(forumAdapter);
                }

            }
        });
    }
}
