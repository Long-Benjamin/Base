package com.ljt.base.utils.glide

import com.bumptech.glide.load.model.GlideUrl

/**
 * 【作用】：当图片的url中带用于保护图片资源的变化的token
 * 【用法】：使用我们创建的MyGlideUrl传入url代码(java)：
        Glide.with(this)
        .load(new MyGlideUrl(url))
        .into(imageView);
 */
class MyGlideUrl constructor(var mUrl: String): GlideUrl(mUrl) {


    override fun getCacheKey(): String {
        //将token部分参数替换为空字符串后返回作为缓存Key
        return mUrl.replace(findTokenParam(), "");
    }

    /**
     * 查找token部分参数的方法
     * @return token部分参数String
     */
    fun findTokenParam(): String{
        var tokenParam = ""
        var tokenKeyIndex = if (mUrl.indexOf("?token=") >= 0)  mUrl.indexOf("?token=") else mUrl.indexOf("&token=")

        if (tokenKeyIndex != -1) {
            var nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1)

            tokenParam = if (nextAndIndex != -1) {
                mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1)
            } else {
                mUrl.substring(tokenKeyIndex)
            }
        }
        return tokenParam
    }
}