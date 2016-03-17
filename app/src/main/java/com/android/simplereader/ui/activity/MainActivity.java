package com.android.simplereader.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.presenter.MainActivityPresent;
import com.android.simplereader.ui.fragment.EssayFragment;
import com.android.simplereader.ui.fragment.StoriesFragment;
import com.android.simplereader.ui.fragment.ZeroFragment;
import com.android.simplereader.ui.fragment.MineFragment;
import com.android.simplereader.ui.view.IMainActivityView;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.SPUtils;
import com.nineoldandroids.view.ViewHelper;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements View.OnClickListener,IMainActivityView{
    //这是Fragment
    private Fragment findFragment;
    private Fragment friendFragment;
    private Fragment attentionFragment;
    private Fragment mineFragment;
    private FragmentManager fragmentManager;
    //present层
    private MainActivityPresent mainActivityPresent;
    //ui层
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.attention_Layout)
    View attention_Layout;
    @Bind(R.id.attention_text)
    TextView attention_text;
    @Bind(R.id.attention_image)
    ImageView attention_image;
    @Bind(R.id.find_Layout)
    View find_Layout;
    @Bind(R.id.find_text)
    TextView find_text;
    @Bind(R.id.find_image)
    ImageView find_image;
    @Bind(R.id.friend_Layout)
    View friend_Layout;
    @Bind(R.id.friend_text)
    TextView friend_text;
    @Bind(R.id.friend_image)
    ImageView friend_image;
    @Bind(R.id.mine_Layout)
    View mine_Layout;
    @Bind(R.id.mine_text)
    TextView mine_text;
    @Bind(R.id.mine_image)
    ImageView mine_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollectorUtils.addActivity(this);
        ButterKnife.bind(this);
        initView();
        mainActivityPresent = new MainActivityPresent(this);

        fragmentManager= getFragmentManager();
        mainActivityPresent.showFragment(0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.find_Layout:
                mainActivityPresent.showFragment(0);
                break;
            case R.id.attention_Layout:
                mainActivityPresent.showFragment(1);
                break;
            case R.id.friend_Layout:
                mainActivityPresent.showFragment(2);
                break;
            case R.id.mine_Layout:
                mainActivityPresent.showFragment(3);
                break;
        }

    }
    private void initView(){
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                slideAnim(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        attention_Layout.setOnClickListener(this);
        find_Layout.setOnClickListener(this);
        friend_Layout.setOnClickListener(this);
        mine_Layout.setOnClickListener(this);
    }

    @Override
    public void setTabSelection(int index) {

        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:

                find_image.setImageResource(R.mipmap.ic_stories_select);
                find_text.setTextColor(MainActivity.this.getResources().getColor(R.color.main));
                if (findFragment == null) {
                        findFragment = new StoriesFragment();
                        transaction.add(R.id.content, findFragment);
                    } else {
                        transaction.show(findFragment);
                    }
                    break;
            case 1:

                attention_image.setImageResource(R.mipmap.ic_menu_home_select);
                        attention_text.setTextColor(MainActivity.this.getResources().getColor(R.color.main));
                        if (attentionFragment == null) {
                            attentionFragment = new EssayFragment();
                            transaction.add(R.id.content, attentionFragment);
                        } else {
                            transaction.show(attentionFragment);
                        }
                        break;
            case 2:
                    if ((boolean) SPUtils.get(MainActivity.this, "is_login", false)){
                        friend_text.setTextColor(MainActivity.this.getResources().getColor(R.color.main));
                        friend_image.setImageResource(R.mipmap.ic_menu_allfriends_select);
                        if (friendFragment == null) {
                            friendFragment = new ZeroFragment();
                            transaction.add(R.id.content, friendFragment);
                        } else {
                            transaction.show(friendFragment);
                            }
                        }else {
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("温馨提示")
                                .content("您还没有登录呐~")
                                .positiveText("好，我要去登录")
                                .negativeText("不，我就不要~")
                                .titleColor(MainActivity.this.getResources().getColor(R.color.main))
                                .backgroundColor(MainActivity.this.getResources().getColor(R.color.white))
                                .positiveColor(MainActivity.this.getResources().getColor(R.color.main))
                                .negativeColor(MainActivity.this.getResources().getColor(R.color.dialog_nav))
                                .cancelable(false)
                                .theme(Theme.LIGHT)
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {

                                        Intent ToLogin = new Intent(MainActivity.this, LoginActivity.class);
                                        ToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                        startActivity(ToLogin);
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        setTabSelection(1);
                                    }
                                })
                                .show();

                    }
                        break;
            case 3:
                if ((boolean) SPUtils.get(MainActivity.this, "is_login", false)) {
                    mine_image.setImageResource(R.mipmap.ic_menu_friendslist_select);

                    mine_text.setTextColor(MainActivity.this.getResources().getColor(R.color.main));
                    if (mineFragment == null) {
                        mineFragment = new MineFragment();
                        transaction.add(R.id.content, mineFragment);
                    } else {
                        transaction.show(mineFragment);
                    }
                }else {
                    new MaterialDialog.Builder(MainActivity.this)
                            .title("温馨提示")
                            .content("您还没有登录呐~")
                            .positiveText("好，我要去登录")
                            .negativeText("不，我就不要~")
                            .titleColor(MainActivity.this.getResources().getColor(R.color.main))
                            .backgroundColor(MainActivity.this.getResources().getColor(R.color.white))
                            .positiveColor(MainActivity.this.getResources().getColor(R.color.main))
                            .negativeColor(MainActivity.this.getResources().getColor(R.color.dialog_nav))
                            .cancelable(false)
                            .theme(Theme.LIGHT)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {

                                    Intent ToLogin = new Intent(MainActivity.this, LoginActivity.class);
                                    ToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(ToLogin);
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    setTabSelection(1);
                                }
                            })
                            .show();
                }
                break;

        }
        transaction.commit();
    }



    private void clearSelection() {
        find_text.setTextColor(Color.parseColor("#82858b"));
        attention_image.setImageResource(R.mipmap.ic_menu_home);
        friend_text.setTextColor(Color.parseColor("#82858b"));
        find_image.setImageResource(R.mipmap.ic_stories);
        attention_text.setTextColor(Color.parseColor("#82858b"));
        friend_image.setImageResource(R.mipmap.ic_menu_allfriends);
        mine_text.setTextColor(Color.parseColor("#82858b"));
        mine_image.setImageResource(R.mipmap.ic_menu_friendslist);
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (attentionFragment != null) {
            transaction.hide(attentionFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
        if (friendFragment !=null){
            transaction.hide(friendFragment);
        }

    }

    //滑动动画
    private void slideAnim(View drawerView, float slideOffset){
        View contentView = drawerLayout.getChildAt(0);
        // slideOffset表示菜单项滑出来的比例，打开菜单时取值为0->1,关闭菜单时取值为1->0
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;
        float leftScale = 1 - 0.3f * scale;

        ViewHelper.setScaleX(drawerView, leftScale);
        ViewHelper.setScaleY(drawerView, leftScale);
        ViewHelper.setAlpha(drawerView, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(contentView, drawerView.getMeasuredWidth()
                * (1 - scale));
        ViewHelper.setPivotX(contentView, 0);
        ViewHelper.setPivotY(contentView, contentView.getMeasuredHeight() / 2);
        contentView.invalidate();
        ViewHelper.setScaleX(contentView, rightScale);
        ViewHelper.setScaleY(contentView, rightScale);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
    }
}
