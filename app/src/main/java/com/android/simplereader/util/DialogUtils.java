package com.android.simplereader.util;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.simplereader.R;
import com.android.simplereader.ui.activity.LoginActivity;

/**
 * Created by Dragon丶Lz on 2016/2/4.
 */
public class DialogUtils {



    private static MaterialDialog dialog;

    /**
     * 所有的加载
     * @param context
     * @param Title
     * @param content
     */
    public static void onProcess(Context context,String Title,String content){

       dialog= new MaterialDialog.Builder(context)
                .title(Title)
                .content(content)
                .titleColor(context.getResources().getColor(R.color.main))
                .backgroundColor(context.getResources().getColor(R.color.white))
                .positiveColor(context.getResources().getColor(R.color.main))
                .negativeColor(context.getResources().getColor(R.color.dialog_nav))
                .theme(Theme.LIGHT)
                .progress(true, 100)
                .cancelable(true)
                .show();
    }

    /***
     * 所有的取消
     */
    public static void dissmissProcess(){
        dialog.dismiss();
    }

    /***
     * 需要登录
     * @param context
     */
    public static void NeedLogin(final Context context){
        new MaterialDialog.Builder(context)
                .title("温馨提示")
                .content("您还没有登录呐~")
                .positiveText("好，我要去登录")
                .negativeText("不，我就不要~")
                .titleColor(context.getResources().getColor(R.color.main))
                .backgroundColor(context.getResources().getColor(R.color.white))
                .positiveColor(context.getResources().getColor(R.color.main))
                .negativeColor(context.getResources().getColor(R.color.dialog_nav))
                .cancelable(false)
                .theme(Theme.LIGHT)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        Intent ToLogin = new Intent(context, LoginActivity.class);
                        ToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        context.startActivity(ToLogin);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {

                    }
                })
                .show();
    }

    /**
     * 提交意见反馈
     * @param context
     */
    public static void PostFeedBack(final Context context){
        View FeedBackView = LayoutInflater.from(context).inflate(R.layout.view_feedback, null);
        final AppCompatEditText feedbackData = (AppCompatEditText) FeedBackView.findViewById(R.id.feedback_data);

        new MaterialDialog.Builder(context)
                .title("请告诉我们您的意见~")
                .customView(FeedBackView, false)
                .positiveText("好了，我说完了~")
                .negativeText("算了，不想说了~")
                .titleColor(context.getResources().getColor(R.color.main))
                .backgroundColor(context.getResources().getColor(R.color.white))
                .positiveColor(context.getResources().getColor(R.color.main))
                .negativeColor(context.getResources().getColor(R.color.dialog_nav))
                .cancelable(false)
                .theme(Theme.LIGHT)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        BmobUtil.CommitFeedBack(context, feedbackData.getText().toString());
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();

    }


    public static void UpdatePasswordDialog(final Context context,final View view){
        View UpdatePasswordView = LayoutInflater.from(context).inflate(R.layout.view_update_password, null);
        final  AppCompatEditText oldPassword_edt = (AppCompatEditText) UpdatePasswordView.findViewById(R.id.update_password_old_password);
        final  AppCompatEditText newPassword_edt = (AppCompatEditText) UpdatePasswordView.findViewById(R.id.update_password_new_password);
        final  AppCompatEditText twoPassword_edt = (AppCompatEditText) UpdatePasswordView.findViewById(R.id.update_password_new_password_twice);
        new MaterialDialog.Builder(context)
                .title("修改密码")
                .customView(UpdatePasswordView, false)
                .positiveText("好了，我确定了~")
                .negativeText("算了，我不改了~")
                .titleColor(context.getResources().getColor(R.color.main))
                .backgroundColor(context.getResources().getColor(R.color.white))
                .positiveColor(context.getResources().getColor(R.color.main))
                .negativeColor(context.getResources().getColor(R.color.dialog_nav))
                .cancelable(false)
                .theme(Theme.LIGHT)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        String oldPassword = oldPassword_edt.getText().toString();
                        String newPassword = newPassword_edt.getText().toString();
                        String twoPassword = twoPassword_edt.getText().toString();
                        if (newPassword.equals(twoPassword)) {
                            BmobUtil.UpdateCurrentUserPassword(oldPassword,newPassword,context,view);
                        } else {
                            Snackbar.make(view, "Sorry，新密码的输入不一致", Snackbar.LENGTH_LONG)
                                    .setAction("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }).show();
                        }

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();


    }

    public static void ShowEmailDialog(Context context,String email){
        new MaterialDialog.Builder(context)
                .title("您当前的邮箱")
                .content(email)
                .positiveText("我知道了")

                .titleColor(context.getResources().getColor(R.color.main))
                .backgroundColor(context.getResources().getColor(R.color.white))
                .positiveColor(context.getResources().getColor(R.color.main))
                .cancelable(false)
                .theme(Theme.LIGHT)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
