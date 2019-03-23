package cn.com.iyin.utils

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * @author longj
 * @date 2018/12/17.
 * @E-mail: longjintang123@163.com
 */
object RxCountDown {

    /**
     * 倒计时
     * @param time 倒计时秒数
     * @return
     */
    fun countdown(time: Int): Flowable<Int> {
        var time = time
        if (time < 0) time = 0

        val countTime = time
        return Flowable.interval(0, 1, TimeUnit.SECONDS)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map { countTime - it.toInt() }
                .take((countTime + 1).toLong())

    }

}
