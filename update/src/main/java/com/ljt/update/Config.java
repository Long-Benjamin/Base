package com.ljt.update;

import android.os.Environment;

import java.io.File;

public class Config {

    public static final String APK_MKDIR = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "apkCache" + File.separator;
}
