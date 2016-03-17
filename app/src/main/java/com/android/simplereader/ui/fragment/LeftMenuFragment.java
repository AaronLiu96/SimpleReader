package com.android.simplereader.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.LeftMenu;
import com.android.simplereader.model.callback.MyPopupOnTouchListner;
import com.android.simplereader.presenter.LeftFragmentPresent;
import com.android.simplereader.ui.activity.AboutActivity;
import com.android.simplereader.ui.activity.LoginActivity;
import com.android.simplereader.ui.adapter.LeftMenuAdapter;
import com.android.simplereader.ui.view.ILeftMenuFragment;
import com.android.simplereader.ui.widget.RoundImageView;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.GetImageUtils;
import com.android.simplereader.util.PicPopupWindowUtils;
import com.android.simplereader.util.SPUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Dragon丶Lz on 2016/1/28.
 */
public class LeftMenuFragment extends Fragment implements ILeftMenuFragment,AdapterView.OnItemClickListener{

    private TextView LeftMenu_Name;
    private ListView LeftMenu_List;
    private TextView LeftMenu_finish;
    private LeftMenuAdapter leftMenuAdapter;
    private LeftFragmentPresent leftFragmentPresent;
    private RoundImageView LeftMenu_userImage;
    private List<LeftMenu> dataList = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private TextView gallery;
    private TextView photo;
    private Uri imageUri;
    private String userName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_left,container,false);
        initView(view);
        leftMenuAdapter = new LeftMenuAdapter(getActivity(),R.layout.fragment_leftmenu_item,dataList);

        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            LeftMenu_Name.setText((String) SPUtils.get(getActivity(), "name", ""));
            userName = (String) SPUtils.get(getActivity(), "name", "");
        }else {
            LeftMenu_Name.setText("未登录");
        }
        LeftMenu_List.setAdapter(leftMenuAdapter);
        LeftMenu_List.setOnItemClickListener(this);
        initUserInfo();
        initPopupWindow();

        return view;
    }

    private void initView(View v){
        LeftMenu_userImage = (RoundImageView) v.findViewById(R.id.LeftMenu_userImage);
        LeftMenu_userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAsDropDown(v);
            }
        });
        LeftMenu_Name = (TextView) v.findViewById(R.id.LeftMenu_Name);
        LeftMenu_List = (ListView) v.findViewById(R.id.LeftMenu_List);
        LeftMenu_finish = (TextView) v.findViewById(R.id.LeftMenu_finish);
        LeftMenu_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollectorUtils.finishAll();
            }
        });



    }


    @Override
    public void setName(String name) {
        LeftMenu_Name.setText(name);
    }

    @Override
    public void refreshData(List<LeftMenu> dataList) {
        Log.d("LeftListProblem-->>","我已经执行了refreshData");
        leftMenuAdapter = new LeftMenuAdapter(getActivity(),R.layout.fragment_leftmenu_item,dataList);
        Log.d("LeftListProblem-->>", dataList.get(0).getName());
        LeftMenu_List.setAdapter(leftMenuAdapter);
        Log.d("LeftListProblem-->>", "已经执行了setAdapter");
    }


    private void initData(){
        dataList.add(new LeftMenu("意见反馈"));
        dataList.add(new LeftMenu("关于我们"));
        dataList.add(new LeftMenu("切换账号"));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()){
            case R.id.LeftMenu_List:
                switch (position){
                    case 0:
                        DialogUtils.PostFeedBack(getActivity());
                        break;
                    case 1:
                        Intent ToAbout = new Intent(getActivity(), AboutActivity.class);
                        ToAbout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(ToAbout);
                        break;
                    case 2:
                        Intent ToLogin = new Intent(getActivity(), LoginActivity.class);
                        ToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(ToLogin);
                        getActivity().finish();
                        break;
                    default:
                        break;
                }
                break;

        }
    }

    private void initUserInfo() {
        //获取uri地址
        SharedPreferences sp = getActivity().getSharedPreferences("data" + userName, Context.MODE_APPEND);
        imageUri = Uri.parse(sp.getString("image_uri", ""));
        //将保存的头像显示出来
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                    .openInputStream(imageUri), null, options);

            if (bitmap != null) {
                LeftMenu_userImage.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initPopupWindow() {
        mPopupWindow = PicPopupWindowUtils.getPopouWiondow(getActivity());
        gallery = (TextView) mPopupWindow.getContentView().findViewById(R.id.tv_gallery);
        gallery.setOnTouchListener(new MyPopupOnTouchListner());
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageUri = GetImageUtils.getImageUri(userName+ "'s_image");
                //保存uri
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data" + userName, getActivity().MODE_PRIVATE).edit();
                editor.putString("image_uri", imageUri.toString());
                editor.apply();
                GetImageUtils.chooseFromAlbum(LeftMenuFragment.this, imageUri);
            }
        });
        photo = (TextView) mPopupWindow.getContentView().findViewById(R.id.tv_photo);
        photo.setOnTouchListener(new MyPopupOnTouchListner());
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = GetImageUtils.getImageUri(userName + "'s_image");
                //保存uri
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data" + userName, getActivity().MODE_PRIVATE).edit();
                editor.putString("image_uri", imageUri.toString());
                editor.apply();
                GetImageUtils.takePhoto(LeftMenuFragment.this, imageUri);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("HeraImageProblem-->>", "我执行了onActivityResult");
        Log.d("HeraImageProblem-->>",requestCode+"");
        Log.d("HeraImageProblem-->>",resultCode+"");
        if (requestCode == 1 || requestCode == 2) {
            GetImageUtils.cropPhoto(this, requestCode, resultCode, LeftMenu_userImage, imageUri, data);
        }
    }
}
