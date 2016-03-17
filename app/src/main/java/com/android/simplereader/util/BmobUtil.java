package com.android.simplereader.util;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.simplereader.model.bean.Collection;
import com.android.simplereader.model.bean.Comment;
import com.android.simplereader.model.bean.FeedBack;
import com.android.simplereader.model.bean.Zero;

import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Dragon丶Lz on 2016/2/1.
 */
public class BmobUtil {

    private static final String TAG = "ZeroNoDataProblem--->>";

    /**
     * 查询最近发布的消息
     *
     * @param number 每次返回的数量
     */

    public static List<Zero> QueryAllDataZero(int number, Context mContext, final View v) {
        BmobQuery<Zero> query = new BmobQuery<Zero>();
        final List<Zero> data = new ArrayList<>();
        query.setLimit(number);
        query.findObjects(mContext, new FindListener<Zero>() {
            @Override
            public void onSuccess(List<Zero> list) {
                for (int i = 0; i < list.size(); i++) {
                    data.add(list.get(i));
                    Log.d(TAG, "data得到了数据——>" + data.get(i).getZeroDate());
                }
            }

            @Override
            public void onError(int i, String s) {
                Snackbar.make(v, "数据出错了，请稍后再试~", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });

        return data;
    }


    /**
     * 点赞的时候数字增加1
     */
    public static void UpdateGoodZero(final Context context,String itemId) {
        Zero zero = new Zero();
        zero.increment("ZeroGood");
        zero.update(context, itemId, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "谢谢你为作者点赞~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "点赞出错了！" + s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 提交Zero数据的方法
     */
    public static void PostZeroContent(final View view, Context context, String content, String userName) {

        Zero zero = new Zero();
        zero.setZeroContent(content);

        zero.setZeroGood(0);
        zero.setZeroName(userName);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = format.format(curDate);
        zero.setZeroDate(time);
        zero.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Snackbar.make(view, "提交成功了，刷新一下看看吧~", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Snackbar.make(view, "Sorry，提交失败了~", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });
    }


    /***
     * 提交评论
     */
    public static void PostZeroComment(final Context context, String content, String name, String id) {
        Comment comment = new Comment();
        comment.setComment_data(content);
        comment.setComment_name(name);
        comment.setZeroItemId(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = format.format(curDate);
        comment.setComment_date(time);
        comment.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "评论成功了~~~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "出现了错误：" + s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /***
     * 修改密码
     */

    public static void UpdateCurrentUserPassword(final String lastPassword, final String newPassword, final Context context, final View view) {

        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("username", (String) SPUtils.get(context, "name", ""));
        query.findObjects(context, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> object) {
                // TODO Auto-generated method stub
                object.get(0).updateCurrentUserPassword(context, lastPassword, newPassword, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Snackbar.make(view, "新密码修改成功~", Snackbar.LENGTH_LONG)
                                .setAction("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Snackbar.make(view, "修改失败" + s, Snackbar.LENGTH_LONG)
                                .setAction("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                    }
                });
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });


    }

    /***
     * 提交意见反馈
     */

    public static void CommitFeedBack(final Context context, final String data) {
        FeedBack feedBack = new FeedBack();
        feedBack.setData(data);
        feedBack.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "谢谢您的宝贵意见,我们会认真考虑~", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "反馈出错了：" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /***
     * 添加收藏
     */
    public static void PostCollection(final Context context, String username, String CollectionUserName, String CollectionTitle, String CollectionDate, String CollectionContent, String CollectionPic) {

        Collection collection = new Collection();
        collection.setCollectionContent(CollectionContent);
        collection.setCollectionUserName(CollectionUserName);
        collection.setUserName(username);
        collection.setCollectionPic(CollectionPic);
        collection.setCollectionTime(CollectionDate);
        collection.setCollectionTitle(CollectionTitle);
        collection.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context,"收藏失败:"+s,Toast.LENGTH_LONG).show();
            }
        });

    }


    /***
     * 删除操作
     */
    public static void DeleteCollectionItem(Context context,String objectId){
        Collection collection = new Collection();
        collection.setObjectId(objectId);
        collection.delete(context, new DeleteListener() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    /***
     * 查询当前用户的邮箱
     */
    public static void QueryEmail(final Context context){
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();

        query.addWhereEqualTo("username", (String)SPUtils.get(context,"name",""));
        query.findObjects(context, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> object) {
                // TODO Auto-generated method stub
                DialogUtils.ShowEmailDialog(context, object.get(0).getEmail());
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
               Toast.makeText(context,"出错了:"+msg,Toast.LENGTH_LONG).show();
            }
        });
    }
}

