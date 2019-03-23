package com.ljt.base.base.ui

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity: RxAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        doBeforCreate()
        super.onCreate(savedInstanceState)

    }

    /**
     * 比如做Theme 的修改之类的，启屏页的优化时会用
     */
    open fun doBeforCreate(){}


    /**
     * 这里是对 AView 中方法的默认实现
     */
    fun getActivity(): BaseActivity {
        return this
    }
}