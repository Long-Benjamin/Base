package com.along.freedom.base.intercepter

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * @author longj
 * @date 2018/12/10.
 * @E-mail: longjintang123@163.com
 */
object NetworkLoggingInterceptor {

    fun get(): HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}
