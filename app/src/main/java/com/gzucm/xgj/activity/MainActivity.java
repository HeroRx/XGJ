package com.gzucm.xgj.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gzucm.xgj.domin.MyUser;
import com.gzucm.xgj.fragment.FirstFragment;
import com.gzucm.xgj.fragment.MyAppointFragment;
import com.gzucm.xgj.fragment.MyDrivingFragment;
import com.gzucm.xgj.view.CustomImageView;
import com.gzucm.xgj.view.DragLayout;
import com.gzucm.xgj.xgj_car.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/5/7.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    MyUser myUser = new MyUser();

    //�໬
    private DragLayout dl;


    private FirstFragment firstFragment;
    private MyDrivingFragment myDrivingFragment;
    private MyAppointFragment myAppointFragment;

    private ListView lv;


    /**
     * �׽��沼��
     */
    public View firstLayout;

    /**
     * �ҵļ�У���沼��
     */
    private View myDrivingLayout;

    /**
     * �ҵ�ԤԼ���沼��
     */
    private View myAppointLayout;

    /**
     * ��Tab��������ʾ�׽���ͼ��Ŀؼ�
     */
    private ImageView firstImage;

    /**
     * ��Tab��������ʾ�ҵļ�У ͼ��ؼ�
     */
    private ImageView myDrivingImage;

    /**
     * ��Tab��������ʾ�ҵ�ԤԼ ͼ��ؼ�
     */
    private ImageView myAppointImage;

    /**
     * ��Tab��������ʾ�׽������Ŀؼ�
     */
    private TextView firstText;

    /**
     * ��Tab��������ʾ�ҵļ�У����Ŀؼ�
     */
    private TextView myDrivingText;

    /**
     * ��Tab��������ʾ�ҵ�ԤԼ����Ŀؼ�
     */
    private TextView myAppointText;

    /**
     * ���ڶ�Fragment���й���
     */
    private android.support.v4.app.FragmentManager fragmentManager;

    //ѡ��ͷ���dialog
    private View view;
    Dialog dialog;

    private Button from_gallery, from_camera, cancel_choosephoto;
    //ͷ����ʾ
    private ImageView iv_bottom;
    private Bitmap head;//ͷ��Bitmap
    private static String path = "/sdcard";//sd·��

    //--



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDragLayout();
        initViews();
//        fragmentManager = getFragmentManager();
        fragmentManager = getSupportFragmentManager();
        // ��һ������ʱѡ�е�0��tab
        setTabSelection(0);


    }

    /**
     * �����¼
     */


    /**
     * �໬��ʼ��
     */
    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
