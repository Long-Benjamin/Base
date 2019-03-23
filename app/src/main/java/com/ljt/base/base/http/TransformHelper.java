package com.ljt.base.base.http;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.ljt.base.base.bean.Result;
import com.ljt.mvvmdemo.base.http.ResultMessageException;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class TransformHelper {

  /**
   * 返回数据的转换
   * @param <T>
   * @param <RESULT>
   * @return
   * */
    public static <T, RESULT extends Result<T>> ObservableTransformer<RESULT, T> transformResultToData() {
        return  new ObservableTransformer<RESULT, T>() {
            @Override
            public ObservableSource<T> apply(Observable<RESULT> upstream) {
                return upstream.compose(TransformHelper.<RESULT>handleResultException())
                        .map(new Function<RESULT, T>() {
                            @Override
                            public T apply(RESULT result) throws Exception {
                                return result.getData();
                            }
                        });
            }

        };
    }

    public static <RESULT extends Result<?>> ObservableTransformer<RESULT,RESULT> handleResultException() {
        return  new ObservableTransformer<RESULT, RESULT>() {
            @Override
            public ObservableSource<RESULT> apply(Observable<RESULT> upstream) {
                return upstream
                        .flatMap(new Function<RESULT,Observable<RESULT>>() {

                            @Override
                            public Observable<RESULT> apply(RESULT result) throws Exception {
                                String errorMessage = "";
                                //数据异常
                                if (result == null){
                                    return Observable.error(new ResultMessageException(errorMessage));

                                    //服务器或数据请求异常
                                }else if (!result.isSuccess()) {
                                    errorMessage = result.getMsg();
                                    return Observable.error(new ResultMessageException(errorMessage));
                                }
                                return Observable.just(result);
                            }
                        });
            }

        };
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    public static <T> LifecycleTransformer <T>bindToLifecycle(@NonNull Activity lifecycle) {
        if (lifecycle instanceof LifecycleProvider) {
            return ((LifecycleProvider) lifecycle).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("context not the LifecycleProvider type");
        }

    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    public static <T> LifecycleTransformer <T>bindToLifecycle(@NonNull Fragment lifecycle) {
        if (lifecycle instanceof LifecycleProvider) {
            return ((LifecycleProvider) lifecycle).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("fragment not the LifecycleProvider type");
        }
    }


}
