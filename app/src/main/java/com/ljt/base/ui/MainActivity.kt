package com.ljt.base.ui

import android.os.Bundle
import com.ljt.base.R
import com.ljt.base.base.ui.BaseWhiteTitleActivity

class MainActivity : BaseWhiteTitleActivity() {

    override fun initTitleBar() {
        setTitleText("首页")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
