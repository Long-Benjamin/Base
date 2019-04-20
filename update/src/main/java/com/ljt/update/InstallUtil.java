package com.ljt.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class InstallUtil {

    public static void install(Context context, String apkPath){

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File file = new File(apkPath);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);


        } catch (Exception e) {
            Log.e("安装失败：","installApk 失败！");
        }


    }
}
