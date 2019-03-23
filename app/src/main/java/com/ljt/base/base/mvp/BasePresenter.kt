package com.ljt.base.base.mvp

import org.json.JSONObject
import okhttp3.ResponseBody




open class BasePresenter<V: IView>(val view: V) : IPresenter{
    private fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("msg")
        } catch (e: Exception) {
            e.message!!
        }

    }
}