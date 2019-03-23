package cn.com.iyin.utils

import android.text.TextUtils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.lang.reflect.Type

object JsonUtil {

    val GSON = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    fun toJson(src: Any): String {
        return GSON.toJson(src)
    }

    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return GSON.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String, type: Type): T {
        return GSON.fromJson(json, type)
    }

    fun isJson(json: String): Boolean {
        return !TextUtils.isEmpty(json) && json.startsWith("{") && json.endsWith("}")
    }

    fun isJsonArray(json: String): Boolean {
        return !TextUtils.isEmpty(json) && json.startsWith("[") && json.endsWith("]")
    }
}
