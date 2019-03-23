package cn.com.iyin.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager

import java.lang.reflect.Field

/**
 * @AUTHOR: LJT
 * @DATE: 2019/1/31
 * @DESCRIPTION：华为手机inputMethodManager导致的内存泄露问题
 */
object FixMemLeak {

    private var field: Field? = null
    private var hasField = true

    fun fixLeak(context: Context) {
        if (!hasField) {
            return
        }
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm === null) return

        val arr = arrayOf("mLastSrvView")
        for (param in arr) {
            try {
                if (field == null) {
                    field = imm.javaClass.getDeclaredField(param)
                }
                if (field == null) {
                    hasField = false
                }
                if (field != null) {
                    field!!.isAccessible = true
                    field!!.set(imm, null)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }
    }

}