//				shake();
            }

            @Override
            public void onDrag(float percent) {
//				ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    /**
     * ��ʼ���ؼ�
     */
    private void initViews() {
        firstLayout = findViewById(R.id.first_layout);
        myDrivingLayout = findViewById(R.id.driving_layout);
        myAppointLayout = findViewById(R.id.appoint_layout);
        firstImage = (ImageView) findViewById(R.id.first_image);
        myDrivingImage = (ImageView) findViewById(R.id.driving_image);
        myAppointImage = (ImageView) findViewById(R.id.appoint_image);
        firstText = (TextView) findViewById(R.id.first_text);
        myDrivingText = (TextView) findViewById(R.id.driving_text);
        myAppointText = (TextView) findViewById(R.id.appoint_text);
        firstLayout.setOnClickListener(this);
        myDrivingLayout.setOnClickListener(this);
        myAppointLayout.setOnClickListener(this);

        //ͷ��ѡ��
        iv_bottom = (CustomImageView) findViewById(R.id.iv_bottom);
        Bitmap bt = BitmapFactory.decodeFile(path + "/head.jpg");
        if (bt != null) {
//            Drawable drawable = new BitmapDrawable(bt);
//            iv_bottom.setImageDrawable(drawable);
              iv_bottom.setImageBitmap(bt);
        } else {
            //���SD����û������Ҫ�ӷ�����ȡͷ��ȡ������ͷ���ٱ�����SD��
        }
        iv_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
                dialog = new Dialog(MainActivity.this, R.style.transparentFrameWindowStyle);
                dialog.setContentView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                Window window = dialog.getWindow();

                // ������ʾ����
                window.setWindowAnimations(R.style.main_menu_animstyle);
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = 0;
                wl.y = getWindowManager().getDefaultDisplay().getHeight();
                // ������������Ϊ�˱�֤��ť����ˮƽ����
                wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                // ������ʾλ��
                dialog.onWindowAttributesChanged(wl);
                // ���õ����Χ��ɢ
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                from_gallery = (Button)view.findViewById(R.id.from_gallery);
                from_camera = (Button)view.findViewById(R.id.from_camera);
                cancel_choosephoto = (Button) view.findViewById(R.id.cancel_choosephoto);
                cancel_choosephoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                from_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent1, 1);
                    }
                });

                from_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                        startActivityForResult(intent2,2);
                    }
                });

            }
        });


        /**
         * �໬�б�
         */
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_text, new String[]{"�ҵ��ղ�", "����", "������"}));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(MainActivity.this, "�ҵ��ղ�", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "����", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "������", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {//��ͼƬ����
                    cropPhoto(data.getData());
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {//�������������
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null){
                        setPicToView(head);
                        addToBmob();
                        iv_bottom.setImageBitmap(head);
                        dialog.dismiss();
                    }

                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ����ϵͳ�Ĳü�
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        // aspectX aspectY �ǿ�ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY �ǲü�ͼƬ���
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * ͷ�񱣴���sd����
     *
     * @param mBitmap
     */
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;//���sd���Ƿ���ã���������return
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdir();//�����ļ���

        String fileName = path + "/head.jpg";
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToBmob(){
        String fileName = path + "/head.jpg";
        final BmobFile bmobFile = new BmobFile(new File(fileName));
        myUser.setIcon(bmobFile);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--���ص��ϴ��ļ���������ַ
                    toast("�ϴ��ļ��ɹ�:" + bmobFile.getFileUrl());
                }else{
                    toast("�ϴ��ļ�ʧ�ܣ�" + e.getMessage());
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_layout:
                setTabSelection(0);
                break;
            case R.id.driving_layout:
                setTabSelection(1);
                break;
            case R.id.appoint_layout:
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    /**
     * ���ݴ����index����������ѡ�е�tabҳ��
     *
     * @param i ÿ��tabҳ��Ӧ���±ꡣ0��ʾ��Ϣ��1��ʾ��ϵ�ˣ�2��ʾ��̬��3��ʾ���á�
     */
    private void setTabSelection(int i) {
        // ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
        clearSelection();
        // ����һ��Fragment����
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        // �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
        hideFragments(transaction);
        switch (i) {
            case 0:
                firstImage.setImageResource(R.drawable.first_selected);
                firstText.setTextColor(Color.parseColor("#0e8bf0"));
                if (firstFragment == null) {
                    firstFragment = new FirstFragment();
                    transaction.add(R.id.content, firstFragment);
                } else {
                    transaction.show(firstFragment);
                }
                break;
            case 1:
                myDrivingImage.setImageResource(R.drawable.driving_selected);
                myDrivingText.setTextColor(Color.parseColor("#0e8bf0"));
                if (myDrivingFragment == null) {
                    myDrivingFragment = new MyDrivingFragment();
                    transaction.add(R.id.content, myDrivingFragment);
                } else {
                    transaction.show(myDrivingFragment);
                }
                break;
            case 2:
            default:
                myAppointImage.setImageResource(R.drawable.appoint_selected);
                myAppointText.setTextColor(Color.parseColor("#0e8bf0"));
                if (myAppointFragment == null) {
                    myAppointFragment = new MyAppointFragment();
                    transaction.add(R.id.content, myAppointFragment);
                } else {
                    transaction.show(myAppointFragment);
                }
                break;
        }

        transaction.commit();
    }


    private void hideFragments(android.support.v4.app.FragmentTransaction transaction) {
        if (firstFragment != null) {
            transaction.hide(firstFragment);
        }
        if (myDrivingFragment != null) {
            transaction.hide(myDrivingFragment);
        }
        if (myAppointFragment != null) {
            transaction.hide(myAppointFragment);
        }
    }

    private void clearSelection() {
        firstImage.setImageResource(R.drawable.first_unselected);
        firstText.setTextColor(Color.parseColor("#cdcdcd"));
        myDrivingImage.setImageResource(R.drawable.driving_unselected);
        myDrivingText.setTextColor(Color.parseColor("#cdcdcd"));
        myAppointImage.setImageResource(R.drawable.appoint_unselected);
        myAppointText.setTextColor(Color.parseColor("#cdcdcd"));
    }

    private void toast(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_LONG).show();
    }
}
