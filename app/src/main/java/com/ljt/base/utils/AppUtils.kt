package com.ljt.base.utils

import android.content.Context
import android.os.Build


object AppUtils {

    /**
     * 获取应用名称
     */
    fun getAppPackageName(context: Context): String{
        return context.packageName
    }

    /**
     * 获取应用版本名称
     */
    fun getAppVersionName(context: Context): String {

        // 获取packagemanager的实例
        val packageManager = context.packageManager
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        val packInfo = packageManager.getPackageInfo(getAppPackageName(context), 0)

        return packInfo.versionName
    }

    /**
     * 获取应用版本号
     */
    fun getAppVersionCode(context: Context): Long {

        // 获取packagemanager的实例
        val packageManager = context.packageManager
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        val packInfo = packageManager.getPackageInfo(getAppPackageName(context), 0)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packInfo.longVersionCode
        } else {
            packInfo.versionCode.toLong()
        }
    }



}