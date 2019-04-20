package com.ljt.base.ui

import android.os.Bundle
import android.util.Log
import com.ljt.base.R
import com.ljt.base.base.ui.BaseWhiteTitleActivity
import com.ljt.update.OnDownloadListener
import com.ljt.update.UpdateCheck

const val URL = "https://github.com/SmarterBoy/update/blob/master/app-release.apk"

class MainActivity : BaseWhiteTitleActivity() {

    override fun initTitleBar() {
        setTitleText("首页")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()

        UpdateCheck(this).checkPermissions(URL, object: OnDownloadListener{

            override fun startLoad(all: Long?) {
                Log.e("取消下载：","---> startLoad")

            }

            override fun loaddingLoad(progress: Long?) {
                Log.e("取消下载：","---> loaddingLoad$progress %")
            }

            override fun completeLoad(path: String?) {
                Log.e("取消下载：","---> completeLoad")
            }

            override fun failLoad(exception: String?) {
                Log.e("取消下载：","---> failLoad")
            }

            override fun failRequest(exception: String?) {
                Log.e("取消下载：","---> failRequest")
            }

        })
    }

}
