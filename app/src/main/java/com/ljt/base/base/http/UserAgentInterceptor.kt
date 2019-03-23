package com.ljt.base.base.http

import com.ljt.base.app.MainApplication
import com.ljt.base.utils.AppUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author longj
 * @date 2018/12/10.
 * @E-mail: longjintang123@163.com
 */

class UserAgentInterceptor : Interceptor {

    private val KEY_USER_AGENT = "User-Agent"
    private val userAgent: String

    init {
        val pkgName = "PACKAGE/" + AppUtils.getAppPackageName(MainApplication.INSTANCE)
        val version = "VERSION/"+ AppUtils.getAppVersionName(MainApplication.INSTANCE)
        this.userAgent = getUserAgent() + " " + pkgName + " " + version
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithUserAgent = originalRequest.newBuilder()
                .header(KEY_USER_AGENT, userAgent)
                .build()
        return chain.proceed(requestWithUserAgent)
    }

    private fun getUserAgent(): String {

        //Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)
        val userAgent: String? = System.getProperty("http.agent")

        val sb = StringBuilder()

        var i = 0
        val length = userAgent!!.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }
}