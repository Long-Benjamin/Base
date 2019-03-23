package com.ljt.base.base.bean

class Result<T> {

    var code: Int = -1

    var msg: String = ""

    var data: T? = null

    fun isSuccess(): Boolean{
        return code == 0
    }

}