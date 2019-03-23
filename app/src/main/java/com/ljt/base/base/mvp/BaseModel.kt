package com.ljt.base.base.mvp

import com.ljt.mvvmdemo.base.http.ResultMessageException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import com.ljt.base.base.bean.Result


abstract class BaseModel<SERVICE>(service: SERVICE) {

    private var mService: SERVICE = service

    fun getService() = mService


    /**
     * 返回数据的转换
     * @param <T>
     * @param <RESULT>
     * @return </RESULT></T> */
    fun <T, RESULT : com.ljt.base.base.bean.Result<T>> transformResultToData(): ObservableTransformer<RESULT, T> {
        return ObservableTransformer { upstream ->
            upstream.compose(this.handleResultException<RESULT>())
                    .map {
                        it.data
                    }
        }
    }

    /**
     * 数据转换和同意异常处理
     */
    fun <RESULT : Result<*>> handleResultException(): ObservableTransformer<RESULT, RESULT> {
        return ObservableTransformer { upstream ->
            upstream.flatMap(Function<RESULT, Observable<RESULT>> { result ->
                var errorMessage = ""
                //数据异常
                if (!result.isSuccess()) {
                    errorMessage = result.msg
                    return@Function Observable.error(ResultMessageException(errorMessage))
                }
                Observable.just(result)
            })
        }
    }

    /** 等同于上面的两个方法的总和 */
   /* fun <T, RESULT : ResultGank<T>> handleDataException(): ObservableTransformer<RESULT, T> {
        return ObservableTransformer { upstream ->
            upstream.flatMap(Function<RESULT, Observable<T>> { result ->
                var errorMessage = ""
                //数据异常
                if (result == null) {
                    return@Function Observable.error(ResultMessageException(errorMessage))

                //服务器或数据请求异常
                } else if (result.isSuccess()) {
                    errorMessage = "Error ...!"
                    return@Function Observable.error(ResultMessageException(errorMessage))
                }
                Observable.just(result.results)
            })
        }
    }*/


}