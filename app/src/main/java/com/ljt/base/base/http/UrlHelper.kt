package com.ljt.base.base.intercepter


import okhttp3.HttpUrl

/**
 * Url帮助类  <br></br>
 */
object UrlHelper {

    /***
     * 修复Url：1.增加公共参数，2.调整新Url path部分，3.签名参数
     * @param url 原始url
     * @return 修复后的Url
     */
    fun fixUrl(url: String): String {

        val httpUrl = HttpUrl.parse(url) ?: return url

        val newUrl = httpUrl.newBuilder()
                .removeAllEncodedQueryParameters("appSecret")
                .removeAllQueryParameters("access_token")
                .addQueryParameter("appSecret", "appSecret")
                .addQueryParameter("access_token", "access_token")
                .build()


        return newUrl.toString()
    }
}
