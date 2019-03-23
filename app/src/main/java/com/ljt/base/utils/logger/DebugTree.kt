package com.ljt.base.utils.logger
import com.orhanobut.logger.Logger
import timber.log.Timber
import com.orhanobut.logger.AndroidLogAdapter


/**
 * Debug模式时打印日志
 */
class DebugTree : Timber.Tree(){

    init {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Logger.log(priority,tag,message,t)
    }

}