package com.along.freedom.base.intercepter

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * 拦截过滤""替换成null，解决因""导致解析对象错误问题。  <br></br>
 */
class ResultInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val proceed = chain.proceed(chain.request())
        val responseBody = proceed.body()
        //如果data对应的是对象，但data无数据时，返回的是"",而不是null，导致解析data异常
        //所以将""替换成null。
        val resultBody = responseBody!!.string().replace(":\"\"".toRegex(), ":null")
        val newResponseBody = ResponseBody.create(responseBody.contentType(), resultBody)

        return proceed.newBuilder().body(newResponseBody).build()
    }

}
