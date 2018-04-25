package com.gzucm.xgj.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gzucm.xgj.activity.AppointActivity;
import com.gzucm.xgj.activity.DriTheoryStudyActivity;
import com.gzucm.xgj.activity.ForumActivity;
import com.gzucm.xgj.activity.SignUpActivity;
import com.gzucm.xgj.adapter.SchInfomationAdapter;
import com.gzucm.xgj.bean.ADInfo;
import com.gzucm.xgj.bean.Sch;
import com.gzucm.xgj.lib.CycleViewPager;
import com.gzucm.xgj.utils.ViewFactory;
import com.gzucm.xgj.xgj_car.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/7/19.
 */
public class FirstFragment extends Fragment implements View.OnClickListener{

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    private RelativeLayout rl_sign,rl_appoint,rl_study,rl_forum;
    private View newsLayout;

    private String[] imageUrls ={"http://bmob-cdn-7891.b0.upaiyun.com/2017/04/12/8031ec6240432b3380d5ae3b7badffe2.jpg",
            "http://bmob-cdn-7891.b0.upaiyun.com/2017/04/12/46162155401fe16080d009e5a6a08ea7.jpg",
            "http://bmob-cdn-7891.b0.upaiyun.com/2017/04/12/219922f040d75a3280519968fa75125a.jpg",
            "http://bmob-cdn-7891.b0.upaiyun.com/2017/04/12/0fc54ba640a00585803b99e8382e6b38.jpg",
            "http://bmob-cdn-7891.b0.upaiyun.com/2017/04/12/6740a693404c4a4b80ca0712c4a8e610.jpg"};
    private Context mContext;

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        newsLayout = inflater.inflate(R.layout.fragment_first, container,
                false);
        configImageLoader();
        initialize();
        return newsLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_sign:

                Intent intent = new Intent(mContext, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_appoint:
                Intent intent1 = new Intent(mContext, AppointActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_study:
                Intent intent2 = new Intent(mContext, DriTheoryStudyActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_forum:
                Intent intent3 = new Intent(mContext, ForumActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private void initialize() {

        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        rl_sign = (RelativeLayout) newsLayout.findViewById(R.id.rl_sign);
        rl_appoint = (RelativeLayout) newsLayout.findViewById(R.id.rl_appoint);
        rl_study = (RelativeLayout) newsLayout.findViewById(R.id.rl_study);
        rl_forum = (RelativeLayout) newsLayout.findViewById(R.id.rl_forum);
        mListView = (ListView) newsLayout.findViewById(R.id.sch_info_lv);
        rl_sign.setOnClickListener(this);
        rl_study.setOnClickListener(this);
        rl_appoint.setOnClickListener(this);
        rl_forum.setOnClickListener(this);

        //图片轮播
        adCarousel();

        //加载list列表
        loadlist();

    }

    /**
     * 加载list列表
     */
    private void loadlist() {

        BmobQuery<Sch> bmobQuery = new BmobQuery<Sch>();
        bmobQuery.setLimit(1000);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.findObjects(new FindListener<Sch>() {
            @Override
            public void done(final List<Sch> list, BmobException e) {

                if (e == null){
                    SchInfomationAdapter schInfomationAdapter = new SchInfomationAdapter(mContext,list);
                    schInfomationAdapter.notifyDataSetChanged();

                    //跳转对应的SchMainActivity即可，如何跳转
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                            //跳转到报名界面
                        }
                    });
                    mListView.setAdapter(schInfomationAdapter);
                }

            }
        });
    }

    /**
     * 图片轮播
     */
    private void adCarousel() {
        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(getActivity(),
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                        .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

}
