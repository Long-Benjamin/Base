package com.ljt.base.base.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.Unbinder
import cn.com.iyin.utils.OsUtil
import com.ljt.base.R
import com.ljt.base.commond.Config
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * 提供默认的标题栏
 * @property mViewStatusBarPlace View? 状态栏
 * @property mFrameLayoutContent FrameLayout? 主容器
 * @property mTitleLayout LinearLayout? 标题栏
 * @property toolbar Toolbar?
 * @property titleText TextView? 标题
 */
abstract class BaseFragment: RxFragment() {

    val TAG = this.javaClass.simpleName

    private var unbinder: Unbinder ?= null

    var mViewStatusBarPlace: View? = null
    var mFrameLayoutContent: FrameLayout? = null
    var mTitleLayout: LinearLayout? = null
    var toolbar: Toolbar? = null
    var titleText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.base_fragment_container_layout, container, false)

        mViewStatusBarPlace = root.findViewById(R.id.status_bar)
        var params = mViewStatusBarPlace?.layoutParams
        params?.height = getStatusBarHeight()
        mViewStatusBarPlace?.layoutParams = params

        mFrameLayoutContent = root.findViewById(R.id.container) as FrameLayout
        var layout = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        val contentView =  LayoutInflater.from(context).inflate(getLayoutId(), null)
        mFrameLayoutContent?.addView(contentView,layout)

        mTitleLayout = root.findViewById(R.id.title_bar)
        toolbar = root.findViewById(R.id.tool_bar)
        titleText = root.findViewById(R.id.title_text)

        initTitle()

        unbinder = ButterKnife.bind(this, contentView)

        return root
    }

    private fun initTitle() {
        //更新标题
        titleText?.text = getTitleText()
        mTitleLayout?.visibility = getVisibility()
        toolbar?.visibility = getToolBarVisibility()

        //重新设置状态栏高度
        val params = mViewStatusBarPlace?.layoutParams
        params?.height = getStatusBarHeight()
        mViewStatusBarPlace?.layoutParams = params



        //设置状态栏字体颜色
//        if (getStatusDarkMode()) {
//            setDarkMode()
//        }else{
//            setLightMode()
//        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun getStatusDarkMode(): Boolean {
        return true
    }

    open fun getTitleText(): String{
        return getString(R.string.app_name)
    }

    open fun getVisibility(): Int{
        return View.VISIBLE
    }

    open fun getToolBarVisibility(): Int{
        return View.VISIBLE
    }

    /** 初始化控件*/
    abstract fun initView()

    /** 获取跳转参数 */
    abstract fun initParam()

    /** 获取布局文件ID*/
    abstract fun getLayoutId(): Int

    fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }


    //深色模式
    fun setDarkMode() {
        setImmersiveStatusBar(true, Color.DKGRAY)
        setToolbarBgColor(Color.DKGRAY)
    }

    private fun setToolbarBgColor(white: Int) {
        toolbar?.setBackgroundColor(white)
    }

    //白色模式
    fun setLightMode() {
        setImmersiveStatusBar(false, Color.WHITE)
        setToolbarBgColor(Color.WHITE)
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
                    statusBarPlaceColor = -0x333334
                }
            }
        }
        setStatusBarPlaceColor(statusBarPlaceColor)
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


    private fun setStatusBarPlaceColor(statusColor: Int) {
        if (mViewStatusBarPlace != null) {
            mViewStatusBarPlace?.setBackgroundColor(statusColor)
        }
    }

    fun getFragment(): Fragment{
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
        unbinder?.unbind()
    }

    fun showToast(info: String) {

    }

    fun showProgress() {

    }

    /**
     * 页面跳转 部分 -》
     */
    protected fun startActivity(cls:Class<*>){
        startActivity(Intent(this.requireContext(),cls))
    }

    protected fun startActivity(cls:Class<*>, bundle: Bundle){
        var intent = Intent(this.requireContext(),cls)
        intent.putExtra(Config.KEY_BUNDLE, bundle)
        startActivity(intent)
    }
}