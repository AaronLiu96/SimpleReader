package com.android.simplereader.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Dragon丶Lz on 2016/2/26.
 */
public class GetImageUtils {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static Uri imageUri;
    private static final String TAG = "GetImageUtil";

    public static void takePhoto(Fragment fragment, Uri uri) {
        imageUri = uri;
        Log.d(TAG, "takePhoto() + 拍照，uri:" + imageUri);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        fragment.startActivityForResult(intent, TAKE_PHOTO);
    }

    public static void chooseFromAlbum(Fragment fragment, Uri uri) {
        imageUri = uri;
        Log.d(TAG, "chooseFromAlbum() + 从相册选择图片，uri:" + imageUri);
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.setType("image/*");
        fragment.startActivityForResult(intent, TAKE_PHOTO);
    }

    public static Uri getImageUri(String fileName) {
        Log.d(TAG, "getImageUri() + 开始创建文件:" + fileName + ".jpg");
        File outputImage = new File(Environment.getExternalStorageDirectory(), "MYJANDAN/userImage/" + fileName + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Log.d(TAG, "getImageUri() + 返回uri:" + imageUri);
        return imageUri;
    }

    public static void cropPhoto(Fragment fragment, int requestCode, int resultCode,
                                 ImageView imageView, Uri uri, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (data != null) {
                    imageUri = data.getData();
                } else {
                    imageUri = uri;
                }
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    fragment.startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bitmap = BitmapFactory.decodeStream(fragment.getActivity().getContentResolver()
                                .openInputStream(uri), null, options);
                        if (bitmap != null) {
                            Log.d(TAG, "cropPhoto() + 我觉得这一步根本就没运行！");
                            imageView.setImageBitmap(bitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}

