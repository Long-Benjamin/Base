package com.ljt.base.app

import com.ljt.base.BuildConfig
import com.ljt.base.utils.logger.DebugTree
import com.ljt.base.utils.logger.ReleaseTree
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import kotlin.concurrent.thread

class MainApplication : BaseApplication() {


    companion object {
        @JvmStatic lateinit var INSTANCE: MainApplication
        @JvmStatic fun getInstance()  = INSTANCE
    }


    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        Injects.init(INSTANCE)

        Injects.appComponent().inject(this)

        /** 开启子线程初始化 */
        thread(start = true){
            initComponent()
        }

    }

    private fun initComponent() {

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            LeakCanary.install(this)
        } else {
            Timber.plant(ReleaseTree())
        }



    }

}