package com.ljt.base.utils.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Glide 图片加载配置工具类
 */
object OptionsUtil {

    fun normalOption(): RequestOptions{
        return RequestOptions()
//                .placeholder(R.drawable.ic_content_add)
//                .error(R.drawable.ic_back)
                .priority(Priority.NORMAL)
    }

    fun centerCropOption(): RequestOptions{
        return normalOption().centerCrop()
    }

    fun fitCenterOption(): RequestOptions{
        return normalOption().fitCenter()
    }

    fun circleCropOption(): RequestOptions{
        return normalOption().circleCrop()
    }

    fun skipMemoryCacheOption(): RequestOptions{
        return normalOption().skipMemoryCache(true)
    }

    fun diskCacheOption(): RequestOptions{
        return normalOption().diskCacheStrategy(DiskCacheStrategy.NONE)
    }

    fun overrideOption(width: Int, height: Int): RequestOptions{
        return normalOption().override(width,height)
    }




}