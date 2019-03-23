package cn.com.iyin.utils

import android.os.Build
import android.text.TextUtils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Method

/**
 * Created by XiaoJianjun on 2017/3/7.
 * os相关的工具类.
 */
object OsUtil {

    val isMIUI: Boolean
        get() = !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"))

    val isFlyme: Boolean
        get() {
            try {
                val method = Build::class.java.getMethod("hasSmartBar")
                return method != null
            } catch (e: Exception) {
                return false
            }

        }

    private fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return line
    }
}
