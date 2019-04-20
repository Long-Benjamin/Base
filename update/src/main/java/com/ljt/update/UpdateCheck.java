package com.ljt.update;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import okhttp3.*;

import java.io.*;

public class UpdateCheck {

    private Context mContext;

    public UpdateCheck(Context context){
        mContext = context;
    }

    public void checkPermissions(final String url, final OnDownloadListener listener){
        new RxPermissions((FragmentActivity) mContext)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            dowloadApk(url,listener);

                        }else{
                            new AlertDialog.Builder(mContext)
                                    .setTitle("提示")
                                    .setMessage("下载更新APP需要文件读写授权，是否去应用权限设置中心开启？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            goPermissionSettings();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                });

    }

    /** 跳转到应用权限设置 */
    private void goPermissionSettings() {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        mContext. startActivity(intent);
    }


    private Long startsPoint = 0L;

    private String getRootPath() {
        File file = new File(Config.APK_MKDIR);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }
    private File getTempFile(String url){
        return new File(getRootPath(), getFileName(url)+".temp");
    }

    private File getFile(String url){
        return new File(getRootPath(), getFileName(url));
    }

    private Long getFileStart(String url){
        return getTempFile(url).length();
    }

    private String getFileName(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private Call mCall = null;

    /** 下载apk */
    public void dowloadApk(final String url, final OnDownloadListener listener) {

         if (listener == null){
             throw new NullPointerException("method dowloadApk() listener can't be a null.");
         }
        //1、判断apk文件是否已经下载
        File vFile = getFile(url);
        if (vFile.exists() && vFile.length() > 1){
            listener.completeLoad(vFile.getAbsolutePath());
            return;
        }else{
            startsPoint = getFileStart(url);
        }

        //2、文件没有下载则开始下载，并设置开始
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("RANGE", "bytes=$startsPoint-")
                .build();
        mCall = client.newCall(request);

        mCall.enqueue(new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                listener.failRequest(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Long length = response.body().contentLength();
                if (length == 0L) {
                    // 说明文件已经下载完，直接跳转安装就好
                    listener.completeLoad(getFile(url).getAbsolutePath());
                    return;
                }
                listener.startLoad(length + startsPoint);

                // 保存文件到本地
                InputStream ins = null;
                RandomAccessFile randomAccessFile = null;
                BufferedInputStream bis = null;

                byte[] buff = new byte[2048];
                int len = 0;
                Long progress = startsPoint;
                Long total = length + startsPoint;
                try {
                    ins = response.body().byteStream();
                    bis = new BufferedInputStream(ins);

                    File file = getTempFile(url);
                    // 随机访问文件，可以指定断点续传的起始位置
                    randomAccessFile = new RandomAccessFile(file, "rwd");
                    randomAccessFile.seek(startsPoint);

                    while ((len = ins.read(buff)) != -1 ) {
                        randomAccessFile.write(buff, 0, len);
                        progress += len;
                        listener.loaddingLoad(progress*100/total);
                    }

                    // 下载完成
                    File newFile = getFile(url);
                    boolean isCreate = newFile.createNewFile();
                    if (isCreate) {
                        boolean flag = file.renameTo(newFile);
                        listener.completeLoad(newFile.getAbsolutePath());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    listener.failLoad(e.getLocalizedMessage());

                } finally {
                    try {
                        if (ins != null){
                            ins.close();
                        }
                        if (ins != null){
                            bis.close();
                        }
                        randomAccessFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        });
    }


    public void cancelDownload() {
        if (mCall != null)mCall.cancel();
        Log.e("取消下载：","---> Activitydistory");
    }
}
