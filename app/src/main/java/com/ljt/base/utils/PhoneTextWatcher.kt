package cn.com.iyin.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * @AUTHOR: LJT
 * @DATE:  2019/1/18
 * @DESCRIPTION：电话号码输入自动格式化 188 8858 9458
 */
open class PhoneTextWatcher(editText: EditText): TextWatcher {

    var editText: EditText = editText

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {

        if (charSequence == null || charSequence.isEmpty()) {
            return
        }
        var stringBuilder = StringBuilder()
        for (i in 0 until charSequence.length) {
            if (i != 3 && i != 8 && charSequence[i] == ' ') {
                continue
            } else {
                stringBuilder.append(charSequence[i])
                if ((stringBuilder.length == 4 || stringBuilder.length == 9)
                        && stringBuilder[stringBuilder.length - 1] != ' ') {
                    stringBuilder.insert(stringBuilder.length - 1, ' ')
                }
            }
        }
        if (stringBuilder.toString() != charSequence.toString()) {
            var index = start + 1
            if (stringBuilder[start] == ' ') {
                if (before == 0) {
                    index++
                } else {
                    index--
                }
            } else {
                if (before == 1) {
                    index--
                }
            }
            editText.setText(stringBuilder.toString())
            editText.setSelection(index)

        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

}