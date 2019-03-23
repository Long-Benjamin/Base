package com.ljt.base.utils

import android.content.Context
import com.ljt.base.app.MainApplication

object SharePreHelper {

    private val FREEDOM = "FREEDOM"
    private val FIRST_START = "FIRST_START"

    private val preferences = MainApplication.getInstance().getSharedPreferences(FREEDOM, Context.MODE_PRIVATE)

    //是否第一次启动App
    var isFirstStart = preferences.getBoolean(FIRST_START,true)
    set(value) = putAny(FIRST_START,value)



    fun putAny(key: String, any: Any){

        when(any){
            is Boolean -> preferences.edit().putBoolean(key,any).apply()
            is String -> preferences.edit().putString(key,any).apply()
            is Float -> preferences.edit().putFloat(key,any).apply()
            is Long -> preferences.edit().putLong(key,any).apply()
            is Int -> preferences.edit().putInt(key,any).apply()
            else -> throw RuntimeException("SharedPreferences can't save this type Data!")
        }
    }

}