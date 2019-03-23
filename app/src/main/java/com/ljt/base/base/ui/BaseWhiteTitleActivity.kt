package com.ljt.base.base.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.ljt.base.R
import com.ljt.base.commond.Config
import com.ljt.base.utils.StatusBarUtil

abstract class BaseWhiteTitleActivity: BaseActivity() {

    /** 子activity的布局容器 */
    private lateinit var container: FrameLayout
    /** 标题栏和状态栏 */
    private lateinit var titleBar: LinearLayout
    /** 标题栏 */
    private lateinit var toolBar: Toolbar
    /** 页面左上叫返回按钮 */
    private lateinit var backImg: ImageButton
    /** 页面标题 */
    private lateinit var titleTv: TextView
    /** 右上角更多按钮 */
    private lateinit var moreImg: ImageButton
    /** ButterKnife的Unbunder对象 */
    private lateinit var unbunder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.base_activity_white_container_layout)

        container = findViewById(R.id.container)
        titleBar = findViewById(R.id.title_bar)
        toolBar = findViewById(R.id.tool_bar)
        backImg = findViewById(R.id.titel_back)
        titleTv = findViewById(R.id.title_text)
        moreImg = findViewById(R.id.title_more)

        initTitleBar()
        initBackBar()

        //设置状态栏字体颜色
        var statusBarUtil = StatusBarUtil(this)
        if (getStatusDarkMode()) {
            statusBarUtil.setDarkMode()
        }else{
            statusBarUtil.setLightMode()
        }
    }

    abstract fun initTitleBar()

    open fun getStatusDarkMode(): Boolean {
        return true
    }

    /**
     * 必要时可以加一个回调，在子类中处理点击事件
     */
    fun initBackBar() {
        backImg.setOnClickListener {
            this.finish()
        }
    }


    override fun setContentView(layoutResID: Int) {
        val contentView = LayoutInflater.from(this).inflate(layoutResID, null)
        container.addView(contentView)
        unbunder = ButterKnife.bind(this)

    }

    /**
     * 标题栏 部分 -》
     */
    protected fun setTitleText(title: String) {
        titleTv.text = title
    }

    protected fun setTitleBarColor(color: Int) {
        titleBar.setBackgroundColor(color)
    }

    protected fun setTitleBarResource(resId: Int) {
        titleBar.setBackgroundResource(resId)
    }

    protected fun setMoreVisible(visible: Boolean) {
        moreImg.visibility = if(visible)View.VISIBLE else View.INVISIBLE
    }

    protected fun setTitelBarVisible(visible: Boolean) {
        toolBar.visibility = if(visible)View.VISIBLE else View.GONE
    }

    protected fun setBackBarDrawable(resId: Int) {
        backImg.setImageResource(resId)
    }


    /**
     * 页面跳转 部分 -》
     */
    protected fun startActivity(cls:Class<*>){
        startActivity(Intent(this,cls))
    }

    protected fun startActivity(cls:Class<*>, bundle: Bundle){
        var intent = Intent(this,cls)
        intent.putExtra(Config.KEY_BUNDLE, bundle)
        startActivity(intent)
    }

    /**
     * 页面销毁回收 部分 -》
     */
    override fun onDestroy() {
        super.onDestroy()
        unbunder.unbind()

    }


    fun showToast(info: String) {

    }

     fun showProgress() {

    }


}