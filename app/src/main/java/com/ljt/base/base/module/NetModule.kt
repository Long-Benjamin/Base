package com.ljt.base.base.module

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import cn.com.iyin.utils.JsonUtil
import com.along.freedom.base.intercepter.ResultInterceptor
import com.google.gson.Gson
import com.ljt.base.BuildConfig
import com.ljt.base.base.http.UserAgentInterceptor
import com.ljt.base.base.scope.NetScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named


@Module
class NetModule @Inject constructor(baseUrl: String) {

    private val DEFAULT_TIMEOUT_SECONDS: Long = 15
    private val CACHE_MAX_SIZE = (10 * 1024 * 1024).toLong()

    private var mBaseUrl: String

    init {
        mBaseUrl = baseUrl
    }

    @NetScope
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = ArrayList<Interceptor>()
        interceptors.add(ResultInterceptor())
        interceptors.add(UserAgentInterceptor())
        return interceptors
    }

    @NetScope
    @Provides
    @Named("network_interceptors")
    fun provideNetworkInterceptors(): ArrayList<Interceptor> {
        val networkInterceptors = ArrayList<Interceptor>()
        //Debug 模式禁用Gzip。因为Logging打印不了Gzip的数据。
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            networkInterceptors.add(httpLoggingInterceptor)
            networkInterceptors.add(Interceptor { chain ->
                val request = chain.request().newBuilder()
                        .removeHeader("Accept-Encoding").build()
                chain.proceed(request)
            })
        }
        return networkInterceptors
    }


    @NetScope
    @Provides
    fun provideCache(context: Context): Cache {

        val cacheDir = if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED))
            context.externalCacheDir
        else
            context.cacheDir

        return Cache(cacheDir!!, CACHE_MAX_SIZE)
    }

    @NetScope
    @Provides
    fun provideOkHttpClientBuilder(cache: Cache): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(cache)
//                .authenticator(TokenAuthenticator())
    }

    @Provides
    @NetScope
    fun provideOkHttpClient(builder: OkHttpClient.Builder, interceptors: ArrayList<Interceptor>,
                            @Named("network_interceptors")networkInterceptors: ArrayList<Interceptor>): OkHttpClient {

        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }
        for (interceptor in networkInterceptors) {
            builder.addNetworkInterceptor(interceptor)
        }
        return builder.build()
    }

    @NetScope
    @Provides
    fun provideGson(): Gson {
        return JsonUtil.GSON

    }

    @NetScope
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @NetScope
    @Provides
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder
                .baseUrl(mBaseUrl)
                .build()
    }


}