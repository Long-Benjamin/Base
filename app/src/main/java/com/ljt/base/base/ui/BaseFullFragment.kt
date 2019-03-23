package com.ljt.base.base.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.WindowManager
import cn.com.iyin.utils.OsUtil
import com.ljt.base.commond.Config
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * @AUTHOR: LJT
 * @DATE:  2019/1/23
 * @DESCRIPTION：没有标题栏和状态栏
 */
abstract class BaseFullFragment: RxFragment() {

    val TAG = this.javaClass.simpleName


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置状态栏字体颜色
        if (getStatusDarkMode()) {
            setDarkMode()
        }else{
            setLightMode()
        }
    }

    open fun getStatusDarkMode(): Boolean {
        return true
    }


    //深色模式
    fun setDarkMode() {
        setImmersiveStatusBar(true, Color.DKGRAY)
    }

    //白色模式
    fun setLightMode() {
        setImmersiveStatusBar(false, Color.WHITE)
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    protected fun setImmersiveStatusBar(fontIconDark: Boolean, statusBarColor: Int) {
        var statusBarPlaceColor = statusBarColor
        setTranslucentStatus()
        if (fontIconDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    || OsUtil.isMIUI
                    || OsUtil.isFlyme) {
                setStatusBarFontIconDark(true)
            } else {
                if (statusBarPlaceColor == Color.WHITE) {
                }
            }
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private fun setStatusBarFontIconDark(dark: Boolean) {
        // 小米MIUI
        try {

            var window = activity?.window
            var clazz = window?.javaClass
            var layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            var field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            var darkModeFlag = field.getInt(layoutParams)
            var extraFlagField = clazz?.getMethod("setExtraFlags", Int::class.java, Int::class.java)
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField?.invoke(window, darkModeFlag, darkModeFlag)
            } else {       //清除黑色字体
                extraFlagField?.invoke(window, 0, darkModeFlag)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 魅族FlymeUI
        try {
            var window = activity?.window
            var lp = window?.attributes
            var darkFlag = lp!!::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            var meizuFlags = lp::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true

            var bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (true) (value or bit) else (value and bit.inv())

            meizuFlags.setInt(lp, value)
            window?.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                activity?.window?.decorView?.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    /**
     * 设置状态栏透明
     */
    private fun setTranslucentStatus() {

        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity?.window
            window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }


    fun getFragment(): Fragment {
        return this
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun showToast(info: String) {
//        AnyinToast.showToast(this.requireActivity(),info)
    }

    fun showProgress() {

    }


    /**
     * 页面跳转 部分 -》
     */
    protected fun startActivity(cls:Class<*>){
        startActivity(Intent(this.context,cls))
    }

    protected fun startActivity(cls:Class<*>, bundle: Bundle){
        var intent = Intent(this.context,cls)
        intent.putExtra(Config.KEY_BUNDLE, bundle)
        startActivity(intent)
    }



}